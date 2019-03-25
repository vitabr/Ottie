package restforyou.com.tofsologia.model;

import android.util.Log;

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

    public void addRecord(final Record record, final onRecordAdded recordAddedListener){
        App.getAppDBExecutor().execute(new Runnable() {
            @Override
            public void run() {
                recordsDao.addRecord(record);
                App.getAppMainExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        recordAddedListener.onRecordAdded();
                    }
                });
            }
        });


    }

    public void getAllRecords(final onRecordsReceived listener) {
        App.getAppDBExecutor().execute(new Runnable() {
            @Override
            public void run() {
                final List<Record> activeAlarmsList = new ArrayList<>();
                activeAlarmsList.addAll(recordsDao.getAll());
                App.getAppMainExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        listener.onRecorsReceived(activeAlarmsList);
                    }
                });

            }
        });
    }

    public void getRecordById(final int id, final OnRecordReceived onRecordsReceivedListener){
        App.getAppDBExecutor().execute(new Runnable() {
            @Override
            public void run() {
                final Record record = recordsDao.getRecordById(id);
                App.getAppMainExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        ///Log.e("xxx", record.toString());
                        onRecordsReceivedListener.onRecordReceived(record);
                    }
                });
            }
        });

    }

    public interface onRecordAdded {
        void onRecordAdded();
    }

    public interface onRecordsReceived {
        void onRecorsReceived(List<Record> records);
    }

    public interface OnRecordReceived {
        void onRecordReceived(Record record);
    }



}




