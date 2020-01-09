package ViewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import DatabaseRepository.DatabaseRepository;
import models.Note;

public class NoteViewModel extends AndroidViewModel {


    private LiveData<List<Note>> noteList = new MutableLiveData<>();
    private DatabaseRepository databaseRepository;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        databaseRepository = new DatabaseRepository(application);
    }

    public void insert(String text) {
        databaseRepository.insert(text);
    }

    public LiveData<List<Note>> getData() {
        LiveData<List<Note>> data = databaseRepository.getDataFromDatabase();
        return data;
    }

    public void update(Note note) {
        databaseRepository.updateNotes(note);
    }

    public void deleteNote(Note note) {
        databaseRepository.deleteNote(note);
    }
}
