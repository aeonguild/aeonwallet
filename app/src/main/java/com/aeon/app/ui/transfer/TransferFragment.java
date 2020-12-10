package com.aeon.app.ui.transfer;

/*
Copyright 2020 ivoryguru

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aeon.app.BackgroundThread;
import com.aeon.app.MainActivity;
import com.aeon.app.R;
import com.aeon.app.ui.blog.BlogAdapter;
import com.aeon.app.ui.blog.BlogContent;
import com.aeon.app.ui.wallet.WalletAdapter;
import com.aeon.app.ui.wallet.WalletContent;

public class TransferFragment extends Fragment {
    public static WalletAdapter walletAdapter;
    public static BlogAdapter blogAdapter;
    public static Group transferGroup;
    public static Group onboardGroup;
    private EditText recipient;
    private CheckBox check_payment_id;
    private EditText text_payment_id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transfer, container, false);
        Context context = view.getContext();
        RecyclerView rv = view.findViewById(R.id.rv_wallet_info_list);
        rv.setLayoutManager(new GridLayoutManager(context, 1));
        walletAdapter = new WalletAdapter(WalletContent.ITEMS);
        walletAdapter.showSummary(true);
        rv.setAdapter(walletAdapter);
        rv = view.findViewById(R.id.rv_blog_list);
        rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        blogAdapter = new BlogAdapter(BlogContent.ITEMS,context);
        rv.setAdapter(blogAdapter);
        transferGroup = view.findViewById(R.id.group_transfer);
        onboardGroup = view.findViewById(R.id.group_onboard);
        recipient = (EditText)view.findViewById(R.id.transfer_recipient_info);
        check_payment_id = (CheckBox)view.findViewById(R.id.check_payment_id);
        text_payment_id = (EditText)view.findViewById(R.id.text_payment_id);
        check_payment_id.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EditText text_payment_id = (EditText)view.findViewById(R.id.text_payment_id);
                if(isChecked){
                    text_payment_id.setVisibility(View.VISIBLE);
                } else {
                    text_payment_id.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.showUI();
        if(BackgroundThread.isManaging){
            transferGroup.setVisibility(View.VISIBLE);
            text_payment_id.setVisibility(View.GONE);
            onboardGroup.setVisibility(View.GONE);
            recipient.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(getActivity().getCurrentFocus(), 0);
        } else {
            transferGroup.setVisibility(View.GONE);
            text_payment_id.setVisibility(View.GONE);
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
}