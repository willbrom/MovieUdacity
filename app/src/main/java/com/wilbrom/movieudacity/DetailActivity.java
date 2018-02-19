package com.wilbrom.movieudacity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.wilbrom.movieudacity.models.Results;

public class DetailActivity extends AppCompatActivity {

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title = (TextView) findViewById(R.id.title);
        Results results = (Results) getIntent().getParcelableExtra("par");
        title.setText(results.getTitle());
    }
}
