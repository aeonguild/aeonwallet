package com.aeon.app.models;

import android.util.Log;

import java.math.BigDecimal;

public class TransactionInfo {
    static {
        System.loadLibrary("aeon_transaction_info_jni");
    }
    private static final String TAG = "Info";
    private final long handle;
    public Direction direction = Direction.Unknown;
    public boolean isPending = false;
    public boolean isFailed = false;
    public BigDecimal amount = new BigDecimal(0);
    public BigDecimal fee = new BigDecimal(0);
    public long height = 0 ;
    public long confirmations= 0;
    public long unlockTime = 0;
    public String hash = "";
    public String paymentId= "";

    public enum Direction {
        In,
        Out,
        Unknown
    }
    public TransactionInfo(long handle){
        Log.v(TAG, "Info");
        this.handle = handle;
    }

    public void refresh(){
        Log.v(TAG, "refresh >> "+handle);
        getDirection();
        getHash();
        getAmount();
        getHeight();
        isPending();
        isFailed();
        getFee();
        getConfirmations();
        getUnlockTime();
        getPaymentId();
    }
    public Direction getDirection(){
        Log.v(TAG, "getDirection");
        this.direction= Direction.values()[getDirectionJNI()];
        return direction;
    }
    public boolean isPending(){
        Log.v(TAG, "isPending");
        this.isPending=isPendingJNI();
        return isPending;

    }
    public boolean isFailed(){
        Log.v(TAG, "isFailed");
        this.isFailed=isFailedJNI();
        return isFailed;

    }
    public BigDecimal getAmount(){
        Log.v(TAG, "getAmount");
        this.amount= (new BigDecimal(getAmountJNI())).movePointLeft(12);
        return amount;

    }
    public BigDecimal getFee(){
        Log.v(TAG, "getFee");
        this.fee= (new BigDecimal(getFeeJNI())).movePointLeft(12);
        return fee;
    }
    public long getHeight(){
        Log.v(TAG, "getHeight");
        this.height=getHeightJNI();
        return height;
    }
    public long getConfirmations(){
        Log.v(TAG, "getConfirmations");
        this.confirmations=getConfirmationsJNI();
        return confirmations;

    }
    public long getUnlockTime(){
        Log.v(TAG, "getUnlockTime");
        this.unlockTime=getUnlockTimeJNI();
        return unlockTime;

    }
    public String getHash() {
        Log.v(TAG, "getHash");
        this.hash=getHashJNI();
        return hash;

    }
    public String getPaymentId() {
        Log.v(TAG, "getPaymentId");
        this.paymentId=getPaymentIdJNI();
        return paymentId;

    }
    private native int getDirectionJNI();
    private native boolean isPendingJNI();
    private native boolean isFailedJNI();
    private native long getAmountJNI();
    private native long getFeeJNI();
    private native long getHeightJNI();
    private native long getConfirmationsJNI();
    private native long getUnlockTimeJNI();
    private native String getHashJNI() ;
    private native String getPaymentIdJNI() ;
}
