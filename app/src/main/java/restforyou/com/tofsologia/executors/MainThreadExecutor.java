package restforyou.com.tofsologia.executors;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

public class MainThreadExecutor implements Executor {
    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    @Override
    public void execute(@NonNull Runnable runnable) {
        mainThreadHandler.post(runnable);
    }
}
