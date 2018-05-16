package com.example.rapha.sundaybaking;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class SwipeRefreshLayoutMatchers {

    /*
     * Custom Matcher to match the refreshing state of SwipeRefreshLayout
     * Kotlin version found here: https://stackoverflow.com/questions/50129234/espresso-test-to-verify-that-swiperefreshlayout-is-showing-the-refreshing-indica
     */
    public static Matcher<View> isRefreshing() {
        return new BoundedMatcher<View, SwipeRefreshLayout>(SwipeRefreshLayout.class) {
            @Override
            protected boolean matchesSafely(SwipeRefreshLayout item) {
                return item.isRefreshing();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is refreshing");
            }
        };
    }
}
