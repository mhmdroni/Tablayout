package com.example.tablayout;

import android.os.Bundle;
import android.widget.TextView;

import com.example.tablayout.model.WordModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (getSupportActionBar() !=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        TextView tvWord = findViewById(R.id.tv_word);
        TextView tvDescription = findViewById(R.id.tv_description);

        WordModel wordModel = getIntent().getParcelableExtra("word");
        tvWord.setText(wordModel.getWord());
        tvDescription.setText(wordModel.getDescription());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
