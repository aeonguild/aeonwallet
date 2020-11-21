package com.aeon.app.ui.wallet;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aeon.app.BackgroundThread;
import com.aeon.app.MainActivity;
import com.aeon.app.R;

public class WalletFragment extends Fragment {
    public static WalletAdapter walletAdapter;

    public WalletFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        if(BackgroundThread.isManaging){
            openWalletView(view);
        } else {
            closeWalletView(view);
        }

        Context context = view.getContext();
        RecyclerView rv = view.findViewById(R.id.rv_wallet_info_list);
        rv.setLayoutManager(new GridLayoutManager(context, 1));
        walletAdapter = new WalletAdapter(WalletContent.ITEMS);
        rv.setAdapter(walletAdapter);
        return view;
    }

    public static void openWalletView(View view){
        Group g = view.findViewById(R.id.group_wallet_off);
        g.setVisibility(View.GONE);
        g = view.findViewById(R.id.group_wallet_on);
        g.setVisibility(View.VISIBLE);
    }

    public static void closeWalletView(View view){
        Group g = view.findViewById(R.id.group_wallet_off);
        g.setVisibility(View.VISIBLE);
        g = view.findViewById(R.id.group_wallet_on);
        g.setVisibility(View.GONE);
    }

    public static void hideUI(View view){
        Group g = view.findViewById(R.id.group_wallet_off);
        g.setVisibility(View.GONE);
        g = view.findViewById(R.id.group_wallet_on);
        g.setVisibility(View.GONE);
    }
    @Override
    public void onResume() {
        super.onResume();
        MainActivity.hideUI();
    }
}