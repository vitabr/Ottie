package restforyou.com.tofsologia;

import android.app.Application;
import android.arch.persistence.room.Room;

import restforyou.com.tofsologia.executors.AppExecutors;
import restforyou.com.tofsologia.executors.DataBaseThreadExecutor;
import restforyou.com.tofsologia.executors.MainThreadExecutor;
import restforyou.com.tofsologia.model.AppDataBase;
import restforyou.com.tofsologia.utils.Constants;

public class App extends Application {
    public static App instance;
    private AppDataBase dataBase;
    private static AppExecutors sExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        MLKit.init();
        instance = this;
        dataBase = Room.databaseBuilder(this, AppDataBase.class, Constants.DATABASE).build();
        sExecutors = new AppExecutors();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDataBase getDataBase() {
        return dataBase;
    }

    public static MainThreadExecutor getAppMainExecutor(){
        return (MainThreadExecutor)sExecutors.getMainThreadExecutor();
    }
    public static DataBaseThreadExecutor getAppDBExecutor(){
        return (DataBaseThreadExecutor)sExecutors.getDbExecutor();
    }
}
