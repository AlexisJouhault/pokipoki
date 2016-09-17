package com.pokemeows.pokipoki.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pokemeows.pokipoki.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateEventActivity extends AppCompatActivity {

    @BindView(R.id.editTextName)
    EditText editTextName;
    @BindView(R.id.editTextDate)
    EditText editTextDate;
    @BindView(R.id.SubmitEvent)
    Button submitEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        ButterKnife.bind(this);



        editTextName.getText();
        editTextDate.getText();

//        String hello = null;
//        Intent intent = getIntent();
//
//        hello =  intent.getStringExtra("hello");
//
//        View helloView = findViewById(R.id.hello_text);
//        TextView helloTextView = (TextView) helloView;
//
//        if (hello != null) {
//            helloTextView.setText(hello);
//        }

//        createEventButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @OnClick(R.id.SubmitEvent)
    public void onSubmitClick() {
        String name = editTextName.getText().toString();
        String date = editTextDate.getText().toString();
        String eventMessage = getResources().getString(R.string.submission);
        Toast.makeText(this, eventMessage + " " + name + " " + date, Toast.LENGTH_LONG).show();
    }
}
