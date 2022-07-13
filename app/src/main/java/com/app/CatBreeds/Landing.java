package com.app.catbreeds;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.catbreeds.adapter.AdapterLandig;
import com.app.catbreeds.model.ModelLanding;
import com.app.catbreeds.network.ClientInstance;
import com.app.catbreeds.network.GetDataService;
import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Landing extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerLanding;
    private AdapterLandig adapter;
    private ProgressDialog progressDialog;
    private SearchView textSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        textSearch = findViewById(R.id.textSearch);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        GetDataService service = ClientInstance.getInstanceRetrofit().create(GetDataService.class);
        Call<List<ModelLanding>> call = service.getAll();
        call.enqueue(new Callback<List<ModelLanding>>() {
            @Override
            public void onResponse(Call<List<ModelLanding>> call, Response<List<ModelLanding>> response) {
                progressDialog.dismiss();
                ResponeList(response.body());
            }

            @Override
            public void onFailure(Call<List<ModelLanding>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Landing.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });


        textSearch.setOnQueryTextListener(this);
    }

    private void ResponeList(List<ModelLanding> listLanding) {
        recyclerLanding = findViewById(R.id.recycler);
        recyclerLanding.setHasFixedSize(true);
        RecyclerView.LayoutManager lManager = new LinearLayoutManager(this);
        recyclerLanding.setLayoutManager(lManager);
        adapter = new AdapterLandig(this, listLanding);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Landing.this, Detail.class);
                intent.putExtra("breed", listLanding.get(recyclerLanding.getChildAdapterPosition(v)).getName());
                intent.putExtra("description", listLanding.get(recyclerLanding.getChildAdapterPosition(v)).getDescription());
                String urlString;
                if (listLanding.get(recyclerLanding.getChildAdapterPosition(v)).getImage() != null) {
                    JsonElement url = listLanding.get(recyclerLanding.getChildAdapterPosition(v)).getImage().get("url");
                    urlString= url.getAsString().replace("\"\"", "\"");
                }
                else{
                    urlString = "http://www.allianceplast.com/wp-content/uploads/no-image.png";
                }
                intent.putExtra("image", urlString);
                startActivity(intent);
            }
        });
        recyclerLanding.setAdapter(adapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (textSearch.getQuery().length() != 0)
            adapter.filterSearch(newText);

        return false;
    }
}