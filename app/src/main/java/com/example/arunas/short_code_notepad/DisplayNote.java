package com.example.arunas.short_code_notepad;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;

public class DisplayNote extends AppCompatActivity {

    EditText enterNoteName;
    EditText enterNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_note);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(255, 255, 204));

        enterNote = (EditText) findViewById(R.id.enterNote);
    }

    public void saveMessage(View view) {
        enterNote = (EditText) findViewById(R.id.enterNote);
        enterNoteName = (EditText) findViewById(R.id.enterNoteName);

        String noteText = enterNote.getText().toString();
        String noteName = enterNoteName.getText().toString();

        try {
            FileOutputStream fout = openFileOutput(noteName + ".txt", MODE_PRIVATE);
            fout.write(noteText.getBytes());
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error Occured: " + e, Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
