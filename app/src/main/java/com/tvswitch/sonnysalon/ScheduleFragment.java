package com.tvswitch.sonnysalon;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment implements View.OnClickListener{
    public static String TAG = "ScheduleFragment";
    private ViewGroup root;
    private FloatingActionMenu mFABmenu;

    public ScheduleFragment(){}

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = (ViewGroup)inflater.inflate(R.layout.fragment_schedule, container, false);
        if(mFABmenu != null){
            ArrayList<FloatingActionMenu.Item> items =  mFABmenu.getSubActionItems();
            for(FloatingActionMenu.Item item: items){
                item.view.setOnClickListener(this);
            }
        }
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
}
