package com.example.arunas.short_code_notepad;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView theListView;
    Intent myIntent;


    Button newButton, saveButton, openButton;
    EditText text;
    TextView noteNames;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        theListView = (ListView) findViewById(R.id.myListView);

        String[ ] myData = {"Visual Basic .NET", "Java", "Android", "C# .NET", "PHP", "C++", "Scala", "Ruby on Rails", "Javascript", "HTML", "Python", "Swift"};

         File path = new File("/storage/" + "");


        File list[] = path.listFiles();
        for( int i=0; i< list.length; i++)
        {

        }

        getFilesDir().listFiles().toString();


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, myData );

        theListView.setAdapter( myAdapter );

        newButton = (Button) findViewById(R.id.newButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        openButton = (Button) findViewById(R.id.openButton);
        text = (EditText) findViewById(R.id.text);
        noteNames = findViewById(R.id.noteNames);


    }



    public void buttonAction(View v) {
        final EditText fileName = new EditText(this);
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setView(fileName);

        if (v.getId() == R.id.saveButton) {
            ad.setMessage("Save File");

            ad.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        FileOutputStream fout = openFileOutput(fileName.getText().toString() + ".txt", MODE_PRIVATE);
                        fout.write(text.getText().toString().getBytes());


//                        noteNames.setText(fileName.getText().toString());


                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error Occured: " + e, Toast.LENGTH_LONG).show();
                    }
                }
            });

            ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            ad.show();

        }

        if (v.getId() == R.id.openButton) {
            ad.setMessage("Open File");

            ad.setPositiveButton("Open", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    int c;
                    text.setText("");

                    try {
                        FileInputStream fin = openFileInput(fileName.getText().toString() + ".txt");

                        while ((c = fin.read()) != -1) {
                            text.setText((text.getText().toString() + Character.toString((char) c)));

                        }

//                        noteNames.setText(getFilesDir().listFiles().toString());


                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error Occured: " + e, Toast.LENGTH_LONG).show();
                    }
                }
            });

            ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            ad.show();
        }

        if (v.getId() == R.id.newButton) {
            text.setText("");
        }
    }
}

