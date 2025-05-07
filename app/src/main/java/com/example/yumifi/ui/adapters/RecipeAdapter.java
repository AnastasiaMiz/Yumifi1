package com.example.yumifi.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.yumifi.R;
import com.example.yumifi.models.Recipe;

import java.util.List;

public class RecipeAdapter extends ArrayAdapter<Recipe> {

    private Context context;
    private List<Recipe> recipes;

    public RecipeAdapter(Context context, List<Recipe> recipes) {
        super(context, R.layout.item_recipe, recipes);
        this.context = context;
        this.recipes = recipes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_recipe, parent, false);
        }

        Recipe recipe = getItem(position);

        TextView titleTextView = convertView.findViewById(R.id.recipeTitle);
        TextView descriptionTextView = convertView.findViewById(R.id.recipeDescription);

        titleTextView.setText(recipe.getTitle());
        descriptionTextView.setText(recipe.getDescription());

        return convertView;
    }
}
