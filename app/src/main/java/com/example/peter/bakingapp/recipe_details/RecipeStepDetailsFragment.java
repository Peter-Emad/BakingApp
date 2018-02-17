package com.example.peter.bakingapp.recipe_details;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.bakingapp.R;
import com.example.peter.bakingapp.common.base.BaseFragment;
import com.example.peter.bakingapp.common.helpers.Constants;
import com.example.peter.bakingapp.common.models.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.peter.bakingapp.common.helpers.Constants.EXO_PLAYER_PLAY_WHEN_READY;
import static com.example.peter.bakingapp.common.helpers.Constants.RECIPE_SELECTED_STEP_DETAILS;
import static com.example.peter.bakingapp.common.helpers.Constants.SELECTED_RECIPE_VIDEO_POSITION;

/**
 * Created by Peter on 05/02/2018.
 */

public class RecipeStepDetailsFragment extends BaseFragment implements ExoPlayer.EventListener {


    private Context context;
    private List<Step> steps;
    private int selectedPosition = -1;
    private ImageView imgRecipeStepPrevious, imgRecipeStepNext;
    private TextView txtRecipeStepNumber, txtRecipeStepDescription;
    private SimpleExoPlayerView recipeStepExoPlayerView;
    private SimpleExoPlayer simpleExoPlayer;
    private MediaSessionCompat mediaSessionCompat;
    private PlaybackStateCompat.Builder playBackState;
    private long currentVideoPosition;
    private boolean exoPlayerPlayWhenReady;

    public static RecipeStepDetailsFragment newInstance(List<Step> stepList, int position) {
        RecipeStepDetailsFragment recipeStepDetailsFragment = new RecipeStepDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.RECIPE_STEP_SELECTED_POSITION, position);
        bundle.putParcelableArrayList(Constants.RECIPE_STEPS_KEY, (ArrayList<? extends Parcelable>) stepList);
        recipeStepDetailsFragment.setArguments(bundle);
        return recipeStepDetailsFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        context = getActivity();
        initializeViews(v);
        setListeners();
        if (savedInstanceState != null) {
            if (savedInstanceState.getInt(RECIPE_SELECTED_STEP_DETAILS) != 0)
                selectedPosition = savedInstanceState.getInt(RECIPE_SELECTED_STEP_DETAILS);
            if (savedInstanceState.getLong(SELECTED_RECIPE_VIDEO_POSITION) != 0)
                currentVideoPosition = savedInstanceState.getLong(SELECTED_RECIPE_VIDEO_POSITION);
            exoPlayerPlayWhenReady = savedInstanceState.getBoolean(EXO_PLAYER_PLAY_WHEN_READY, true);
            initSelectedRecipeStep(selectedPosition);
        }

