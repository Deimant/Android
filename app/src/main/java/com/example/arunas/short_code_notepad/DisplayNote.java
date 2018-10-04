package com.example.arunas.short_code_notepad;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;

public class DisplayNote extends AppCompatActivity {

    EditText noteName;
    EditText noteBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_note);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(255, 255, 204));

//      Get the Intent that started this activity and extract the string

        Intent intent = getIntent();
        String editName = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_NAME);
        String editNote = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_BODY);

//      Capture the layout 's TextView and set the string as its text

        noteName = (EditText) findViewById(R.id.noteName);
        noteName.setText(editName);

        noteBody = (EditText) findViewById(R.id.noteBody);
        noteBody.setText(editNote);
    }

    //Todo when saving put cursor on the name view on opening of the body

    public void saveMessage(View view) {

        noteName = (EditText) findViewById(R.id.noteName);
        noteBody = (EditText) findViewById(R.id.noteBody);

        String noteNameText = noteName.getText().toString();
        String noteText = noteBody.getText().toString();

        try {
            FileOutputStream fout = openFileOutput(noteNameText + ".txt", MODE_PRIVATE);
            fout.write(noteText.getBytes());
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error Occured: " + e, Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
