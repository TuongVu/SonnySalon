package com.tvswitch.sonnysalon;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.tvswitch.customviews.SlidingTabLayout;
import com.tvswitch.utils.ScheduleItem;
import com.tvswitch.utils.ScheduleRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment implements View.OnClickListener{
    public static String TAG = "ScheduleFragment";
    private ViewGroup root;
    private FloatingActionMenu mFABmenu;
//    private RecyclerView recyclerView;
//    private List<ScheduleItem> scheduleItemList = new ArrayList<ScheduleItem>();
    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    public ScheduleFragment(){}

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = (ViewGroup)inflater.inflate(R.layout.fragment_schedule, container, false);
        mPager = (ViewPager) root.findViewById(R.id.schedule_pager);
        mPager.setAdapter(new MyPagerAdaper(getChildFragmentManager()));
        mTabs = (SlidingTabLayout) root.findViewById(R.id.schedule_tabs);
        mTabs.setDistributeEvenly(true);
        mTabs.setBackgroundColor(getResources().getColor(R.color.primaryColor));
        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.mainAppColor));
        mTabs.setCustomTabView(R.layout.custom_schedule_tab_view,R.id.scheduleTabText);
        mTabs.setViewPager(mPager);
        if(mFABmenu != null){
            ArrayList<FloatingActionMenu.Item> items =  mFABmenu.getSubActionItems();
            for(FloatingActionMenu.Item item: items){
                item.view.setOnClickListener(this);
            }
        }
//        recyclerView = (RecyclerView) root.findViewById(R.id.schedule_recyclerview);
//        recyclerView.setHasFixedSize(true);
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(mLayoutManager);
//
//        DefaultItemAnimator animator = new DefaultItemAnimator();
//        animator.setAddDuration(1000);
//        animator.setRemoveDuration(1000);
//        recyclerView.setItemAnimator(animator);
//
//        scheduleItemList.add(new ScheduleItem(Color.RED,"Red"));
//        scheduleItemList.add(new ScheduleItem(Color.YELLOW,"Yellow"));
//        scheduleItemList.add(new ScheduleItem(Color.GREEN,"Green"));
//        scheduleItemList.add(new ScheduleItem(Color.BLUE,"Blue"));
//        scheduleItemList.add(new ScheduleItem(Color.BLACK,"Black"));
//        scheduleItemList.add(new ScheduleItem(Color.CYAN,"Cyan"));
//        scheduleItemList.add(new ScheduleItem(Color.DKGRAY,"Dark Gray"));
//        scheduleItemList.add(new ScheduleItem(Color.MAGENTA,"Magenta"));
//        scheduleItemList.add(new ScheduleItem(Color.LTGRAY,"Light Gray"));
//        scheduleItemList.add(new ScheduleItem(Color.RED,"Red"));
//        scheduleItemList.add(new ScheduleItem(Color.YELLOW,"Yellow"));
//        scheduleItemList.add(new ScheduleItem(Color.GREEN,"Green"));
//        scheduleItemList.add(new ScheduleItem(Color.BLUE,"Blue"));
//        scheduleItemList.add(new ScheduleItem(Color.BLACK,"Black"));
//        scheduleItemList.add(new ScheduleItem(Color.CYAN,"Cyan"));
//        scheduleItemList.add(new ScheduleItem(Color.DKGRAY,"Dark Gray"));
//        scheduleItemList.add(new ScheduleItem(Color.MAGENTA,"Magenta"));
//        scheduleItemList.add(new ScheduleItem(Color.LTGRAY,"Light Gray"));
//
//        ScheduleRecyclerAdapter scheduleRecyclerAdapter = new ScheduleRecyclerAdapter(getActivity(),scheduleItemList);
//        recyclerView.setAdapter(scheduleRecyclerAdapter);

        return root;
    }

    public void setmFABmenu(FloatingActionMenu mFABmenu){
        this.mFABmenu = mFABmenu;
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if(tag.equalsIgnoreCase("1")){
            Toast.makeText(getActivity(),"Clicked employee 1",Toast.LENGTH_SHORT).show();
        }else if(tag.equalsIgnoreCase("2")){
            Toast.makeText(getActivity(),"Clicked employee 2",Toast.LENGTH_SHORT).show();
        }else if(tag.equalsIgnoreCase("3")){
            Toast.makeText(getActivity(),"Clicked employee 3",Toast.LENGTH_SHORT).show();
        }
    }

    class MyPagerAdaper extends FragmentStatePagerAdapter {
        int[] icons ={R.drawable.schedule_current,R.drawable.schedule_history};
        String[] tabs ={"Current","History"};

        public MyPagerAdaper(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position){
                case 0:
                    fragment = new ScheduleFragmentCurrent();
                    break;
                case 1:
                    fragment = new ScheduleFragmentHistory();
                    break;
                default:
                    fragment = null;
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Drawable drawable = getResources().getDrawable(icons[position]);
            drawable.setBounds(0,0,48,48);
            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        @Override
        public int getCount() {
            return tabs.length;
        }
    }
}
