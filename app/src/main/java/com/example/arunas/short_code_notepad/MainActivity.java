package com.example.arunas.short_code_notepad;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.Short_code_notepad.MESSAGE";

    ListView theListView;
    Button newButton, editButton, deleteButton;
    TextView noteText, nameText;
    TextView noteNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(255, 255, 204));

        newButton = (Button) findViewById(R.id.newButton);
        editButton = (Button) findViewById(R.id.editButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        noteText = (TextView) findViewById(R.id.noteText);
        nameText = (TextView) findViewById(R.id.nameText);
        theListView = (ListView) findViewById(R.id.myListView);

        File path = new File(getFilesDir().getAbsolutePath());

        File list[] = path.listFiles();

        final List fileList = new ArrayList();

        for (File l : list) {
            fileList.add(l.getName().replaceFirst("[.][^.]+$", ""));
        }

        final ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fileList);

        theListView.setAdapter(myAdapter);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);
                int pos = position;

                // Display the selected item text on TextView
//                text.setText("Your favorite : " + selectedItem);

                int c;
                noteText.setText("");
                try {
                    FileInputStream fin = openFileInput(selectedItem + ".txt");

                    while ((c = fin.read()) != -1) {
                        nameText.setText(selectedItem);
                        noteText.setText((noteText.getText().toString() + Character.toString((char) c)));
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error Occured: " + e, Toast.LENGTH_LONG).show();
                }
            }


        });

        theListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                int selItPos = theListView.getSelectedItemPosition();

                final String pavadinimas =  (String) theListView.getItemAtPosition(selItPos);

                noteText.setText("Your favorite : " + pavadinimas);

//                deleteFile(pavadinimas + ".txt");


                return false;
            }

        });


    }

    public void buttonAction(View v) {
        final EditText fileName = new EditText(this);
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setView(fileName);

        if (v.getId() == R.id.newButton) {
            Intent intent = new Intent(this, DisplayNote.class);
            startActivity(intent);
        }
    }


    public void deleteMessage(View view) {

        deleteFile(nameText.getText() + ".txt");

         finish();
         startActivity(getIntent());
    }

    public void newMessage(View view) {

        Intent intent = new Intent(this, DisplayNote.class);
        startActivity(intent);
    }

}
