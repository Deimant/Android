package com.example.egle.notetaker;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    private boolean mIsViewingOrUpdating;
    private long mNoteCreationTime;
    private String mFileName;
    private Note mLoadedNote = null;

    private EditText mEtTitle;
    private EditText mEtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mEtTitle = (EditText) findViewById(R.id.note_ed_title);
        mEtContent = (EditText) findViewById(R.id.note_ed_content);

        mFileName = getIntent().getStringExtra(Utilities.EXTRAS_NOTE_FILENAME);
        if(mFileName != null && !mFileName.isEmpty() && mFileName.endsWith(Utilities.FILE_EXTENSION)) {
            mLoadedNote = Utilities.getNoteByName(getApplicationContext(), mFileName);

            if(mLoadedNote != null) {
                mEtTitle.setText(mLoadedNote.getmTitle());
                mEtContent.setText(mLoadedNote.getmContent());
                mNoteCreationTime = mLoadedNote.getmDateTime();
                mIsViewingOrUpdating = true;
            }
         } else {
            mNoteCreationTime = System.currentTimeMillis();
            mIsViewingOrUpdating = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(mIsViewingOrUpdating) {
            getMenuInflater().inflate(R.menu.menu_note_view, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_note_add, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_save_note:
            case R.id.action_update:
                validateAndSaveNote();
                break;

            case R.id.action_delete:
                actionDelete();
            break;

            case R.id.action_cancel:
                actionCancel();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        actionCancel();
    }

    private void actionDelete() {

        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this)
                .setTitle("delete note")
                .setMessage("You are about to delete your note, are you sure?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(mLoadedNote != null && Utilities.deleteFile(getApplicationContext(), mFileName)) {
                            Toast.makeText(NoteActivity.this, mLoadedNote.getmTitle() + " is deleted",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(NoteActivity.this, "can not delete the note '"
                                            + mLoadedNote.getmTitle() + "'"
                                    , Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                })

                .setNegativeButton("NO", null);

        dialogDelete.show();

    }

    private void actionCancel() {
        if (!checkNoteAltred()) {
            finish();
        } else {
            AlertDialog.Builder dialogCancel = new AlertDialog.Builder(this)
                    .setTitle("discard changes...")
                    .setMessage("are you sure you do not want to save changes to this note?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("NO", null);
            dialogCancel.show();
        }
    }

    private boolean checkNoteAltred() {
        if(mIsViewingOrUpdating) {
            return mLoadedNote != null && (!mEtTitle.getText().toString().equalsIgnoreCase(mLoadedNote.getmTitle())
                    || !mEtContent.getText().toString().equalsIgnoreCase(mLoadedNote.getmContent()));
        } else {
            return !mEtTitle.getText().toString().isEmpty() || !mEtContent.getText().toString().isEmpty();
        }
    }


    private void validateAndSaveNote() {

        String title = mEtTitle.getText().toString();
        String content = mEtContent.getText().toString();

        if(title.isEmpty()) {
            Toast.makeText(NoteActivity.this, "please enter a title!"
                    , Toast.LENGTH_SHORT).show();
            return;
        }

        if(content.isEmpty()) {
            Toast.makeText(NoteActivity.this, "please enter a content for your note!"
                    , Toast.LENGTH_SHORT).show();
            return;
        }

        if(mLoadedNote != null) {
            mNoteCreationTime = mLoadedNote.getmDateTime();
        } else {
            mNoteCreationTime = System.currentTimeMillis();
        }

        if(Utilities.saveNote(this, new Note(mNoteCreationTime, title, content))) {
            Toast.makeText(this, "your note is saved!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "cant not save the note, please make sure you have enough space on your device",
                    Toast.LENGTH_SHORT).show();
        }
        finish();
        }

     }


