package com.aeon.app.ui.recent;

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
public class RecentFragment extends Fragment {
    public static RecentAdapter recentAdapter;

    public RecentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent, container, false);
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_recent_item_list);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        recentAdapter = new RecentAdapter(RecentContent.ITEMS);
        recyclerView.setAdapter(recentAdapter);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        MainActivity.showUI();
        MainActivity.button_transfer.setTextAppearance(R.style.Button_Unselected);
        MainActivity.button_contacts.setTextAppearance(R.style.Button_Unselected);
        MainActivity.button_recents.setTextAppearance(R.style.Button_Selected);
        MainActivity.image_transfer.setVisibility(View.GONE);
        MainActivity.image_contacts.setVisibility(View.GONE);
        MainActivity.image_recents.setVisibility(View.VISIBLE);
    }
}