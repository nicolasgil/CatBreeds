package com.app.catbreeds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView tv_scroll = findViewById(R.id.tv_scroll);
        TextView tv_breeds = findViewById(R.id.tv_breeds);
        ImageView img_detail = findViewById(R.id.img_detail);

        Intent intent = getIntent();
        Bundle intentExtras = intent.getExtras();

        if (intentExtras != null) {
            String breed = (String) intentExtras.get("breed");
            tv_breeds.setText(breed);
            String description = (String) intentExtras.get("description");
            tv_scroll.setText(description);
            String imagen = (String) intentExtras.get("image");
            Glide.with(this)
                    .load(String.valueOf(imagen))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_no_image)
                    .fitCenter()
                    .circleCrop()
                    .into(img_detail);

        }
        ImageButton arrow_back = findViewById(R.id.arrow_back);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}