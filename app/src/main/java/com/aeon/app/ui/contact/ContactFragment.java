package com.aeon.app.ui.contact;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aeon.app.MainActivity;
import com.aeon.app.R;

/**
 * A fragment representing a list of Items.
 */
public class ContactFragment extends Fragment {
    public static ContactAdapter contactAdapter;

    public ContactFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        Context context = view.getContext();
        RecyclerView rv = view.findViewById(R.id.rv_contact_item_list);
        rv.setLayoutManager(new GridLayoutManager(context, 1));
        contactAdapter = new ContactAdapter(ContactContent.ITEMS);
        rv.setAdapter(contactAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.showUI();
        MainActivity.button_transfer.setTextAppearance(R.style.Button_Unselected);
        MainActivity.button_contacts.setTextAppearance(R.style.Button_Selected);
        MainActivity.button_recents.setTextAppearance(R.style.Button_Unselected);
        MainActivity.image_transfer.setVisibility(View.GONE);
        MainActivity.image_contacts.setVisibility(View.VISIBLE);
        MainActivity.image_recents.setVisibility(View.GONE);
    }
}