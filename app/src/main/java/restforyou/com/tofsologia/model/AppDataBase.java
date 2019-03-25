package restforyou.com.tofsologia.model;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = Record.class, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract RecordsDao recordsDao();
}
