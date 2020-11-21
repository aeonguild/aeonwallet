package com.aeon.app.ui.node;

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

public class NodeFragment extends Fragment {
    public static NodeAdapter nodeAdapter;


    public NodeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_node, container, false);
        Context context = view.getContext();
        RecyclerView rv = view.findViewById(R.id.rv_node_info_list);
        rv.setLayoutManager(new GridLayoutManager(context, 1));
        nodeAdapter = new NodeAdapter(NodeContent.ITEMS);
        rv.setAdapter(nodeAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.hideUI();
    }
}