        return v;
    }


    @Override
    protected void initializeViews(View v) {
        imgRecipeStepPrevious = v.findViewById(R.id.imgRecipeStepPrevious);
        imgRecipeStepNext = v.findViewById(R.id.imgRecipeStepNext);
        txtRecipeStepNumber = v.findViewById(R.id.txtRecipeStepNumber);
        recipeStepExoPlayerView = v.findViewById(R.id.recipeStepPlayer);
        txtRecipeStepDescription = v.findViewById(R.id.txtRecipeStepDescription);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            steps = bundle.getParcelableArrayList(Constants.RECIPE_STEPS_KEY);
            selectedPosition = bundle.getInt(Constants.RECIPE_STEP_SELECTED_POSITION);
        }

    }


    @Override
    protected void setListeners() {
        imgRecipeStepNext.setOnClickListener(imgRecipeStepNextOnClickListener);
        imgRecipeStepPrevious.setOnClickListener(imgRecipeStepPreviousOnClickListener);

    }

    private void initMediaSessionAndPlayer() {
        if (!TextUtils.isEmpty(steps.get(selectedPosition).getVideoURL())) {
            recipeStepExoPlayerView.setVisibility(View.VISIBLE);
            initMediaSession();
            initExoPlayer();
        } else
            recipeStepExoPlayerView.setVisibility(View.GONE);

    }

    private void initMediaSession() {
        mediaSessionCompat = new MediaSessionCompat(context, RecipeStepDetailsFragment.class.getSimpleName());
        mediaSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSessionCompat.setMediaButtonReceiver(null);
        playBackState = new PlaybackStateCompat.Builder().setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PAUSE
                | PlaybackStateCompat.ACTION_PLAY_PAUSE | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS);
        mediaSessionCompat.setPlaybackState(playBackState.build());
        mediaSessionCompat.setCallback(new myMediaSessionCallback());
        mediaSessionCompat.setActive(true);

    }

    private void initExoPlayer() {
        if (simpleExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            simpleExoPlayer.seekTo(currentVideoPosition);
            recipeStepExoPlayerView.setPlayer(simpleExoPlayer);
            simpleExoPlayer.addListener(this);
            String userAgent = Util.getUserAgent(context, getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(steps.get(selectedPosition).getVideoURL()), new DefaultDataSourceFactory(context, userAgent), new DefaultExtractorsFactory(), null, null);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(exoPlayerPlayWhenReady);
        } else
            simpleExoPlayer.seekTo(currentVideoPosition);

    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
        if (mediaSessionCompat != null) {
            mediaSessionCompat.setActive(false);
            mediaSessionCompat = null;
        }

    }

    private View.OnClickListener imgRecipeStepNextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (selectedPosition + 1 < steps.size()) {
                selectedPosition += 1;
                releasePlayer();
                initSelectedRecipeStep(selectedPosition);
                initMediaSessionAndPlayer();
            }
        }
    };
    private View.OnClickListener imgRecipeStepPreviousOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (selectedPosition - 1 >= 0) {
                selectedPosition -= 1;
                releasePlayer();
                initSelectedRecipeStep(selectedPosition);
                initMediaSessionAndPlayer();
            }
        }
    };


    private void initSelectedRecipeStep(int stepPosition) {
        //simpleExoPlayer
        txtRecipeStepDescription.setText(steps.get(stepPosition).getDescription());
        txtRecipeStepNumber.setText(context.getString(R.string.recipeStep, stepPosition, steps.size()));
        if (!TextUtils.isEmpty(steps.get(stepPosition).getThumbnailURL()))
            try {
                recipeStepExoPlayerView.setDefaultArtwork(Picasso.with(context).load(steps.get(stepPosition).getThumbnailURL())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder).get());
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initMediaSessionAndPlayer();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || simpleExoPlayer == null))
            initMediaSessionAndPlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (simpleExoPlayer != null)
            currentVideoPosition = simpleExoPlayer.getCurrentPosition();
        if (Util.SDK_INT <= 23)
            releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23)
            releasePlayer();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            playBackState.setState(PlaybackStateCompat.STATE_PLAYING,
                    simpleExoPlayer.getCurrentPosition(), 1f);
        } else if (playbackState == ExoPlayer.STATE_READY) {
            playBackState.setState(PlaybackStateCompat.STATE_PAUSED,
                    simpleExoPlayer.getCurrentPosition(), 1f);
        }
        if (playBackState != null) {
            mediaSessionCompat.setPlaybackState(playBackState.build());
        }

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    private class myMediaSessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onSkipToPrevious() {
            simpleExoPlayer.seekTo(0);
        }

        @Override
        public void onPause() {
            simpleExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onPlay() {
            simpleExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(RECIPE_SELECTED_STEP_DETAILS, selectedPosition);
        outState.putLong(SELECTED_RECIPE_VIDEO_POSITION, currentVideoPosition);
        outState.putBoolean(EXO_PLAYER_PLAY_WHEN_READY, simpleExoPlayer.getPlayWhenReady());
    }
}
