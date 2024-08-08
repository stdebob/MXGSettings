// this file is a part of MxGSettings. if you kang it w/o permission, or you don't credit, you're COOKED. T-T _/\_
package com.mxg.settings.module.hook.home.gesture;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.mxg.settings.module.base.BaseHook;
import com.mxg.settings.module.hook.GlobalActions;

public class HotSeatSwipe extends BaseHook {

    private GestureDetector mDetectorHorizontal;

    @Override
    public void init() {
        findAndHookMethod("com.miui.home.launcher.hotseats.HotSeats", "dispatchTouchEvent", MotionEvent.class, new MethodHook() {
            @Override
            protected void before(final MethodHookParam param) throws Throwable {
                MotionEvent ev = (MotionEvent) param.args[0];
                if (ev == null) return;

                ViewGroup hotSeat = (ViewGroup) param.thisObject;
                Context helperContext = hotSeat.getContext();
                if (helperContext == null) return;
                if (mDetectorHorizontal == null)
                    mDetectorHorizontal = new GestureDetector(helperContext, new SwipeListenerHorizontal(hotSeat));
                mDetectorHorizontal.onTouchEvent(ev);
            }
        });
    }


    private static class SwipeListenerHorizontal extends GestureDetector.SimpleOnGestureListener {

        private final int SWIPE_MIN_DISTANCE_HORIZ;
        private final int SWIPE_THRESHOLD_VELOCITY;

        final Context helperContext;

        SwipeListenerHorizontal(Object cellLayout) {
            helperContext = ((ViewGroup) cellLayout).getContext();
            float density = helperContext.getResources().getDisplayMetrics().density;
            SWIPE_MIN_DISTANCE_HORIZ = Math.round(75 * density);
            SWIPE_THRESHOLD_VELOCITY = Math.round(33 * density);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1 == null || e2 == null) return false;

            if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE_HORIZ && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
                return GlobalActions.handleAction(helperContext, "prefs_key_home_gesture_right_swipe");

            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE_HORIZ && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
                return GlobalActions.handleAction(helperContext, "prefs_key_home_gesture_left_swipe");

            return false;
        }
    }
}
