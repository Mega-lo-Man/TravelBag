package com.example.testapp;


import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity{//FragmentActivity {

    // это будет именем файла настроек
    //public static final String APP_PREFERENCES = "mysettings";

    private static Boolean editFlag = false; // Флаг разрешения/запрета редактирования editText'ов
    LinkedHashMap<String, Boolean> linkedHashMap = new LinkedHashMap<String, Boolean>();

    //ArrayList<record> itemsList;
    HashMap<String, Integer> record;
    private int counter = 0;

    //список вьюх которые будут создаваться
    private List<View> viewList;

    private Menu menu;

    private Toolbar toolbar;

    private SharedPreferences myPreferences;

    private RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private DataAdapter myAdapter;



    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager2 mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FragmentStateAdapter pagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSubTitleOnToolbar();

        // Instantiate a ViewPager and a PagerAdapter
        mPager = findViewById(R.id.view_pager);
        pagerAdapter = new PagerAdapter(this);
        mPager.setAdapter(pagerAdapter);

    }

    @Override
    protected void onPause() {
        super.onPause();

        //сохраним view
        SharedPreferences.Editor myEditor = myPreferences.edit();
        myEditor.clear();

        for (LinkedHashMap.Entry<String, Boolean> entry : linkedHashMap.entrySet()) {
            myEditor.putBoolean(entry.getKey(),
                    entry.getValue());
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

        switch (id) {
            case R.id.add_settings:
                counter++;
                //addItem("New entries" + counter, false);
                return true;
            case R.id.editable_settings:
                editFlag = !editFlag; // инвертируем флаг разрешения редактирования editText'ов
                setSubTitleOnToolbar();
                myAdapter.itemActive = editFlag;
                myAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "EditMode: " + editFlag, Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // метод добавляет content_main на основную Activity
    // strText - текст записываемый в EditText
    // chekedState - состояние CheckBox'а
    private void addItem(String strText, boolean chekedState) {
        linkedHashMap.put("New entry " + linkedHashMap.size(), false);
        myAdapter.notifyItemInserted(linkedHashMap.size());
    }

    private LinkedHashMap<String, Boolean> loadPeferences() {
        //Необходимо загрузить неотсортированный список настроек приложения и отсортировать
        //по алфавиту, чтобы легко было находить item'ы.
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                Collator collator = Collator.getInstance(new Locale("ru", "RU"));
                collator.setStrength(Collator.PRIMARY);
                return collator.compare(s, t1);
            }
        };

        SortedMap<String, Boolean> sortedMap = new TreeMap<String, Boolean>(comparator);

        for (Map.Entry<String, ?> entry : myPreferences.getAll().entrySet()) {
            sortedMap.put(entry.getKey(), (Boolean) entry.getValue());
            //Log.d("CLICK ROW:" , " SharedPreferences " + entry.getKey() + " : " + entry.getValue().toString());
        }

        LinkedHashMap<String, Boolean> tempMap = new LinkedHashMap<>();

        for (Map.Entry<String, ?> entry : sortedMap.entrySet()) {
            tempMap.put(entry.getKey(), (Boolean) entry.getValue());
        }
        return tempMap;
    }

    private void setSubTitleOnToolbar() {
        if (editFlag) {
            toolbar.setSubtitle("Edit mode");
        } else {
            toolbar.setSubtitle("Read only");
        }
    }

    private void showRemoveAlertDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        // Add the buttons
        builder.setTitle("Your Title");
        builder.setMessage("Your Dialog Message");
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                String key = (new ArrayList<>(linkedHashMap.keySet())).get(position);

                linkedHashMap.remove(key);
                myAdapter.notifyItemRemoved(position);
                //myAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean invertValueByIndex(int position) {
        int counter = 0;
        String str = "";
        boolean value = false;

        for (Map.Entry<String, Boolean> entry : linkedHashMap.entrySet()) {
            if (counter == position) {
                str = entry.getKey();
                value = entry.getValue();
            } else {
                counter++;
            }
            if (str != "") {
                linkedHashMap.put(str, !value);
                return true;
            }
        }
        return false;
    }


}
