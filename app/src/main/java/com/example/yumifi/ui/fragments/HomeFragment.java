package com.example.yumifi.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.yumifi.R;
import com.example.yumifi.database.DatabaseHelper;
import com.example.yumifi.models.Recipe;

import java.util.List;

public class HomeFragment extends Fragment {

    private DatabaseHelper databaseHelper; // Экземпляр DatabaseHelper

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Инициализация базы данных
        databaseHelper = new DatabaseHelper(getContext());

        // Получаем список рецептов из базы данных
        List<Recipe> recipes = databaseHelper.getAllRecipes();

        // Пример вывода рецептов в TextView (вы можете заменить это на RecyclerView для списка)
        TextView recipesTextView = rootView.findViewById(R.id.recipesTextView);
        StringBuilder recipesList = new StringBuilder();
        for (Recipe recipe : recipes) {
            recipesList.append(recipe.getTitle()).append("\n");
        }

        recipesTextView.setText(recipesList.toString());

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрытие базы данных при уничтожении фрагмента
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}
