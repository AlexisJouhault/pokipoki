package com.pokemeows.pokipoki.tools;

import android.view.MotionEvent;

public interface ScrollableListener {
    boolean isViewBeingDragged(MotionEvent event);
}
