package Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import JavaIntefrace.ListenerInterface;
import models.Note;

@Dao
public interface NoteDao {

    @Insert
    void insertNotes(Note notes);

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> queryNote();

    @Delete
    int deleteNote(Note notes);

    @Update
    int updateNotes(Note notes);

}
