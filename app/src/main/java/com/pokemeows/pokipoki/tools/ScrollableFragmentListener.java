package com.pokemeows.pokipoki.tools;

public interface ScrollableFragmentListener {

    void onFragmentAttached(ScrollableListener fragment, int position);

    void onFragmentDetached(ScrollableListener fragment, int position);
}
