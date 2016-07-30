package com.example.flux.android;

import android.app.Application;

import com.example.flux.android.dispatcher.Dispatcher;
import com.example.flux.android.stores.ThreadStore;
import com.example.flux.android.stores.MessageStore;

/**
 * Created by ntop on 16/7/27.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initFlux();
    }

    private void initFlux() {
        Flux.init(Dispatcher.get());
        Flux.register(ThreadStore.class);
        Flux.register(MessageStore.class);
    }

    // 根据内存来管理Flux中Store的内存占用
    // TODO
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

    }
}
