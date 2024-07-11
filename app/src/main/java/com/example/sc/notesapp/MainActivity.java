package com.example.sc.notesapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_NOTE_REQUEST = 1;
    private static final int EDIT_NOTE_REQUEST = 2;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("NotesApp-- Create Notes");


        recyclerView = findViewById(R.id.recyclerview_id);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        noteAdapter = new NoteAdapter(note -> {
            Intent intent = new Intent(MainActivity.this, Add_New.class);
            intent.putExtra(Add_New.EXTRA_ID, note.getId());
            intent.putExtra(Add_New.EXTRA_TITLE, note.getTitle());
            intent.putExtra(Add_New.EXTRA_CONTENT, note.getContent());
            startActivityForResult(intent, EDIT_NOTE_REQUEST);
        });
        recyclerView.setAdapter(noteAdapter);

        loadNotes();

        FloatingActionButton addButton = findViewById(R.id.floatingActionButton);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Add_New.class);
            startActivityForResult(intent, ADD_NOTE_REQUEST);
        });

        // Implement swipe-to-delete functionality
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Note noteToDelete = noteAdapter.getNoteAt(position);
                NoteDatabase.databaseWriteExecutor.execute(() -> {
                    NoteDao noteDao = NoteDatabase.getDatabase(MainActivity.this).noteDao();
                    noteDao.delete(noteToDelete);
                });
                noteAdapter.removeNoteAt(position);
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void loadNotes() {
        NoteDatabase.databaseWriteExecutor.execute(() -> {
            NoteDao noteDao = NoteDatabase.getDatabase(MainActivity.this).noteDao();
            List<Note> notes = noteDao.getAllNotesSync(); // Synchronous method to get notes
            runOnUiThread(() -> {
                noteAdapter.setNotes(notes);
            });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra(Add_New.EXTRA_TITLE);
            String content = data.getStringExtra(Add_New.EXTRA_CONTENT);

            NoteDatabase.databaseWriteExecutor.execute(() -> {
                NoteDao noteDao = NoteDatabase.getDatabase(MainActivity.this).noteDao();
                Note note;
                if (requestCode == ADD_NOTE_REQUEST) {
                    note = new Note(title, content);
                    noteDao.insert(note);
                } else if (requestCode == EDIT_NOTE_REQUEST) {
                    int id = data.getIntExtra(Add_New.EXTRA_ID, -1);
                    if (id != -1) {
                        note = new Note(title, content);
                        note.setId(id);
                        noteDao.update(note);
                    }
                }
                loadNotes();
            });
        }
    }
}
