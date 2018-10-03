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
    TextView text;
    TextView noteNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(255, 255, 204));

        newButton = (Button) findViewById(R.id.newButton);
        editButton = (Button) findViewById(R.id.editButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        text = (TextView) findViewById(R.id.text);
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

                // Display the selected item text on TextView
//                text.setText("Your favorite : " + selectedItem);

                Integer pos = theListView.getSelectedItemPosition();
                myAdapter.remove(fileList.get(0));


                int c;
                text.setText("");
                try {
                    FileInputStream fin = openFileInput(selectedItem + ".txt");

                    while ((c = fin.read()) != -1) {
                        text.setText((text.getText().toString() + Character.toString((char) c)));
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error Occured: " + e, Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    public void buttonAction(View v) {
        final EditText fileName = new EditText(this);
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setView(fileName);


        if (v.getId() == R.id.deleteButton) {
            ad.setMessage("Delete Record");

            ad.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
//                        deleteFile(fileName.getText().toString() + ".txt");
                        deleteFile("kkkkkk");
                        finish();
                        startActivity(getIntent());
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
            Intent intent = new Intent(this, DisplayNote.class);
            startActivity(intent);
        }
    }


//    public void deleteMessage(View view) {
//
////                Object failoVardas = theListView.getSelectedItem();
//
//
//        text.setText("Your favorite : ");
//
//            theListView.getSelectedItem().;
//
//
//        String uri = theListView.g;
//        File file = new File (uri);
//        file.delete();
//
////        try {
////            deleteFile();
////            finish();
////            startActivity(getIntent());
////        } catch (Exception e) {
////            Toast.makeText(getApplicationContext(), "Error Occured: " + e, Toast.LENGTH_LONG).show();
////        }
//    }
}


