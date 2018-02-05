package com.example.peter.bakingapp.common.helpers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import static com.example.peter.bakingapp.common.helpers.ServicesHelper.Tag.LOAD_RECIPES;


public class ServicesHelper {
    private static ServicesHelper mInstance;
    private RequestQueue mRequestQueue;
    private static final String RECIPES_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


    private ServicesHelper(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static synchronized ServicesHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ServicesHelper(context.getApplicationContext());
        }
        return mInstance;
    }


    private <T> void addToRequestQueue(Request<T> req) {
        mRequestQueue.add(req);
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public void fetchRecipes(Response.Listener<org.json.JSONArray> loadRecipesSuccessListener, Response.ErrorListener loadRecipesErrorListener) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, RECIPES_URL, null, loadRecipesSuccessListener, loadRecipesErrorListener);
        jsonArrayRequest.setTag(LOAD_RECIPES);
        addToRequestQueue(jsonArrayRequest);
    }


    public enum Tag {
        LOAD_RECIPES
    }
}
