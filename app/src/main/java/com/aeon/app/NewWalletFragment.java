package com.aeon.app;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class NewWalletFragment extends Fragment {
    private TextView text_seed_phrase;
    private Button understood;
    public NewWalletFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_wallet, container, false);
        text_seed_phrase = v.findViewById(R.id.text_seed_phrase);
        understood= v.findViewById(R.id.button_understood);
        if(!BackgroundThread.isShownNewWalletFragment) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (BackgroundThread.seed == null) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            text_seed_phrase.setText(BackgroundThread.seed);
                            understood.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }).start();
        }
        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        MainActivity.hideUI();
    }
    @Override
    public void onStop() {
        super.onResume();
        text_seed_phrase.setText("");
        BackgroundThread.isShownNewWalletFragment = true;
        understood.setVisibility(View.GONE);
    }
}