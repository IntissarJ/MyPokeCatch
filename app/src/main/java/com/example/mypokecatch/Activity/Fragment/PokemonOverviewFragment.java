package com.example.mypokecatch.Activity.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mypokecatch.Adapter.PokemonAdapter;
import com.example.mypokecatch.R;


public class PokemonOverviewFragment extends Fragment {

    private final PokemonAdapter adapter;

    public PokemonOverviewFragment(PokemonAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_pokemon_overview,
                container, false);
        FragmentActivity act = getActivity();
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(act));
        recyclerView.setAdapter(this.adapter);

        // Inflate the layout for this fragment
        return root;
    }
}