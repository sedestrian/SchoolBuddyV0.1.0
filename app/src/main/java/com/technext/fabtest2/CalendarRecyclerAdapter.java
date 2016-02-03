package com.technext.fabtest2;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Alessandro on 26/02/2015.
 */
public class CalendarRecyclerAdapter extends RecyclerView.Adapter<CalendarRecyclerAdapter.MyViewHolder> {

    AppCompatActivity context;
    private LayoutInflater inflater;
    Calendar minCal = Calendar.getInstance();
    Calendar maxCal = Calendar.getInstance();
    Calendar temp = Calendar.getInstance();
    long dayCount;
    final int itemHeight;
    int selected;
    List<Integer> monthDays = new ArrayList<Integer>();


    public List<Integer> getMonthDays() {
        return monthDays;
    }

    public void setMonthDays(List<Integer> monthDays) {
        this.monthDays = monthDays;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public CalendarRecyclerAdapter(Context context) {
        this.context = (AppCompatActivity) context;
        inflater = LayoutInflater.from(context);
        minCal.set(1970, 0, 5);
        maxCal.set(2039, 11, 31);
        long diff = maxCal.getTime().getTime() - minCal.getTime().getTime();
        dayCount = diff / (24*60*60*1000);
        final View header = ((AppCompatActivity)context).findViewById(R.id.header);
        final View cont = ((AppCompatActivity)context).getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        if(context.getResources().getDisplayMetrics().heightPixels > context.getResources().getDisplayMetrics().widthPixels) {
            itemHeight = ((cont.getHeight() / 2) - (((AppCompatActivity) context).getSupportActionBar().getHeight() / 2) - (header.getHeight()) - (((AppCompatActivity) context).findViewById(R.id.status).getHeight() / 2)) / 6;
        }else{
            itemHeight = ((cont.getHeight()) - (((AppCompatActivity) context).getSupportActionBar().getHeight()) - (header.getHeight()) - (((AppCompatActivity) context).findViewById(R.id.status).getHeight())) / 6;
        }
        Calendar today = Calendar.getInstance();
        final long position = ((today.getTime().getTime() - minCal.getTime().getTime()) / (24*60*60*1000))+1;
        int newPos = (int) position - (getDayByPos((int) position) - 1);
        List<Integer> temp = new ArrayList<>();
        for(int i = newPos; i < newPos + getDaysInMonthByPos(newPos); i++){
            temp.add(i);
        }
        monthDays = temp;
    }

    private int getDayByPos(int position){
        Calendar tod = Calendar.getInstance();
        tod.set(1970, 0, 5);
        tod.add(Calendar.DATE, position);
        return tod.get(Calendar.DATE);
    }
    private int getDaysInMonthByPos(int position){
        Calendar tod = Calendar.getInstance();
        tod.set(1970, 0, 5);
        tod.add(Calendar.DATE, position);
        return tod.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cal_day, parent, false);
        view.getLayoutParams().height = itemHeight;
        MyViewHolder holder = new MyViewHolder(view);
        holder.bg.getLayoutParams().height = itemHeight - (Helper.getPx(context, 2));
        holder.bg.getLayoutParams().width = itemHeight - (Helper.getPx(context, 2));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        temp.set(1970, 0, 5);
        temp.add(Calendar.DATE, position);
        holder.day.setText(temp.get(Calendar.DAY_OF_MONTH)+"");
        if(position == selected){
            holder.bg.setBackground(context.getResources().getDrawable(R.drawable.dateoval));
        }else{
            holder.bg.setBackground(null);
        }
        //System.out.println(position);
        if(monthDays.contains(position)){
            holder.day.setTextColor(Color.WHITE);
        }else{
            holder.day.setTextColor(context.getResources().getColor(R.color.greyedText));
        }
        //holder.bg.setBackground();
    }

    @Override
    public int getItemCount() {
        return (int)dayCount;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        RelativeLayout bg;
        TextView day;
        public MyViewHolder(View itemView) {
            super(itemView);
            bg = (RelativeLayout) itemView.findViewById(R.id.bgSel);
            day = (TextView) itemView.findViewById(R.id.text);
            bg.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            RecyclerView parent = (RecyclerView)context.findViewById(R.id.recycledCal);
            GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
            if(selected > manager.findFirstVisibleItemPosition() && selected < manager.findLastVisibleItemPosition()) {
                View item = manager.findViewByPosition(selected).findViewById(R.id.bgSel);
                item.setBackground(null);
            }
            selected = getAdapterPosition();
            bg.setBackground(ContextCompat.getDrawable(context, R.drawable.dateoval));
        }
    }
}
