package com.example.peter.bakingapp.recipe;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.peter.bakingapp.R;
import com.example.peter.bakingapp.common.models.dto.RecipesResponse;
import com.squareup.picasso.Picasso;

/**
 * Created by Peter on 03/02/2018.
 */

class RecipesListingAdapter extends RecyclerView.Adapter<RecipesListingAdapter.MyViewHolder> {
    private Context context;
    private RecipesResponse[] recipesResponse;
    private onItemClickListener onItemClickListener;


    public RecipesListingAdapter(Context context, onItemClickListener onItemClickListener, RecipesResponse[] recipesResponse) {
        this.context = context;
        this.recipesResponse = recipesResponse;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_listing_rv_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipesListingAdapter.MyViewHolder holder, int position) {
        if (recipesResponse[position] != null) {
            holder.txtRecipeItemTitle.setText(recipesResponse[position].getName());
            holder.txtRecipeItemIngredient.setText(String.valueOf(recipesResponse[position].getIngredients().size()));
            holder.txtRecipeItemSteps.setText(String.valueOf(recipesResponse[position].getSteps().size()));
            holder.txtRecipeItemServing.setText(String.valueOf(recipesResponse[position].getServings()));

            if (!TextUtils.isEmpty(recipesResponse[position].getImage()))
                Picasso.with(context).load(recipesResponse[position].getImage())
                        .error(R.drawable.placeholder)
                        .placeholder(R.drawable.placeholder)
                        .into(holder.imgRecipeItemImage);
        }
    }

    @Override
    public int getItemCount() {
        return recipesResponse.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtRecipeItemTitle, txtRecipeItemIngredient, txtRecipeItemSteps, txtRecipeItemServing;
        private ImageView imgRecipeItemImage;
        private CardView cvRecipeItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtRecipeItemTitle = itemView.findViewById(R.id.txtRecipeItemTitle);
            txtRecipeItemIngredient = itemView.findViewById(R.id.txtRecipeItemIngredient);
            txtRecipeItemSteps = itemView.findViewById(R.id.txtRecipeItemSteps);
            txtRecipeItemServing = itemView.findViewById(R.id.txtRecipeItemServing);
            imgRecipeItemImage = itemView.findViewById(R.id.imgRecipeItemImage);
            cvRecipeItem = itemView.findViewById(R.id.cvRecipeItem);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null)
                if (recipesResponse != null)
                    onItemClickListener.onRecipeClick(recipesResponse[getAdapterPosition()]);
        }
    }

    public interface onItemClickListener {
        void onRecipeClick(RecipesResponse recipesResponse);
    }

}
