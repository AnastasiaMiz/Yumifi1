package com.example.yumifi.ui.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.yumifi.R;

public class AddRecipeFragment extends Fragment {

    public AddRecipeFragment() {
        // Пустой конструктор
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout для фрагмента
        return inflater.inflate(R.layout.fragment_add_recipe, container, false);
    }
}
