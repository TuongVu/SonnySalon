package com.tvswitch.sonnysalon;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.tvswitch.sonnylibrary.utils.AnimationUtils;
import com.tvswitch.sonnylibrary.utils.PrefUtils;

public class MainActivity extends AppCompatActivity {
    private ScrollView scrollView;
    private LinearLayout menuContainer;
    private Toolbar toolbar;
    private TextView title;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private String[] mainMenuData;
    private int selectedPosition = 0;
//    private MainMenuAdapter mainMenuAdapter;
    private FragmentManager fragmentManager;
//    private android.support.v4.app.FragmentManager fragmentManagerV4;
    private PrefUtils prefs;

    private FloatingActionButton mFAB;
    private FloatingActionMenu mFABMenu;

    private View lastSelectedMenuView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnimationUtils.setupWindowAnimations(getWindow());
        AppStateObjects.setContext(getApplicationContext());
        prefs = new PrefUtils(this);
        fragmentManager = getSupportFragmentManager();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null){ //check if tool supported on OS version
            setSupportActionBar(toolbar);
        }
        TextView usernameTextView = (TextView) findViewById(R.id.userNametextView);
        usernameTextView.setText(AppStateObjects.getUser().loginname);
        mainMenuData = getResources().getStringArray(R.array.menu_array);
        scrollView = (ScrollView) findViewById(R.id.mainmenuscrollView);
        menuContainer = (LinearLayout)findViewById(R.id.mainmenuscrollcontainer);
//        mainMenuListView = (ListView) findViewById(R.id.left_drawer_listview);
//        mainMenuAdapter = new MainMenuAdapter(getApplicationContext(),mainMenuData);
//        mainMenuListView.setAdapter(mainMenuAdapter);
//        mainMenuListView.setOnItemClickListener();

        setUpMainMenuItem(menuContainer);
        setDrawerLayoutWidth(scrollView, 50.0);
        title = (TextView) findViewById(R.id.toolBarTitle);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if(selectedPosition >= 0) {
                    title.setText(mainMenuData[selectedPosition]);
                    toggleFAB(true);
                }
