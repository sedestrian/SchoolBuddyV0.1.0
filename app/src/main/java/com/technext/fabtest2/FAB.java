package com.technext.fabtest2;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class FAB extends android.support.v4.app.Fragment{
    /*private static final String animText = "param1";
    int rotation = 225, time = 600;
    boolean animateButtonRotation = false;

    public interface MyListener{
        public void setDimensions(int Height, int Width);
    }

    public FAB() {
        // Required empty public constructor
    }

    Activity act;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        act = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fab, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        FloatingActionButton imgB = (FloatingActionButton) getView().findViewById(R.id.runCommand);
        imgB.setOnClickListener(this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case (R.id.runCommand):{
                SharedPreferences prefs = this.getActivity().getPreferences(Context.MODE_PRIVATE);
                final FloatingActionButton Fab_Button = (FloatingActionButton) this.getView().findViewById(R.id.runCommand);
                final RelativeLayout bt1 = (RelativeLayout) getActivity().findViewById(R.id.bigHolder1);
                final RelativeLayout bt2 = (RelativeLayout) getActivity().findViewById(R.id.bigHolder2);
                final RelativeLayout bt3 = (RelativeLayout) getActivity().findViewById(R.id.bigHolder3);
                final RelativeLayout bt4 = (RelativeLayout) getActivity().findViewById(R.id.bigHolder4);
                int startColor = 0x00FFFFFF;
                int endColor = 0xD9FFFFFF;
                Boolean buttonActivated = prefs.getBoolean("buttonActivated", false);
                if(!buttonActivated) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Fab_Button.animate().z(getPx(8)).setDuration(time);
                    }
                    Fab_Button.animate().rotation(rotation).setDuration(time);
                    bt1.setVisibility(View.VISIBLE);
                    bt2.setVisibility(View.VISIBLE);
                    bt3.setVisibility(View.VISIBLE);
                    bt4.setVisibility(View.VISIBLE);
                    ObjectAnimator alphaFab1On = ObjectAnimator.ofFloat(bt1, "Alpha", 0f, 1f);
                    alphaFab1On.setDuration(time - 600);
                    ObjectAnimator alphaFab2On = ObjectAnimator.ofFloat(bt2, "Alpha", 0f, 1f);
                    alphaFab2On.setDuration(time - 400);
                    ObjectAnimator alphaFab3On = ObjectAnimator.ofFloat(bt3, "Alpha", 0f, 1f);
                    alphaFab3On.setDuration(time - 200);
                    ObjectAnimator alphaFab4On = ObjectAnimator.ofFloat(bt4, "Alpha", 0f, 1f);
                    alphaFab4On.setDuration(time);
                    final AnimatorSet set2 = new AnimatorSet();
                    set2.playTogether(alphaFab1On, alphaFab2On, alphaFab3On, alphaFab4On);
                    set2.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            Fab_Button.setClickable(false);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            Fab_Button.setClickable(true);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    set2.start();
                    prefs.edit().putBoolean("buttonActivated", true).apply();
                }else{
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Fab_Button.animate().z(getPx(2)).setDuration(time);
                    }
                    Fab_Button.animate().rotation(0).setDuration(time);
                    ObjectAnimator alphaFab1 = ObjectAnimator.ofFloat(bt1, "Alpha", 1f, 0f);
                    alphaFab1.setDuration(time);
                    ObjectAnimator alphaFab2 = ObjectAnimator.ofFloat(bt2, "Alpha", 1f, 0f);
                    alphaFab2.setDuration(time-200);
                    ObjectAnimator alphaFab3 = ObjectAnimator.ofFloat(bt3, "Alpha", 1f, 0f);
                    alphaFab3.setDuration(time-400);
                    ObjectAnimator alphaFab4 = ObjectAnimator.ofFloat(bt4, "Alpha", 1f, 0f);
                    alphaFab4.setDuration(time-600);
                    final AnimatorSet set1 = new AnimatorSet();
                    set1.playTogether(alphaFab1, alphaFab2, alphaFab3, alphaFab4);
                    set1.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            Fab_Button.setClickable(false);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            Fab_Button.setClickable(true);
                            bt1.setVisibility(View.GONE);
                            bt2.setVisibility(View.GONE);
                            bt3.setVisibility(View.GONE);
                            bt4.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    set1.start();
                    prefs.edit().putBoolean("buttonActivated", false).apply();
                }
                break;
            }
            default:{
                System.out.println("Not the right button");
                break;
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public int getPx(int dp){
        float scale = getResources().getDisplayMetrics().density;
        return((int) Math.round(dp*scale));
    }*/

}
