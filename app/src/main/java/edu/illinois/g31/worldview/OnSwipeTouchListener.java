package edu.illinois.g31.worldview;
import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

/**
 * Created by Asaf Geva on 11/12/2016.
 */

public class OnSwipeTouchListener implements OnTouchListener {


    private final GestureDetector gestureDetector;
    public final RelativeLayout theLayout;
    ScrollView Scroll;

    public OnSwipeTouchListener (Context ctx, RelativeLayout swipedLayout,ScrollView scroll){
        gestureDetector = new GestureDetector(ctx, new GestureListener());
        theLayout = swipedLayout;
        Scroll=scroll;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        // Scroll.requestDisallowInterceptTouchEvent(false);
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 50;
        private static final int SWIPE_VELOCITY_THRESHOLD = 50;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        @Override
        public boolean onSingleTapConfirmed(final MotionEvent e) {
            onClick(); // my method
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            Scroll.requestDisallowInterceptTouchEvent(true);
            
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = 2*(e2.getX() - e1.getX());
                if (Math.abs(diffX) > Math.abs(diffY)) {

                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                    result = true;
                }

                result = true;

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void onClick() {
    }
}
