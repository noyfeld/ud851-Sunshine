package com.example.android.sunshine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    TextView tvDetails ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvDetails=(TextView)findViewById(R.id.tv_details);
        if (getIntent().hasExtra(Intent.EXTRA_TEXT)){
            tvDetails.setText(getIntent().getStringExtra(Intent.EXTRA_TEXT));
        }
        Toolbar tb = (Toolbar)findViewById(R.id.toolbar_up_action);
        tb.setTitle("MainActivity");
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
