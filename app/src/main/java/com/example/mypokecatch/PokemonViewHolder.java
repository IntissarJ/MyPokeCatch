package com.example.mypokecatch;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PokemonViewHolder extends RecyclerView.ViewHolder
{

    ImageView imageView;
    TextView textView;
    private RequestQueue requestQueue;
    public PokemonViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageView);
        textView = itemView.findViewById(R.id.textView);
    }



}
