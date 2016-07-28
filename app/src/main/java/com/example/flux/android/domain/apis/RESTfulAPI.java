package com.example.flux.android.domain.apis;


import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by ntop on 16/7/28.
 */
public class RESTfulAPI {
    public void sendMessage() {
        sleep();
    }
    /**
     * 随机睡3秒
     */
    private static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(new Random(3).nextInt());
        } catch (Exception ignore){}
    }
}
