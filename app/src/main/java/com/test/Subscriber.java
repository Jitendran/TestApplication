package com.test;

import androidx.recyclerview.widget.RecyclerView;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class Subscriber {
    private static WeakReference<RecyclerView> recyclerViewWeakReference;
    private static Disposable subscriber;
    private static final List<String> LOGS = new CopyOnWriteArrayList<>();
    private static final AtomicInteger REF_COUNT = new AtomicInteger(0);
    private static final ReentrantLock LOCK = new ReentrantLock();

    static void init() {
        REF_COUNT.incrementAndGet();
    }

    static void init(RecyclerView recyclerView) {
        LOCK.lock();
        try {
            Subscriber.recyclerViewWeakReference = new WeakReference<>(recyclerView);
            if (subscriber == null) {
                subscriber = MainActivity.RX.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                        subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        LOGS.add(s);
                        if (recyclerViewWeakReference.get() != null) {
                            recyclerViewWeakReference.get().setAdapter(new LogViewHolderAdapter(recyclerViewWeakReference.get().getContext(), LOGS));
                            recyclerViewWeakReference.get().smoothScrollToPosition(LOGS.size() - 1);
                        }
                    }
                });
                REF_COUNT.set(0);
            } else {
                REF_COUNT.incrementAndGet();
            }
        } finally {
            LOCK.unlock();
        }
    }

    static void done() {
        LOCK.lock();
        try {
            if (REF_COUNT.decrementAndGet() == 0) {
                subscriber.dispose();
                subscriber = null;
            }
        } finally {
            LOCK.unlock();
        }
    }
}
