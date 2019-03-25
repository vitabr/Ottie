package restforyou.com.tofsologia.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface RecordsDao {
    @Insert
    void add(Record record);

    @Delete
    void delete(Record record);

    @Update
    void update(Record record);

    @Query("SELECT * FROM records")
    List<Record> getAll();

    @Query("SELECT * FROM records WHERE id = :recordId")
    Record getRecordById(long recordId);
}
