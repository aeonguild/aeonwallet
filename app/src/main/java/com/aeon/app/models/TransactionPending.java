package com.aeon.app.models;

import android.util.Log;

public class TransactionPending {
    static {
        System.loadLibrary("aeon_transaction_pending_jni");
    }
    private static final String TAG = "Pending";
    private long handle;
    public boolean isCommitted = false;
    public boolean isAttempted = false;
    public String recipient;
    public long amount;
    public long fee;
    public long dust;
    public Status status;
    public enum Status {
        Ok,
        Error,
        Critical
    }

    public enum Priority {
        Priority_Default,
        Priority_Low,
        Priority_Medium,
        Priority_High,
        Priority_Last
    }
    public TransactionPending(String recipient, long amount){
        Log.v(TAG, "Pending");
        this.recipient = recipient;
        this.amount = amount;
    }
    public void setHandle(long handle){
        Log.v(TAG, "setHandle");
        this.handle = handle;
    }
    public void refresh(){
        Log.v(TAG, "refresh");
        this.status = Status.values()[getStatusJNI()];
        this.fee = getFeeJNI();
        this.amount = getAmountJNI();
        this.dust = getDustJNI();
    }
    public void commit(){
        Log.v(TAG, "commit");
        isCommitted = commitJNI();
        isAttempted = true;
    }
    private native boolean commitJNI();
    private native int getStatusJNI();
    public native long getFeeJNI();
    public native long getAmountJNI();
    public native long getDustJNI();
}