//                mainMenuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                title.setText(R.string.app_fullname);
//                mainMenuAdapter.notifyDataSetChanged();
                toggleFAB(false);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        //set default start up fragment
        setupFAB();

        if(prefs.getDefaultMainMenuItemSelected() == -1){
            selectedPosition = prefs.getMainMenuItemSelected();
        }else{
            selectedPosition = prefs.getDefaultMainMenuItemSelected();
        }
        selectMainMenuItem(selectedPosition);
    }

    private void setUpMainMenuItem(LinearLayout menuContainer){
        menuContainer.removeAllViews();
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for(String itemName:mainMenuData){
            View v = inflater.inflate(R.layout.main_menu_item, null, false);
            TextView name = (TextView)v.findViewById(R.id.nameTextView);
            name.setTag(itemName);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String itemName = (String) v.getTag();
                    selectMainMenuItem(getPositionFromMainArray(itemName,mainMenuData));
                    drawerLayout.closeDrawers();
                }
            });
            name.setText(itemName);
            menuContainer.addView(v);
        }
    }

    private int getPositionFromMainArray(String itemName, String[] mainMenuData){
        int returnValue = -1;
        for(int a = 0;a<mainMenuData.length;a++){
            if(mainMenuData[a].equalsIgnoreCase(itemName)){
                returnValue = a;
                break;
            }
        }
        return returnValue;
    }

    /**
     * Set the width of the left navigation drawer to your desired.
     * Allow use the smaller dimension on either orientation
     * @param leftDrawerList
     * @param percent
     */
    private void setDrawerLayoutWidth(ScrollView leftDrawerList, double percent){
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        if(width > height) {
            width = height;
        }
        width = (int) (width * (percent / 100.0));
        leftDrawerList.setLayoutParams(new RelativeLayout.LayoutParams(width, RelativeLayout.LayoutParams.MATCH_PARENT));
    }

    /**
     * This method set the currently selected menu item then create appropriate fragment
     * @param position
     */
    private void selectMainMenuItem(int position){
        selectedPosition = position;
        title.setText(mainMenuData[selectedPosition]);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        switch (position){
            case 0:
                ft.replace(R.id.main_container,new HomeFragment(),HomeFragment.TAG);
                break;
            case 1:
                ft.replace(R.id.main_container, new ServicesFragment(), ServicesFragment.TAG);
                break;
            case 2:
                ScheduleFragment scheduleFragment = new ScheduleFragment();
                scheduleFragment.setmFABmenu(mFABMenu);
                ft.replace(R.id.main_container, scheduleFragment, ScheduleFragment.TAG);
                toggleFAB(true); //turn floating button on
                break;
            case 3:
                ft.replace(R.id.main_container, new GalleryFragment(), GalleryFragment.TAG);
                break;
        }
        ft.commit();
        prefs.setMainMenuItemSelected(selectedPosition);
        if(selectedPosition != 2){
            toggleFAB(false);
        }
        View view = menuContainer.getChildAt(position).findViewById(R.id.highlightTextView);
        view.setBackgroundColor(getResources().getColor(R.color.menuItemSelectedColor));
        view.invalidate();

        if(lastSelectedMenuView != null){
            lastSelectedMenuView.setBackgroundColor(Color.TRANSPARENT);
            lastSelectedMenuView.invalidate();
        }
        lastSelectedMenuView = view;
    }

    private void toggleFAB(boolean on){
        if(on && selectedPosition == 2){
            mFAB.setVisibility(View.VISIBLE);
        }else{
            if(mFABMenu.isOpen()){
                mFABMenu.toggle(true);
            }
            mFAB.setVisibility(View.GONE);
        }
    }

    /**
     * Create make appointment Floating Action Button
     */
    private void setupFAB() {
        //define the icon for the main floating action button
        ImageView iconFAB = new ImageView(this);
        iconFAB.setImageResource(R.drawable.ic_action_new);

        //set the appropriate background for the main floating action button along with its icon
        mFAB = new FloatingActionButton.Builder(this)
                .setContentView(iconFAB)
                .setBackgroundDrawable(R.drawable.selector_button_red)
                .build();
        //only set visible on appointment selected
        mFAB.setVisibility(View.GONE);

        //define the icons for the sub action buttons
        ImageView iconSortName = new ImageView(this);
        iconSortName.setImageResource(R.drawable.ic_action_important);
        ImageView iconSortDate = new ImageView(this);
        iconSortDate.setImageResource(R.drawable.ic_action_important);
        ImageView iconSortRatings = new ImageView(this);
        iconSortRatings.setImageResource(R.drawable.ic_action_important);

        //set the background for all the sub buttons
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_sub_button_gray));


        //build the sub buttons
        SubActionButton buttonSortName = itemBuilder.setContentView(iconSortName).build();
        SubActionButton buttonSortDate = itemBuilder.setContentView(iconSortDate).build();
        SubActionButton buttonSortRatings = itemBuilder.setContentView(iconSortRatings).build();

        //to determine which button was clicked, set Tags on each button
        buttonSortName.setTag("1");
        buttonSortDate.setTag("2");
        buttonSortRatings.setTag("3");

//        buttonSortName.setOnClickListener(this);
//        buttonSortDate.setOnClickListener(this);
//        buttonSortRatings.setOnClickListener(this);

        //add the sub buttons to the main floating action button
        mFABMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonSortName)
                .addSubActionView(buttonSortDate)
                .addSubActionView(buttonSortRatings)
                .attachTo(mFAB)
                .build();
        mFABMenu.getSubActionItems();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawers();
        else
            super.onBackPressed();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SettingsDialog settingsDialog = new SettingsDialog(this);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(settingsDialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            settingsDialog.show();
            settingsDialog.getWindow().setAttributes(lp);
            settingsDialog.setCancelable(true);
        }
        if( id == R.id.action_logout){
            prefs.setUserName(null);
            prefs.setPassword(null);
            AppStateObjects.setUser(null);
            Intent intent = new Intent(MainActivity.this, DispatchActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Left Navigation main menu adapter
     */
//    private class MainMenuAdapter extends ArrayAdapter<String>{
//        private final Context context;
//        private final String[] values;
//        private final LayoutInflater inflater;
//        public MainMenuAdapter(Context context, String[] values) {
//            super(context, -1, values);
//            this.context = context;
//            this.values = values;
//            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        }
//
//        @Override
//        public int getCount() {
//            return values.length;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View v = convertView;
//            if(convertView == null) {
//                 v = inflater.inflate(R.layout.main_menu_item, parent, false);
//            }
//            TextView name = (TextView)v.findViewById(R.id.nameTextView);
//            name.setText(values[position]);
//            View highlight = (View) v.findViewById(R.id.highlightTextView);
//            if(selectedPosition == position){
//                highlight.setBackgroundResource(R.color.menuItemSelectedColor);
//            }else{
//                highlight.setBackgroundColor(Color.TRANSPARENT);
//            }
//            return v;
//        }
//    }
}
