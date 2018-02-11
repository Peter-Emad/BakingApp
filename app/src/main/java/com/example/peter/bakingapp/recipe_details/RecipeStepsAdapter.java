package com.example.peter.bakingapp.recipe_details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.peter.bakingapp.R;
import com.example.peter.bakingapp.common.models.Step;

import java.util.List;

/**
 * Created by Peter on 05/02/2018.
 */

class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.MyViewHolder> {
    private Context context;
    private List<Step> recipeSteps;
    private OnRecipeStepClicked onRecipeStepClicked;


    public RecipeStepsAdapter(Context context, List<Step> recipeSteps) {
        this.context = context;
        this.recipeSteps = recipeSteps;
        this.onRecipeStepClicked = (OnRecipeStepClicked) context;
    }

    @Override
    public RecipeStepsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_steps_rv_row, parent, false);
        return new RecipeStepsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeStepsAdapter.MyViewHolder holder, int position) {
        if (recipeSteps.get(position) != null) {
            holder.txtRecipeStepDescription.setText(context.getString(R.string.recipeDescription, position, recipeSteps.get(position).getShortDescription()));
            if (TextUtils.isEmpty(recipeSteps.get(position).getVideoURL()))
                holder.imgRecipeStepImage.setVisibility(View.GONE);
            else
                holder.imgRecipeStepImage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return recipeSteps.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RelativeLayout rlRecipeStepContainer;
        private TextView txtRecipeStepDescription;
        private ImageView imgRecipeStepImage;

        public MyViewHolder(View itemView) {
            super(itemView);

            rlRecipeStepContainer = itemView.findViewById(R.id.rlRecipeStepContainer);
            txtRecipeStepDescription = itemView.findViewById(R.id.txtRecipeStepDescription);
            imgRecipeStepImage = itemView.findViewById(R.id.imgRecipeStepImage);
            rlRecipeStepContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onRecipeStepClicked != null)
                if (recipeSteps != null)
                    onRecipeStepClicked.onItemClickListener(recipeSteps, getAdapterPosition());

        }
    }

    public interface OnRecipeStepClicked {
        void onItemClickListener(List<Step> stepList, int position);
    }
}
