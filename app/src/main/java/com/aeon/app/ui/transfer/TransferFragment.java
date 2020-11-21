package com.aeon.app.ui.transfer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aeon.app.BackgroundThread;
import com.aeon.app.MainActivity;
import com.aeon.app.R;
import com.aeon.app.ui.wallet.WalletAdapter;
import com.aeon.app.ui.wallet.WalletContent;

public class TransferFragment extends Fragment {
    public static WalletAdapter walletAdapter;
    public static Group transferGroup;
    public static Group onboardGroup;
    private EditText recipient;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transfer, container, false);
        Context context = view.getContext();
        RecyclerView rv = view.findViewById(R.id.rv_wallet_info_list);
        rv.setLayoutManager(new GridLayoutManager(context, 1));
        walletAdapter = new WalletAdapter(WalletContent.ITEMS);
        walletAdapter.showSummary(true);
        rv.setAdapter(walletAdapter);
        transferGroup = view.findViewById(R.id.group_transfer);
        onboardGroup = view.findViewById(R.id.group_onboard);
        recipient = (EditText)view.findViewById(R.id.transfer_recipient_info);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.showUI();
        if(BackgroundThread.isManaging){
            transferGroup.setVisibility(View.VISIBLE);
            onboardGroup.setVisibility(View.GONE);
            recipient.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(getActivity().getCurrentFocus(), 0);
        } else {
            transferGroup.setVisibility(View.GONE);
            onboardGroup.setVisibility(View.VISIBLE);
        }
        MainActivity.button_transfer.setTextAppearance(R.style.Button_Selected);
        MainActivity.button_contacts.setTextAppearance(R.style.Button_Unselected);
        MainActivity.button_recents.setTextAppearance(R.style.Button_Unselected);
        MainActivity.image_transfer.setVisibility(View.VISIBLE);
        MainActivity.image_contacts.setVisibility(View.GONE);
        MainActivity.image_recents.setVisibility(View.GONE);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(BackgroundThread.isManaging) {
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            try {
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }

    public static void hideUI(View view){
        transferGroup.setVisibility(View.GONE);
        onboardGroup.setVisibility(View.GONE);
    }
    public static void showUI(View view){
        if(BackgroundThread.isManaging){
            transferGroup.setVisibility(View.VISIBLE);
            onboardGroup.setVisibility(View.GONE);
        } else {
            transferGroup.setVisibility(View.GONE);
            onboardGroup.setVisibility(View.VISIBLE);
        }
    }
}