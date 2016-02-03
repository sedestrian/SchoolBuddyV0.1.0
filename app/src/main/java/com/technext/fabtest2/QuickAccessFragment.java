package com.technext.fabtest2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.tr4android.support.extension.widget.*;
import com.tr4android.support.extension.widget.FloatingActionMenu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class QuickAccessFragment extends android.support.v4.app.Fragment {
    SharedPreferences prefs;
    AppCompatActivity context;
    private RecyclerView calendar;
    private Toolbar toolbar;
    CalendarRecyclerAdapter adapter;
    QuickAccessFragment reference;
    boolean rotated = false;


    public QuickAccessFragment() {
        // Required empty public constructor
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            android.support.design.widget.Snackbar.make(v, "Clicked item "+((com.github.clans.fab.FloatingActionButton)v).getLabelText(), Snackbar.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        reference = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View mainlayout;

        if(getResources().getDisplayMetrics().widthPixels < getResources().getDisplayMetrics().heightPixels) {
            mainlayout = inflater.inflate(R.layout.fragment_quick_access, container, false);
        }else{
            mainlayout = inflater.inflate(R.layout.fragment_quick_access_rotated, container, false);
//            RecyclerView calendar = (RecyclerView) mainlayout.findViewById(R.id.recycledCal);
            rotated = true;
        }
        context = (AppCompatActivity) getActivity();
        prefs = context.getPreferences(Context.MODE_PRIVATE);
        prefs.edit().putBoolean("buttonActivated", false).apply();
        calendar = (RecyclerView) mainlayout.findViewById(R.id.recycledCal);

        calendar.setLayoutManager(new MyGridLayoutManager(context, 7));
//        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        layout.setLayoutParams(params);


        mainlayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // Ensure you call it only once :
                mainlayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                RelativeLayout below = (RelativeLayout) context.findViewById(R.id.below);
                com.github.clans.fab.FloatingActionMenu menu = (com.github.clans.fab.FloatingActionMenu) context.findViewById(R.id.menu);
                final View header = context.findViewById(R.id.header);
                adapter = new CalendarRecyclerAdapter(context);
                calendar.setAdapter(adapter);
                RelativeLayout layout = (RelativeLayout) mainlayout.findViewById(R.id.custom);
                layout.setPivotY(0);
                if(reference.rotated){

                    calendar.getLayoutParams().width = calendar.getWidth()/2;
                    layout.getLayoutParams().width = layout.getWidth()/2;
                    below.getLayoutParams().width = below.getWidth()/2;
                    header.getLayoutParams().width = header.getWidth()/2;
                    menu.getLayoutParams().width = below.getWidth();
//                    header.getLayoutParams().height = header.getHeight()/2;
                }
                toolbar = (Toolbar) context.findViewById(R.id.toolbar);
                final View content = context.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
                int real_half = (content.getHeight()/2) + (context.getSupportActionBar().getHeight()/2) + (context.findViewById(R.id.status).getHeight()/2);

                com.github.clans.fab.FloatingActionButton just = (com.github.clans.fab.FloatingActionButton)context.findViewById(R.id.just_butt);
                just.setOnClickListener(listener);
                com.github.clans.fab.FloatingActionButton oral = (com.github.clans.fab.FloatingActionButton)context.findViewById(R.id.oral_butt);
                oral.setOnClickListener(listener);
                com.github.clans.fab.FloatingActionButton test = (com.github.clans.fab.FloatingActionButton)context.findViewById(R.id.test_butt);
                test.setOnClickListener(listener);
                com.github.clans.fab.FloatingActionButton mark = (com.github.clans.fab.FloatingActionButton)context.findViewById(R.id.mark_butt);
                mark.setOnClickListener(listener);
                RelativeLayout.LayoutParams par = (RelativeLayout.LayoutParams)menu.getLayoutParams();
                par.topMargin -= Helper.getPx(context, 37);
                menu.setLayoutParams(par);

                menu.setAlpha(0);



                if(!rotated)
                    below.getLayoutParams().height = (content.getHeight()/2) - ((context.getSupportActionBar().getHeight()/2) + (context.findViewById(R.id.status).getHeight()/2));


                final int calHeight = (content.getHeight() / 2) - (context.getSupportActionBar().getHeight() / 2) - (header.getHeight()) - (context.findViewById(R.id.status).getHeight()/2);
                calendar.setOnScrollListener(calScrollListener);

                TextView belowText = (TextView) context.findViewById(R.id.belowText);
                belowText.setText("All events will be here");

                layout.bringToFront();
                header.bringToFront();
                calendar.bringToFront();
                toolbar.bringToFront();
                menu.bringToFront();

                float change = 1f - ((((float)(layout.getHeight())/2f) /*- ((float)(context.getSupportActionBar().getHeight())/2f) - ((float)(context.findViewById(R.id.status).getHeight())/2)*/)/((float)(layout.getHeight())));

                if(!rotated) {
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, calHeight);
                    params.addRule(RelativeLayout.BELOW, R.id.header);
                    calendar.setLayoutParams(params);
                }
                calendar.setAlpha(0);
                GridLayoutManager layoutManager = (GridLayoutManager) calendar.getLayoutManager();
                calendar.getViewTreeObserver().addOnGlobalLayoutListener(
                        new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                calendar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                setToToday(false);
                                //whitenDays(false);
                            }
                        }
                );



                ObjectAnimator scaleCal = ObjectAnimator.ofFloat(layout, "scaleY", 1f, change);
                scaleCal.setDuration(500);
                scaleCal.setStartDelay(500);
                ObjectAnimator alphaFab = ObjectAnimator.ofFloat(menu, "Alpha", 0f, 1f);
                alphaFab.setDuration(500);
                ObjectAnimator alphaCal = ObjectAnimator.ofFloat(calendar, "Alpha", 0f, 1f);
                alphaCal.setDuration(500);
                ObjectAnimator alphaHead = ObjectAnimator.ofFloat(header, "Alpha", 0f, 1f);
                alphaHead.setDuration(500);

                AnimatorSet animSet = new AnimatorSet();
                if(!rotated) {
                    animSet.play(scaleCal).before(alphaCal);
                }
                animSet.play(alphaCal).with(alphaHead);
                animSet.play(alphaHead).before(alphaFab);
                animSet.start();
                header.bringToFront();
            }
        });
        return mainlayout;
    }

    public void setToToday(Boolean whiten){
        GridLayoutManager manager = (GridLayoutManager) calendar.getLayoutManager();
        int first = manager.findFirstVisibleItemPosition();
        int last = manager.findLastVisibleItemPosition();
        int firstComplete = manager.findFirstCompletelyVisibleItemPosition();
        int lastComplete = manager.findLastCompletelyVisibleItemPosition();
        Calendar from = Calendar.getInstance();
        from.set(1970, 0, 5);
        Calendar today = Calendar.getInstance();
        final long position = ((today.getTime().getTime() - from.getTime().getTime()) / (24*60*60*1000));
        int rowPos = (int)Math.floor(position%7);

        int selPos = adapter.getSelected();
        if(selPos >= first && selPos <= last) {
            manager.findViewByPosition(selPos).findViewById(R.id.bgSel).setBackground(null);
        }
        adapter.setSelected((int)position);
        if(position >= first && position <= last){
            manager.findViewByPosition((int)position).findViewById(R.id.bgSel).setBackground(getResources().getDrawable(R.drawable.dateoval));
        }

        if((first < (position - rowPos)) && (last >= (position + (7-rowPos)))){

        }
        else {
            int temp = getDayByPos((int) position);
            int newPos = (int) position - (temp - 1);

            if (position > (first - 100) && position < (last + 100)) {
            if(newPos > lastComplete){
                newPos += getDaysInMonthByPos(newPos) - getDayByPos(newPos);
            }
            calendar.smoothScrollToPosition(newPos);

            }else {
            calendar.stopScroll();
            manager.scrollToPositionWithOffset(newPos, 0);
            if(whiten)
                whitenDays(false, false);
            }
        }
        //whitenDays(calendar);
        context.getSupportActionBar().setTitle(new SimpleDateFormat("LLLL", new Locale("en")).format(today.getTime())+" "+today.get(Calendar.YEAR));
    }

    public void whitenDays(boolean scroll, boolean today){
        List<Integer> monthDays = new ArrayList<Integer>();
        GridLayoutManager manager = (GridLayoutManager)calendar.getLayoutManager();
        int lastItemThirdRow = manager.findFirstCompletelyVisibleItemPosition() + 20;
        int firstDayPosition = lastItemThirdRow - (getDayByPos(lastItemThirdRow)-1);
        int lastDayPosition = firstDayPosition + getDaysInMonthByPos(firstDayPosition);
        if(today){
            Calendar from = Calendar.getInstance();
            from.set(1970, 0, 5);
            Calendar todayCal = Calendar.getInstance();
            final long position = ((todayCal.getTime().getTime() - from.getTime().getTime()) / (24*60*60*1000));
            firstDayPosition = (int) position - (getDayByPos((int) position) - 1);

            lastDayPosition = firstDayPosition + getDaysInMonthByPos(firstDayPosition);
            for (int i = firstDayPosition; i < lastDayPosition; i++){
                monthDays.add(i);
            }
        }else {
            for (int i = firstDayPosition; i < lastDayPosition; i++) {
                monthDays.add(i);
            }
        }
        adapter.setMonthDays(monthDays);
        for(int i = manager.findFirstVisibleItemPosition(); i <= manager.findLastVisibleItemPosition(); i++){
            if(i >= firstDayPosition && i < lastDayPosition) {
                if (i > manager.findFirstVisibleItemPosition() && i < manager.findLastVisibleItemPosition()) {
                    ((TextView) (manager.findViewByPosition(i).findViewById(R.id.text))).setTextColor(Color.WHITE);
                }
            }
            else{
                if(i > manager.findFirstVisibleItemPosition() && i < manager.findLastVisibleItemPosition()){
                    ((TextView) (manager.findViewByPosition(i).findViewById(R.id.text))).setTextColor(getResources().getColor(R.color.greyedText));
                }
            }
        }
        if(scroll) {
            calendar.stopScroll();
            manager.scrollToPositionWithOffset(lastItemThirdRow - (getDayByPos(lastItemThirdRow) - 1), 0);
        }
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

    private String getMonthNameByPos(int position){
        Calendar temp = Calendar.getInstance();
        temp.set(1970, 0, 5);
        temp.add(Calendar.DATE, position);
        return new SimpleDateFormat("LLLL", new Locale("en")).format(temp.getTime());
    }

    private int getMonthNumByPos(int position){
        Calendar temp = Calendar.getInstance();
        temp.set(1970, 0, 5);
        temp.add(Calendar.DATE, position);
        return temp.get(Calendar.MONTH);
    }

    private int getYearByPos(int position){
        Calendar temp = Calendar.getInstance();
        temp.set(1970, 0, 5);
        temp.add(Calendar.DATE, position);
        return temp.get(Calendar.YEAR);
    }

    public int getPx(int dp){
        float scale = getResources().getDisplayMetrics().density;
        return((int) Math.round(dp*scale));
    }

    RecyclerView.OnScrollListener calScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            System.out.println("Scroll changed"+newState);
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE){
                whitenDays(true, false);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            GridLayoutManager manager = (GridLayoutManager) calendar.getLayoutManager();
            int checkPos = manager.findFirstCompletelyVisibleItemPosition() + 20;
            Calendar temp = Calendar.getInstance();
            temp.set(1970, 0, 5);
            temp.add(Calendar.DATE, checkPos);
            context.getSupportActionBar().setTitle(new SimpleDateFormat("LLLL", new Locale("en")).format(temp.getTime())+" "+temp.get(Calendar.YEAR));
        }
    };

}
