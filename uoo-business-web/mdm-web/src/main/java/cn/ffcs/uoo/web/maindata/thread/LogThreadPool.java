package cn.ffcs.uoo.web.maindata.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogThreadPool {
    private static ExecutorService cachedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*4);
    public static void submitTask(){
        //cachedThreadPool.submit(task)
    }
}
