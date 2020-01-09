package com.codingwithtushar.mynotes;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import Utils.Constant;
import ViewModel.NoteViewModel;
import models.Note;

public class NotesDetailActivity extends AppCompatActivity implements  View.OnClickListener{

    private static final String TAG = "NotesDetailActivity";
    private Note note;
    private EditText editText;
    NoteViewModel noteViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_detail_layout);
        if (getIntent().hasExtra(Constant.NOTE_SELECTED_ITEM)) {
            note = getIntent().getParcelableExtra(Constant.NOTE_SELECTED_ITEM);
        }

        ImageButton checkButton = findViewById(R.id.note_check_button_image);
        ImageButton backButton = findViewById(R.id.note_back_button_image);
        backButton.setOnClickListener(this);
        checkButton.setOnClickListener(this);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        editText = findViewById(R.id.note_edit_text);
        if (note != null) {
            editText.setText(note.getTitle());
            editText.setFocusable(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.note_check_button_image:
                Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
                break;

            case R.id.note_back_button_image:
                insertOrUpdateData();
                finish();
                break;
        }
    }

    private void insertOrUpdateData() {
        String text = editText.getText().toString();
        if (note != null) {
            note.setTitle(text);
            noteViewModel.update(note);
        } else {
            if (!TextUtils.isEmpty(text)) {
                noteViewModel.insert(text);
            }
        }
    }
}
