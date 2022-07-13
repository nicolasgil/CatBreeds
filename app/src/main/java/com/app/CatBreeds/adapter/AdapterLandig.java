package com.app.CatBreeds.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.CatBreeds.model.ModelLanding;
import com.app.catbreeds.R;
import com.bumptech.glide.Glide;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class AdapterLandig extends RecyclerView.Adapter<AdapterLandig.LandingViewHolder> implements View.OnClickListener {

    private final List<ModelLanding> listRecycler;
    private final Context context;
    private final List<ModelLanding> listPrincipal;
    private View.OnClickListener listener;

    public AdapterLandig(Context ctx, List<ModelLanding> listRecycler) {
        this.context = ctx;
        this.listRecycler = listRecycler;
        listPrincipal = new ArrayList<>();
        listPrincipal.addAll(listRecycler);
    }

    public static class LandingViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView breeds;
        public TextView country;
        public TextView intelligence;

        public LandingViewHolder(View v) {
            super(v);
            img = v.findViewById(R.id.img);
            breeds = v.findViewById(R.id.breeds);
            country = v.findViewById(R.id.country);
            intelligence = v.findViewById(R.id.intelligence);
        }
    }

    @Override
    public int getItemCount() {
        return listRecycler.size();
    }

    @Override
    public LandingViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_landing, viewGroup, false);
        v.setOnClickListener(this);
        return new LandingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LandingViewHolder viewHolder, int i) {
        String urlString;
        if (listRecycler.get(i).getImage() != null) {
            JsonElement url = listRecycler.get(i).getImage().get("url");
            urlString = url.getAsString().replace("\"\"", "\"");
        } else {
            urlString = "http://www.allianceplast.com/wp-content/uploads/no-image.png";
        }
        Glide.with(context)
                .load(urlString)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_no_image)
                .fitCenter()
                .into(viewHolder.img);
        viewHolder.breeds.setText(listRecycler.get(i).getName());
        viewHolder.country.setText(listRecycler.get(i).getOrigin());
        viewHolder.intelligence.setText("IQ: " + listRecycler.get(i).getIntelligence());
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public void filterSearch(String textSearch) {
        int size = textSearch.length();
        listRecycler.clear();
        if (size == 0) {
            listRecycler.addAll(listPrincipal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<ModelLanding> collection = listPrincipal.stream()
                        .filter(i -> i.getName().toLowerCase().contains(textSearch.toLowerCase()))
                        .collect(Collectors.toList());
                listRecycler.addAll(collection);
            } else {
                for (ModelLanding list : listPrincipal) {
                    if (list.getName().toLowerCase().contains(textSearch.toLowerCase())) {
                        listRecycler.add(list);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }


}

