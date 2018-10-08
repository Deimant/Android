package com.example.arunas.short_code_notepad;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE_NAME = "com.example.Short_code_notepad.MESSAGE_NAME";
    public static final String EXTRA_MESSAGE_BODY = "com.example.Short_code_notepad.MESSAGE_BODY";

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

        noteText.setKeyListener(null);

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

                String selectedItem = (String) parent.getItemAtPosition(position);

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

                final String pavadinimas = (String) parent.getItemAtPosition(position);
                deleteFile(pavadinimas + ".txt");

                myAdapter.remove(pavadinimas);

                return false;
            }
        });
    }

    public void deleteMessage(View view) {
        nameText = (TextView) findViewById(R.id.nameText);
        String editName = nameText.getText().toString();

        if (editName != "") {
        deleteFile(nameText.getText() + ".txt");
        finish();
        startActivity(getIntent());
        } else {
            Toast.makeText(MainActivity.this, "Select a note to delete.", Toast.LENGTH_SHORT).show();
        }
    }

    public void newMessage(View view) {
        Intent intent = new Intent(this, DisplayNote.class);
        startActivity(intent);
    }

    public void editMessage(View view) {

        nameText = (TextView) findViewById(R.id.nameText);
        noteText = (TextView) findViewById(R.id.noteText);

        String editName = nameText.getText().toString();
        String editNote = noteText.getText().toString();

        if (editName != "") {
            Intent intent = new Intent(this, DisplayNote.class);
            startActivity(intent);
            intent.putExtra(EXTRA_MESSAGE_NAME, editName);
            intent.putExtra(EXTRA_MESSAGE_BODY, editNote);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "Select a note to edit.", Toast.LENGTH_SHORT).show();
        }
    }
}
