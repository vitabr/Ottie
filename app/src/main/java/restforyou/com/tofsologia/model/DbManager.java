package restforyou.com.tofsologia.model;

import java.util.ArrayList;
import java.util.List;

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

    public void updateRecord(final Record record, final OnResult<Void> recordUpdatedListener){
        App.getAppDBExecutor().execute(new Runnable() {
            @Override
            public void run() {
                recordsDao.update(record);
                App.getAppMainExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(recordUpdatedListener != null){
                            recordUpdatedListener.onResult(null);
                        }
                    }
                });
            }
        });
    }

    public void addRecord(final Record record, final OnResult<Void> recordAddedListener){
        App.getAppDBExecutor().execute(new Runnable() {
            @Override
            public void run() {
                recordsDao.add(record);
                App.getAppMainExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        recordAddedListener.onResult(null);
                    }
                });
            }
        });
    }

    public void getAllRecords(final OnResult<List<Record>> listener) {
        App.getAppDBExecutor().execute(new Runnable() {
            @Override
            public void run() {
                final List<Record> activeAlarmsList = new ArrayList<>();
                activeAlarmsList.addAll(recordsDao.getAll());
                App.getAppMainExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        listener.onResult(activeAlarmsList);
                    }
                });

            }
        });
    }

    public void getRecordById(final long id, final OnResult<Record> onRecordsReceivedListener){
        App.getAppDBExecutor().execute(new Runnable() {
            @Override
            public void run() {
                final Record record = recordsDao.getRecordById(id);
                App.getAppMainExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        onRecordsReceivedListener.onResult(record);
                    }
                });
            }
        });
    }

    public interface OnResult<T>{
        void onResult(T result);
    }

}




