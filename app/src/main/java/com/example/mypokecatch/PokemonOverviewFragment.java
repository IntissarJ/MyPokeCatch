package com.example.mypokecatch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mypokecatch.database.Pokemon;

import java.util.List;

public class PokemonOverviewFragment extends Fragment {

    private final PokemonAdapter adapter;

    public PokemonOverviewFragment(List<Pokemon> pokemons, PokemonAdapter adapter) {
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


//    // TODO: Rename and change types and number of parameters
//    public static PokemonOverviewFragment newInstance(String param1, String param2) {
//        PokemonOverviewFragment fragment = new PokemonOverviewFragment();
//        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
////        fragment.setArguments(args);
//        return fragment;
//    }

}