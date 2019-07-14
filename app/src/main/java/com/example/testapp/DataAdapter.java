package com.example.testapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
//import android.widget.TextView;
import android.text.TextWatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DataAdapter extends Adapter<DataAdapter.ViewHolder> {

    private LinkedHashMap<String, Boolean> mDataSet;
    private OnItemClickListener listener;

    public DataAdapter(LinkedHashMap<String, Boolean> myDataSet, OnItemClickListener myListener) {
        mDataSet = myDataSet;
        listener = myListener;
    }

    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_main, parent, false);
        return new ViewHolder(v, new MyCustomEditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder myViewHolder, int position) {
        myViewHolder.bind(position, listener);
        String key = (new ArrayList<>(mDataSet.keySet())).get(position);
        Boolean value = (new ArrayList<>(mDataSet.values())).get(position);
        myViewHolder.textItem.setText(key);
        myViewHolder.chkItem.setChecked(value);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    interface OnItemClickListener {
        void onItemClick(int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final EditText textItem;
        final CheckBox chkItem;
        final Button removeItem;
        public MyCustomEditTextListener myCustomEditTextListener;

        ViewHolder(@NonNull View itemView, MyCustomEditTextListener mCustomEditTextListener) {
            super(itemView);
            textItem = itemView.findViewById(R.id.editText);
            chkItem = itemView.findViewById(R.id.checkBox1);
            removeItem = itemView.findViewById(R.id.remove);
            myCustomEditTextListener = mCustomEditTextListener;
            textItem.addTextChangedListener(mCustomEditTextListener);
        }

        public void bind(final int position, final OnItemClickListener listener) {
            removeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(position);
                }
            });
        }
    }

    private class MyCustomEditTextListener implements TextWatcher{
        private int itemPosition;
        public void updatePosition(int position){
            itemPosition = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            mDataSet.put(charSequence.toString(), true);// = charSequence.toString();
        }
    }

}
