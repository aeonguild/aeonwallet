package com.aeon.app;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.math.BigDecimal;

public class TransactionPendingFragment extends Fragment {
    private EditText text_transfer_password;
    private TextView text_transfer_info;
    private Button understood;
    public TransactionPendingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_transaction_pending, container, false);
        text_transfer_info = (TextView) v.findViewById(R.id.text_transfer_info);
        understood = v.findViewById(R.id.button_confirm_send);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (BackgroundThread.pendingTransaction.fee==0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        text_transfer_info.setText(
                                "You are sending " +BigDecimal.valueOf(BackgroundThread.pendingTransaction.amount).movePointLeft(12).toPlainString()
                                + " aeon to " + BackgroundThread.pendingTransaction.recipient
                                + " for a fee of " + BigDecimal.valueOf(BackgroundThread.pendingTransaction.fee).movePointLeft(12).toPlainString()
                                + " aeon."
                        );
                        understood.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        MainActivity.hideUI();
    }
    @Override
    public void onStop() {
        super.onStop();
    }
}