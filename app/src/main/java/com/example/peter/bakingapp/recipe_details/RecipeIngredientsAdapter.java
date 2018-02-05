package com.example.peter.bakingapp.recipe_details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.peter.bakingapp.R;
import com.example.peter.bakingapp.common.models.Ingredient;

import java.util.List;

/**
 * Created by Peter on 04/02/2018.
 */

class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.MyViewHolder> {
    private Context context;
    private List<Ingredient> recipeIngredients;

    public RecipeIngredientsAdapter(Context context, List<Ingredient> recipeIngredients) {
        this.context = context;
        this.recipeIngredients = recipeIngredients;
    }

    @Override
    public RecipeIngredientsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_ingredients_rv_row, parent, false);
        return new RecipeIngredientsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeIngredientsAdapter.MyViewHolder holder, int position) {
        if (recipeIngredients.get(position) != null) {
            holder.txtIngredientQuantity.setText(String.valueOf(recipeIngredients.get(position).getQuantity()));
            holder.txtIngredientIngredient.setText(recipeIngredients.get(position).getIngredient());
            holder.txtIngredientMeasure.setText(recipeIngredients.get(position).getMeasure());
        }
    }

    @Override
    public int getItemCount() {
        return recipeIngredients.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtIngredientQuantity, txtIngredientMeasure, txtIngredientIngredient;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtIngredientQuantity = itemView.findViewById(R.id.txtIngredientQuantity);
            txtIngredientMeasure = itemView.findViewById(R.id.txtIngredientMeasure);
            txtIngredientIngredient = itemView.findViewById(R.id.txtIngredientIngredient);
        }
    }
}
