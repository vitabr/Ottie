package restforyou.com.tofsologia;

import android.app.Application;
import android.arch.persistence.room.Room;

import restforyou.com.tofsologia.model.AppDataBase;
import restforyou.com.tofsologia.utils.Constants;

public class App extends Application {
    public static App instance;
    private AppDataBase dataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        MLKit.init();
        instance = this;
        dataBase = Room.databaseBuilder(this, AppDataBase.class, Constants.DATABASE).build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDataBase getDataBase() {
        return dataBase;
    }
}
