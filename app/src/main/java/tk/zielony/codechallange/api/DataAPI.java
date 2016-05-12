package tk.zielony.codechallange.api;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Marcin on 2016-05-12.
 * <p/>
 * Abstract API class, can be implemented as local API, or network API.
 * Cache can be added here, so all implementations can use it.
 */
public abstract class DataAPI {
    protected static final String POST = "/posts";
    protected static final String COMMENT = "/comments";

    static final Queue<Runnable> taskQueue = new LinkedList<>();
    static Thread networkThread = new Thread() {
        @Override
        public void run() {
            while (true) {
                Runnable r;
                synchronized (taskQueue) {
                    try {
                        while (taskQueue.isEmpty())
                            taskQueue.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                    r = taskQueue.remove();
                    taskQueue.notify();
                }
                r.run();
            }
        }
    };

    protected static DataAPI instance;

    public static void init(DataAPI implementation) {
        instance = implementation;
        networkThread.start();
    }

    public static void shutdown() {
        networkThread.interrupt();
    }

    public static void addTask(Runnable runnable) {
        synchronized (taskQueue) {
            taskQueue.add(runnable);
            taskQueue.notify();
        }
    }

    public static <Type2> void get(final String endpoint, final DataEvent<Type2> event) {
        addTask(new Runnable() {
            @Override
            public void run() {
                try {
                    Type2 data = instance.getInternal(endpoint, event.getDataClass());
                    event.setData(data);
                    EventBus.getDefault().post(event);
                } catch (Exception e) {
                    EventBus.getDefault().post(new ExceptionEvent(e));
                }
            }
        });
    }

    protected abstract <Type2> Type2 getInternal(final String endpoint, Class<Type2> dataClass) throws APIException;

}
