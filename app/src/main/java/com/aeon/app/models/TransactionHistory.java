package com.aeon.app.models;

import android.util.Log;

public class TransactionHistory {
    static {
        System.loadLibrary("aeon_transaction_history_jni");
    }
    private static final String TAG = "History";
    private long handle;
    public int count;
    public TransactionHistory(long handle){
        Log.v(TAG, "History");
        this.handle = handle;
    }
    public int getCount(){
        Log.v(TAG, "getCount");
        this.count = getCountJNI();
        return count;
    }
    public TransactionInfo getTransaction(int index){
        Log.v(TAG, "getTransaction");
        return new TransactionInfo(getTransactionJNI(index));
    }
    public void refresh(){
        Log.v(TAG, "refresh >> "+handle);
        refreshJNI();
    }
    private native int getCountJNI();
    private native long getTransactionJNI(int index);
    private native void refreshJNI();
}
