package com.example.testapp;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SlidePageFragment extends Fragment {

    private RecyclerView myRecyclerView;
    private List<Item> lstItems;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstItems = new ArrayList<>();

        lstItems.add(new Item("Item 1", true));
        lstItems.add(new Item("Item 2", true));
        lstItems.add(new Item("Item 3", true));
        lstItems.add(new Item("Item 4", true));
        lstItems.add(new Item("Item 5", true));
        lstItems.add(new Item("Item 6", true));
        lstItems.add(new Item("Item 7", true));
        lstItems.add(new Item("Item 8", true));
        lstItems.add(new Item("Item 9", true));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        myRecyclerView = rootView.findViewById(R.id.recycler_view);
        Log.d("MARKER", "Before create DataAdapter");
        DataAdapter recyclerAdapter = new DataAdapter(getContext(), lstItems, new DataAdapter.OnItemClickListener() {
            @Override
            public void onButtonClick(int position) {
                Toast.makeText(getContext(), "Item: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSoftKeyActionGo(Pair<String, String> EditTextString) {

                //InputMethodManager imm = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
                //imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }

            @Override
            public void onCheckBoxClick(int position) {

            }
        });
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerAdapter);
        return rootView;
    }

}
