package com.technext.fabtest2;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Alessandro on 4/14/2015.
 */
public class MyGridLayoutManager extends GridLayoutManager {

    Context context;

    public MyGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
        this.context = context;
    }

    public MyGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
        this.context = context;

    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        int duration = 5000;
        //LinearSmoothScroller smoothScroller = new LinearSmoothScroller(context)
        //{
            //@Override
            //public PointF computeScrollVectorForPosition(int targetPosition)
            //{
            //    int yDelta = calculateCurrentDistanceToPosition(targetPosition);
            //    return new PointF(0, yDelta);
            //}

            // This is the important method. This code will return the amount of time it takes to scroll 1 pixel.
            // This code will request X milliseconds for every Y DP units.
            //@Override
            //protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics)
            //{
            //    return 100 / TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, displayMetrics);
            //}
            View firstVisibleChild = recyclerView.getChildAt(0);
            int itemHeight = firstVisibleChild.getHeight();
            int currentPosition = recyclerView.getChildLayoutPosition(firstVisibleChild);
            int distanceInPixels = Math.abs((currentPosition - position) * itemHeight);
            if (distanceInPixels == 0) {
                distanceInPixels = (int) Math.abs(firstVisibleChild.getY());
            }
            SmoothScroller smoothScroller = new SmoothScroller(recyclerView.getContext(), distanceInPixels, duration);
            smoothScroller.setTargetPosition(position);
            startSmoothScroll(smoothScroller);
        //};
        //smoothScroller.setTargetPosition(position);

        //startSmoothScroll(smoothScroller);
    }

    private class SmoothScroller extends LinearSmoothScroller {
        private static final int TARGET_SEEK_SCROLL_DISTANCE_PX = 10000;
        private final float distanceInPixels;
        private final float duration;

        public SmoothScroller(Context context, int distanceInPixels, int duration) {
            super(context);
            this.distanceInPixels = distanceInPixels;
            float millisecondsPerPx = calculateSpeedPerPixel(context.getResources().getDisplayMetrics());
            this.duration = distanceInPixels < TARGET_SEEK_SCROLL_DISTANCE_PX ?
                    (int) (Math.abs(distanceInPixels) * millisecondsPerPx) : duration;
        }

        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return MyGridLayoutManager.this
                    .computeScrollVectorForPosition(targetPosition);
        }

        @Override
        protected int calculateTimeForScrolling(int dx) {
            float proportion = (float) dx / distanceInPixels;
            return (int) (duration * proportion);
        }
    }
}
