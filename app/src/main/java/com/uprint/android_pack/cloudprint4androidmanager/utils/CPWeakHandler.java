package com.uprint.android_pack.cloudprint4androidmanager.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * void Memory Leak Handler
 * copyright https://github.com/badoo/android-weak-handler
 */
public class CPWeakHandler {
    private final Handler.Callback mCallback;
    private Lock mLock = new ReentrantLock();
    private ExecHandler execHandler;
    final ChainedRef mRunnables = new ChainedRef(mLock, null);

    public CPWeakHandler() {
        mCallback = null;
        execHandler = new ExecHandler();
    }

    public CPWeakHandler(@Nullable Handler.Callback callback) {
        mCallback = callback;
        execHandler = new ExecHandler(new WeakReference<Handler.Callback>(callback));
    }

    public CPWeakHandler(@NonNull Looper looper) {
        mCallback = null;
        execHandler = new ExecHandler(looper);
    }

    public CPWeakHandler(@NonNull Looper looper, @NonNull Handler.Callback callback) {
        mCallback = callback;
        execHandler = new ExecHandler(looper, new WeakReference<>(callback));
    }

    public final boolean post(@NonNull Runnable r) {
        return execHandler.post(wrapRunnable(r));
    }

    public final boolean postAtTime(@NonNull Runnable r, long uptimeMillis) {
        return execHandler.postAtTime(wrapRunnable(r), uptimeMillis);
    }

    public final boolean postAtTime(Runnable r, Object token, long uptimeMillis) {
        return execHandler.postAtTime(wrapRunnable(r), token, uptimeMillis);
    }

    public final boolean postDelayed(Runnable r, long delayMillis) {
        return execHandler.postDelayed(wrapRunnable(r), delayMillis);
    }

    public final boolean postAtFrontOfQueue(Runnable r) {
        return execHandler.postAtFrontOfQueue(wrapRunnable(r));
    }

    public final void removeCallbacks(Runnable r) {
        final WeakRunnable runnable = mRunnables.remove(r);
        if (runnable != null) {
            execHandler.removeCallbacks(runnable);
        }
    }

    public final void removeCallbacks(Runnable r, Object token) {
        final WeakRunnable runnable = mRunnables.remove(r);
        if (runnable != null) {
            execHandler.removeCallbacks(runnable, token);
        }
    }

    public final boolean sendMessage(Message msg) {
        return execHandler.sendMessage(msg);
    }

    public final boolean sendEmptyMessage(int what) {
        return execHandler.sendEmptyMessage(what);
    }

    public final boolean sendEmptyMessageDelayed(int what, long delayMillis) {
        return execHandler.sendEmptyMessageDelayed(what, delayMillis);
    }

    public final boolean sendEmptyMessageAtTime(int what, long uptimeMillis) {
        return execHandler.sendEmptyMessageAtTime(what, uptimeMillis);
    }

    public final boolean sendMessageDelayed(Message msg, long delayMillis) {
        return execHandler.sendMessageDelayed(msg, delayMillis);
    }

    public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
        return execHandler.sendMessageAtTime(msg, uptimeMillis);
    }

    public final boolean sendMessageAtFrontOfQueue(Message msg) {
        return execHandler.sendMessageAtFrontOfQueue(msg);
    }

    public final void removeMessages(int what) {
        execHandler.removeMessages(what);
    }

    public final void removeMessages(int what, Object object) {
        execHandler.removeMessages(what, object);
    }

    public final void removeCallbacksAndMessages(Object token) {
        execHandler.removeCallbacksAndMessages(token);
    }

    public final boolean hasMessages(int what) {
        return execHandler.hasMessages(what);
    }

    public final boolean hasMessages(int what, Object object) {
        return execHandler.hasMessages(what, object);
    }

    public final Looper getLooper() {
        return execHandler.getLooper();
    }

    private Runnable wrapRunnable(Runnable r) {
        if (r == null) {
            throw new NullPointerException("runnbale r can't be null");
        }

        final ChainedRef hardRef = new ChainedRef(mLock, r);
        mRunnables.insertAfter(hardRef);
        return hardRef.wrapper;
        
    }

    private static class ExecHandler extends Handler {
        private final WeakReference<Callback> mCallback;

        ExecHandler() {
            mCallback = null;
        }

        ExecHandler(WeakReference<Handler.Callback> callback) {
            mCallback = callback;
        }

        ExecHandler(Looper looper) {
            super(looper);
            mCallback = null;
        }

        ExecHandler(Looper looper, WeakReference<Handler.Callback> callback) {
            super(looper);
            mCallback = callback;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (mCallback == null) {
                return;
            }
            final Handler.Callback callback = mCallback.get();
            if (callback == null) {
                return;
            }
            callback.handleMessage(msg);
        }
    }

    static class WeakRunnable implements Runnable {
        private final WeakReference<Runnable> mDelegate;
        private final WeakReference<ChainedRef> mReference;

        public WeakRunnable(WeakReference<Runnable> delegate, WeakReference<ChainedRef> reference) {
            mDelegate = delegate;
            mReference = reference;
        }


        @Override
        public void run() {
            final Runnable delegate = mDelegate.get();
            final ChainedRef reference = mReference.get();
            if (reference != null) {
                reference.remove();
            }
            if (delegate != null) {
                delegate.run();
            }
        }
    }

    static class ChainedRef {
        @Nullable
        ChainedRef next;
        @Nullable
        ChainedRef prev;
        @NonNull
        final Runnable runnable;
        @NonNull
        final WeakRunnable wrapper;
        @NonNull
        Lock lock;

        public ChainedRef(@NonNull Lock lock, @NonNull Runnable r) {
            this.runnable = r;
            this.lock = lock;
            this.wrapper = new WeakRunnable(new WeakReference<>(r), new WeakReference<>(this));
        }

        public WeakRunnable remove() {
            lock.lock();
            try {
                if (prev != null) {
                    prev.next = next;
                }
                if (next != null) {
                    next.prev = prev;
                }
                prev = null;
                next = null;
            } finally {
                lock.unlock();
            }
            return wrapper;
        }

        public void insertAfter(@NonNull ChainedRef candidate) {
            lock.lock();
            try {
                if (this.next != null) {
                    this.next.prev = candidate;
                }

                candidate.next = this.next;
                this.next = candidate;
                candidate.prev = this;
            } finally {
                lock.unlock();
            }
        }

        @Nullable
        public WeakRunnable remove(Runnable obj) {
            lock.lock();
            try {
                ChainedRef curr = this.next; // Skipping head
                while (curr != null) {
                    if (curr.runnable == obj) { // We do comparison exactly how Handler does inside
                        return curr.remove();
                    }
                    curr = curr.next;
                }
            } finally {
                lock.unlock();
            }
            return null;
        }
    }
}
