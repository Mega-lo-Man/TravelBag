package com.example.testapp;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    public Boolean itemActive = false;
    private List<Item> mDataSet;
    private OnItemClickListener listener;
    private Context myContext;

    interface OnItemClickListener {
        void onButtonClick(int position);

        void onSoftKeyActionGo(Pair<String, String> EditTextString);

        void onCheckBoxClick(int position);
    }

    public DataAdapter(Context context, List<Item> myDataSet, OnItemClickListener myListener) {
        mDataSet = myDataSet;
        listener = myListener;
        myContext = context;

    }

    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(myContext).inflate(R.layout.content_main, parent, false);
        final ViewHolder vHolder = new ViewHolder(v, new MyCustomEditTextListener());
        Log.d("MARKER", "myContext: " + myContext.toString());
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder myViewHolder, int position) {
        //String key = (new ArrayList<>(mDataSet.keySet())).get(position);
        myViewHolder.bind(mDataSet.get(position).getName(), position, listener);
        //Boolean value = (new ArrayList<>(mDataSet.values())).get(position);
        myViewHolder.textItem.setText(mDataSet.get(position).getName());
        myViewHolder.chkItem.setChecked(mDataSet.get(position).getChecked());
        myViewHolder.removeItem.setEnabled(itemActive);
        myViewHolder.textItem.setEnabled(itemActive);
        Log.d("MARKER", mDataSet.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
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

        public void bind(final String lastEditTextValue, final int position, final OnItemClickListener listener) {
            removeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onButtonClick(position);
                }
            });
            textItem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    boolean handled = false;

                    if (actionId == EditorInfo.IME_ACTION_GO) {
                        Log.d("CLICKROW", " SharedPreferences " + actionId);
                        handled = true;
                    }
                    Pair<String, String> returnPair = Pair.create(lastEditTextValue, textView.getText().toString());
                    listener.onSoftKeyActionGo(returnPair);
                    return handled;

                }
            });
            chkItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCheckBoxClick(position);
                }
            });
        }
    }

    private class MyCustomEditTextListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //lastEditTextValue = ViewHolder.
        }

        @Override
        public void afterTextChanged(Editable editable) {

            //mDataSet.put(str, true);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //mDataSet.put(charSequence.toString(), true);// = charSequence.toString();
            //str += charSequence.toString();
        }
    }

}
