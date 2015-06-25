package com.tvswitch.sonnysalon;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.tvswitch.sonnylibrary.utils.PrefUtils;

import java.util.ArrayList;

/**
 * Created by Tuong on 5/29/15.
 */
public class SettingsDialog extends Dialog{
    private Context context;
    private Spinner menuItemSpinner;
    private PrefUtils prefs;
    public SettingsDialog(Context context){
        super(context);
        this.context = context;
        init();
    }

    private void init(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.settings_dialog_layout);
//        getWindow().setFlags(WindowManager.LayoutParams.Fil, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        prefs = new PrefUtils(context);
        menuItemSpinner = (Spinner)findViewById(R.id.setting_menu_spinner);
        String[] mainMenuData = getContext().getResources().getStringArray(R.array.menu_array);
        ArrayList<String> list = new ArrayList<String>();
        list.add("Last Selected");
        for(String item:mainMenuData){
            list.add(item);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menuItemSpinner.setAdapter(adapter);
        int position = prefs.getDefaultMainMenuItemSelected()+1;
        menuItemSpinner.setSelection(position);
        menuItemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefs.setDefaultMainMenuItemSelected(position - 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(Build.VERSION.SDK_INT >=21) {
            getWindow().setEnterTransition(new Explode().setDuration(2000));
        }
    }
}
