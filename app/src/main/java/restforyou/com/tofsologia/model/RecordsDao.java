package restforyou.com.tofsologia.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;

@Dao
public interface RecordsDao {
    @Insert
    void addRecord(Record record);

    @Delete
    void deleteRecord(Record record);
}
