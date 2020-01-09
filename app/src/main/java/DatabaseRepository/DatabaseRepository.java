package DatabaseRepository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import Dao.NoteDao;
import database.NoteDatabase;
import models.Note;

public class DatabaseRepository {

    private NoteDao noteDao;
    private NoteDatabase noteDatabase;
    public DatabaseRepository(Application application) {
        noteDatabase = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.getNoteDao();
    }

    public void insert(String text) {
        final Note note = new Note(text, text, String.valueOf(System.currentTimeMillis()));
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.insertNotes(note);
            }
        });
    }

    public LiveData<List<Note>> getDataFromDatabase() {
        return noteDao.queryNote();
    }

    public void updateNotes(final Note note) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.updateNotes(note);
            }
        });
    }

    public void deleteNote(final Note note) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.deleteNote(note);
            }
        });
    }

}
