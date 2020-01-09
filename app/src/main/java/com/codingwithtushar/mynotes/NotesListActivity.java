package com.codingwithtushar.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;

import JavaIntefrace.ListenerInterface;
import Utils.Constant;
import Utils.VerticalItemDecorator;
import ViewModel.NoteViewModel;
import adapter.NotesRecyclerAdapter;
import models.Note;

public class NotesListActivity extends AppCompatActivity implements ListenerInterface, View.OnClickListener {

    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    RecyclerView mRecyclerView;
    NotesRecyclerAdapter mAdapter;
    List<Note> mNotes = new ArrayList<>();
    NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        Button startNotesButton = findViewById(R.id.start_notes);
        startNotesButton.setOnClickListener(this);

        setSupportActionBar(toolbar);
        setTitle("Notes");
        initializedRecyclerView();

        noteViewModel.getData().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> note) {
                if (note != null && note.size() > 0) {
                    mNotes = note;
                    mAdapter.setNotes(note);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initializedRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalItemDecorator verticalItemDecorator = new VerticalItemDecorator(10);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        mRecyclerView.addItemDecoration(verticalItemDecorator);
        mAdapter = new NotesRecyclerAdapter(mNotes, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClickEvent(int position) {
        if (mNotes != null  ) {
            Intent intent = new Intent(this, NotesDetailActivity.class);
            intent.putExtra(Constant.NOTE_SELECTED_ITEM, mNotes.get(position));
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        }

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_notes:
                Intent intent = new Intent(this, NotesDetailActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        }
    }

    private void deleteNote(Note note) {
        mNotes.remove(note);
        mAdapter.notifyDataSetChanged();
        noteViewModel.deleteNote(note);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            deleteNote(mNotes.get(viewHolder.getAdapterPosition()));
        }
    };
}
