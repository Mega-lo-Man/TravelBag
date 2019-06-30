package com.example.testapp;


import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity {

    // это будет именем файла настроек
    //public static final String APP_PREFERENCES = "mysettings";

    private static Boolean editFlag = false; // Флаг разрешения/запрета редактирования editText'ов

    private int counter = 0;

    //список вьюх которые будут создаваться
    private List<View> viewList;

    private Menu menu;

    private Toolbar toolbar;

    private SharedPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        setSubTitleOnToolbar();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CLICK ROW", String.valueOf(view.getId()));
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        myPreferences = getDefaultSharedPreferences(this);//APP_PREFERENCES, Context.MODE_PRIVATE);

        //SaveMap = new HashMap<>();
        //HashMap<String, Boolean> SaveMap = new HashMap<>();
        viewList = new ArrayList<>();
        LoadPeferences();
    }

    @Override
    protected void onPause() {
        super.onPause();

        //сохраним view
        SharedPreferences.Editor myEditor = myPreferences.edit();
        myEditor.clear();
        for(View item : viewList){
            myEditor.putBoolean(((EditText)item.findViewById(R.id.editText)).getText().toString(),
                                ((CheckBox)item.findViewById(R.id.checkBox1)).isChecked());
        }
        myEditor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Use the Search Manager to find the SearchableInfo related to this Activity
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        // Bind the Activity’s SearchableInfo to the Search View
        SearchView searchView = (SearchView) menu.findItem(R.id.search_settings).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.add_settings:
                counter++;
                addContent("New entries" + counter, false);
            return true;
            case R.id.editable_settings:
                editFlag = !editFlag; // инвертируем флаг разрешения редактирования editText'ов
                setSubTitleOnToolbar();
                for(View item_1 : viewList){
                    EditText text1 = item_1.findViewById(R.id.editText);
                    //text1.setText("Changed "+ item_1.toString());
                    if (editFlag) {
                        //Настраиваем EditText на запись
                        text1.setFocusable(true);
                        text1.setFocusableInTouchMode(true);
                        text1.setClickable(false);
                        setOptionTitle(R.id.editable_settings, "Read");
                    }else{
                        //Настраиваем EditText только на чтение (дабы случайно не изменить)
                        text1.setFocusable(false);
                        text1.setClickable(true);
                        setOptionTitle(R.id.editable_settings, "Edit");
                    }
                }
                //Toast.makeText(getApplicationContext(), "text", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // метод добавляет content_main на основную Activity
    // strText - текст записываемый в EditText
    // chekedState - состояние CheckBox'а
    private void addContent(String strText, boolean chekedState) {
        //находим наш linear который у нас под кнопкой add edittext в activity_main.xml
        final LinearLayout linear = findViewById(R.id.linear);

        //берем наш кастомный лейаут находим через него все наши кнопки и едит тексты, задаем нужные данные
        final View view = getLayoutInflater().inflate(R.layout.content_main, null);

        Button deleteField = view.findViewById(R.id.remove);
        EditText text = view.findViewById(R.id.editText);
        CheckBox chkBox1 = view.findViewById(R.id.checkBox1);

        chkBox1.setChecked(chekedState);
        text.setText(strText);

        if (editFlag){
            //Настраиваем EditText на запись
            text.setFocusable(true);
            text.setFocusableInTouchMode(true);
            text.setClickable(false);
        }else{
            //Настраиваем EditText только на чтение (дабы случайно не изменить)
            text.setFocusable(false);
            text.setClickable(true);
        }

        //добавляем всё что создаём в массив
        viewList.add(view);
        // добавляем элемнты в linearLayout
        linear.addView(view);
        //Log.d("CLICK ROW", String.valueOf(view.getTag()));
        Log.d("CLICK ROW", ((EditText) view.findViewById(R.id.editText)).getText().toString());

        deleteField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //получаем родительский view и удаляем его
                    ((LinearLayout) view.getParent()).removeView(view);
                    //удаляем эту же запись из массива что бы не оставалось мертвых записей
                    viewList.remove(view);
                    Log.d("CLICK ROW", viewList.toString());
                } catch(IndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    private void LoadPeferences(){
        Map<String, ?> prefsMap = myPreferences.getAll();
        for (Map.Entry<String, ?> entry: prefsMap.entrySet()){
            //Log.d("CLICK ROW:" , " SharedPreferences " + entry.getKey() + " : " + entry.getValue().toString());
            addContent(entry.getKey(),(Boolean) entry.getValue());
        }
    }
    private void setOptionTitle(int id, String title)
    {
        MenuItem item = menu.findItem(id);
        item.setTitle(title);
    }
    private void setSubTitleOnToolbar(){
        if (editFlag){
            toolbar.setSubtitle("Edit mode");
        }else{
            toolbar.setSubtitle("Read only");
        }
    }
}
