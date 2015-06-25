package com.tvswitch.sonnylibrary.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tvswitch.sonnylibrary.R;

import java.util.List;

/**
 * Created by Tuong on 6/18/15.
 */
public class ScheduleRecyclerAdapter extends RecyclerView.Adapter<ScheduleRecyclerAdapter.ScheduleRowHolder>{
    private List<ScheduleItem> scheduleItemList;
    private LayoutInflater inflater;
    public ScheduleRecyclerAdapter(Context context, List<ScheduleItem> scheduleItemList){
        this.inflater = LayoutInflater.from(context);
        this.scheduleItemList = scheduleItemList;
    }

    @Override
    public ScheduleRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.main_menu_item, viewGroup, false);
        ScheduleRowHolder scheduleRowHolder = new ScheduleRowHolder(view);
        return  scheduleRowHolder;
    }

    @Override
    public void onBindViewHolder(ScheduleRowHolder scheduleRowHolder, int i) {
        ScheduleItem scheduleItem = scheduleItemList.get(i);
        scheduleRowHolder.color.setBackgroundColor(scheduleItem.getColor());
        scheduleRowHolder.title.setText(scheduleItem.getName());
    }

    @Override
    public int getItemCount() {
        return (scheduleItemList != null?scheduleItemList.size():0);
    }

    public void addScheduleItem(ScheduleItem scheduleItem){
        scheduleItemList.add(scheduleItem);
        notifyItemInserted(scheduleItemList.size());
    }

    public void removeItem(ScheduleItem item) {
        int position = scheduleItemList.indexOf(item);
        if (position != -1) {
            scheduleItemList.remove(item);
            notifyItemRemoved(position);
        }
    }

    public void removeItem(int position) {
        scheduleItemList.remove(position);
        notifyItemRemoved(position);
    }

    public void setSelectedItem(ScheduleRowHolder scheduleRowHolder, int position){
        if(scheduleItemList.get(position).getColor() != Color.WHITE) {
            scheduleItemList.get(position).setColor(Color.WHITE);
        }
        notifyItemChanged(position);
    }


    public class ScheduleRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public View color;
        public TextView title;
        public ScheduleRowHolder(View view){
            super(view);
            color = view.findViewById(R.id.highlightTextView);
            title = (TextView) view.findViewById(R.id.nameTextView);
            title.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            removeItem(getAdapterPosition());
            setSelectedItem(this,getAdapterPosition());
        }
    }

}
