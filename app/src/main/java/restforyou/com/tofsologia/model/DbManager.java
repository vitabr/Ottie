package restforyou.com.tofsologia.model;

import restforyou.com.tofsologia.App;

public class DbManager {
    private static DbManager instance = null;
    private RecordsDao recordsDao;
    private AppDataBase db;

    private DbManager() {
        db = App.getInstance().getDataBase();
        recordsDao = db.recordsDao();
    }

    public static DbManager getInstance() {
        if (instance == null)
            instance = new DbManager();

        return instance;
    }

    public void addRecord(final Record record){
        //todo add executors
        new Thread(new Runnable() {
            @Override
            public void run() {
              recordsDao.addRecord(record);
            }
        }).start();
    }

}
