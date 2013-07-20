package com.buaa.greenlife.util;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.view.LayoutInflater;
import android.view.View;

import com.buaa.greenlife.views.fragment.InflateListener;

public class AsyncInflater {

  private static AsyncInflater instance;
  private HandlerThread handlerThread;
  private Handler inflateHandler;
  private volatile boolean canAsyncInflate = true;
  private Context context;

  static {
    if (instance == null) {
      instance = new AsyncInflater();
    }
  }

  private AsyncInflater() {
    handlerThread = new HandlerThread("inflate thread", Process.THREAD_PRIORITY_DEFAULT);
    handlerThread.start();
    inflateHandler = new Handler(handlerThread.getLooper());
  }

  public static AsyncInflater getInstance() {
    return instance;
  }

  public void setContext(Context context) {
    this.context = context;
  }

  public void asyncInflate(final LayoutInflater inflater, final int layoutId,
      final WeakReference<Handler> handlerRef,
      final WeakReference<InflateListener> listenerRef) {
    // if (canAsyncInflate) {
    // Runnable inflateRunnable = new Runnable() {
    //
    // @Override
    // public void run() {
    // if (canAsyncInflate) {
    // asyncInflateView(inflater, layoutId, handlerRef, listenerRef);
    // } else {
    // postInflate(inflater, layoutId, handlerRef, listenerRef);
    // }
    //
    // }
    // };
    // inflateHandler.post(inflateRunnable);
    // } else {
    postInflate(inflater, layoutId, handlerRef, listenerRef);
    // }

  }

  private void asyncInflateView(final LayoutInflater inflater, final int layoutId,
      final WeakReference<Handler> handlerRef,
      final WeakReference<InflateListener> listenerRef) {
    final View view;
    try {
      view = inflater.inflate(layoutId, null);
    } catch (Exception e) {
      canAsyncInflate = false;
      postInflate(inflater, layoutId, handlerRef, listenerRef);
      return;
    }
    Handler handler = handlerRef.get();
    if (handler != null) {
      handler.post(new Runnable() {

        @Override
        public void run() {
          InflateListener listener = listenerRef.get();
          if (listener != null) {
            listener.onInflatedView(view);
          }
        }
      });
    }
  }

  private void postInflate(final LayoutInflater inflater, final int layoutId,
      final WeakReference<Handler> handlerRef,
      final WeakReference<InflateListener> listenerRef) {
    if (handlerRef != null) {
      Handler handler = handlerRef.get();
      if (handler != null) {
        handler.post(new Runnable() {

          @Override
          public void run() {
            View view = inflater.inflate(layoutId, null);
            InflateListener listener = listenerRef.get();
            if (listener != null) {
              listener.onInflatedView(view);
            }
          }
        });
      }
    }
  }

}
