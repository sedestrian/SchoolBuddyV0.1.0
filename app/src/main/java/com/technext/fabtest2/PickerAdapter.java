package com.technext.fabtest2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.Theme;

/**
 * Created by Alessandro on 03/03/2015.
 */
public class PickerAdapter extends RecyclerView.Adapter<PickerAdapter.MyViewHolder> {

    LayoutInflater inflater;
    MyActivity context;

    String[] activities = {"Quick Access", "Marks", "Schedule", "Tests"};
    int[] icons = {R.drawable.ic_event_grey_24dp, R.drawable.ic_border_color_grey_24dp, R.drawable.ic_school_grey_24dp, R.drawable.ic_insert_drive_file_grey_24dp};

    public PickerAdapter(Context context) {
        this.context = (MyActivity) context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public PickerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.pickerview, parent, false);
        PickerAdapter.MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PickerAdapter.MyViewHolder holder, int position) {
        holder.title.setText(activities[position]);
        holder.icon.setImageDrawable(ContextCompat.getDrawable(context, icons[position]));
    }

    @Override
    public int getItemCount() {
        return activities.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{
        TextView title;
        ImageView icon;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.pickerText);
            icon = (ImageView) itemView.findViewById(R.id.drawerIcon);
        }

        @Override
        public void onClick(View v) {
            switch (((TextView)v.findViewById(R.id.pickerText)).getText().toString()){
                case "Marks" :
                    if(!(context.getSupportFragmentManager().findFragmentByTag("activity") instanceof MarksFragment)) {
                        FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
                        MarksFragment marksFragment = new MarksFragment();
                        transaction.replace(R.id.fragContainer,marksFragment, "activity");
                        //transaction.addToBackStack(null);
                        transaction.commit();
                        context.getSupportActionBar().setTitle("Marks");
                    }
                    break;
                case "Quick Access" :
                    if(!(context.getSupportFragmentManager().findFragmentByTag("activity") instanceof QuickAccessFragment)){
                        FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
                        QuickAccessFragment fragment = new QuickAccessFragment();
                        transaction.replace(R.id.fragContainer,fragment, "activity");
                        //transaction.addToBackStack(null);
                        transaction.commit();
                    }
            }
            ((DrawerLayout)context.findViewById(R.id.drawer_layout_main)).closeDrawers();
        }
    }
}
