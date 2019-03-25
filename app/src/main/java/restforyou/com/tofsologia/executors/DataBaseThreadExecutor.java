package restforyou.com.tofsologia.executors;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DataBaseThreadExecutor implements Executor {
    private final Executor dbExecutor;

    public DataBaseThreadExecutor() {
        dbExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(@NonNull Runnable runnable) {
        dbExecutor.execute(runnable);
    }
}
