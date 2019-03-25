package restforyou.com.tofsologia.executors;

import java.util.concurrent.Executor;

public class AppExecutors {
    private final Executor dbExecutor;
    private final Executor mainThreadExecutor;

    public AppExecutors(Executor dbExecutor, Executor mainThreadExecutor) {
        this.dbExecutor = dbExecutor;
        this.mainThreadExecutor = mainThreadExecutor;
    }

    public AppExecutors(){

        this(new DataBaseThreadExecutor(), new MainThreadExecutor());
    }

    public Executor getDbExecutor() {

        return dbExecutor;
    }

    public Executor getMainThreadExecutor(){

        return mainThreadExecutor;
    }
}
