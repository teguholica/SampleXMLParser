package com.teguholica.samplexmlparser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent i = getIntent();

        TextView txtTitle = (TextView) findViewById(R.id.title);
        TextView txtDesc = (TextView) findViewById(R.id.desc);

        txtTitle.setText(i.getStringExtra("title"));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            txtDesc.setText(Html.fromHtml(i.getStringExtra("desc"), Html.FROM_HTML_MODE_LEGACY));
        } else {
            txtDesc.setText(Html.fromHtml(i.getStringExtra("desc")));
        }
    }
}
