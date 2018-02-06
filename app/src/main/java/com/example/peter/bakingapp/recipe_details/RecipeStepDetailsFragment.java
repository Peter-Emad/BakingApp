package com.example.peter.bakingapp.recipe_details;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.peter.bakingapp.R;
import com.example.peter.bakingapp.common.base.BaseFragment;
import com.example.peter.bakingapp.common.helpers.Constants;
import com.example.peter.bakingapp.common.models.Step;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 05/02/2018.
 */

public class RecipeStepDetailsFragment extends BaseFragment implements ExoPlayer.EventListener {


    private Context context;
    private List<Step> steps;
    private int selectedPosition = -1;
    private ImageView imgRecipeStepPrevious, imgRecipeStepNext;
    private TextView txtRecipeStepNumber, txtRecipeStepDescription;
    private SimpleExoPlayerView recipeStepPlayer;
    private SimpleExoPlayer player;

    public static RecipeStepDetailsFragment newInstance(List<Step> stepList, int position) {
        RecipeStepDetailsFragment recipeStepDetailsFragment = new RecipeStepDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.RECIPE_STEP_SELECTED_POSITION, position);
        bundle.putParcelableArrayList(Constants.RECIPE_STEPS_KEY, (ArrayList<? extends Parcelable>) stepList);
        recipeStepDetailsFragment.setArguments(bundle);
        return recipeStepDetailsFragment;
    }


    private void initExoPlayer() {
        try {

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            Uri videoURL = null;
            if (!steps.get(selectedPosition).getVideoURL().isEmpty())
                videoURL = Uri.parse(steps.get(selectedPosition).getVideoURL());
            else if (!steps.get(selectedPosition).getThumbnailURL().isEmpty())
                videoURL = Uri.parse(steps.get(selectedPosition).getThumbnailURL());
            DefaultHttpDataSourceFactory defaultHttpDataSourceFactory = new DefaultHttpDataSourceFactory(context.getString(R.string.app_name));
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videoURL, defaultHttpDataSourceFactory, extractorsFactory, null, null);
            recipeStepPlayer.setPlayer(player);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        } catch (Exception e) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        context = getActivity();
        initializeViews(v);
        setListeners();
        initSelectedRecipeStep();
        return v;
    }


    @Override
    protected void initializeViews(View v) {
        imgRecipeStepPrevious = v.findViewById(R.id.imgRecipeStepPrevious);
        imgRecipeStepNext = v.findViewById(R.id.imgRecipeStepNext);
        txtRecipeStepNumber = v.findViewById(R.id.txtRecipeStepNumber);
        recipeStepPlayer = v.findViewById(R.id.recipeStepPlayer);
        txtRecipeStepDescription = v.findViewById(R.id.txtRecipeStepDescription);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            steps = bundle.getParcelableArrayList(Constants.RECIPE_STEPS_KEY);
            selectedPosition = bundle.getInt(Constants.RECIPE_STEP_SELECTED_POSITION);
        }
        if (TextUtils.isEmpty(steps.get(selectedPosition).getVideoURL()))
            initExoPlayer();
        else recipeStepPlayer.setVisibility(View.GONE);

    }

    @Override
    protected void setListeners() {
        imgRecipeStepNext.setOnClickListener(imgRecipeStepNextOnClickListener);
        imgRecipeStepPrevious.setOnClickListener(imgRecipeStepPreviousOnClickListener);

    }

    private View.OnClickListener imgRecipeStepNextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private View.OnClickListener imgRecipeStepPreviousOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };


    private void initSelectedRecipeStep() {
        //player
        txtRecipeStepDescription.setText(steps.get(selectedPosition).getDescription());
        txtRecipeStepNumber.setText(context.getString(R.string.recipeStep, selectedPosition, steps.size()));
        if (!TextUtils.isEmpty(steps.get(selectedPosition).getThumbnailURL()))
            try {
                recipeStepPlayer.setDefaultArtwork(Picasso.with(context).load(steps.get(selectedPosition).getThumbnailURL())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder).get());
            } catch (IOException e) {
                e.printStackTrace();
            }

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

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

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
}
