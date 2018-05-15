package com.example.rapha.sundaybaking.util;

import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.CountingIdlingResource;

public class EspressoIdlingResource {

    private static final String RESOURCE_NAME = "GLOBAL";

    private static CountingIdlingResource idlingResource = new CountingIdlingResource(RESOURCE_NAME);

    public static void increment() {
        idlingResource.increment();
    }

    public static void decrement() {
        idlingResource.decrement();
    }

    public static IdlingResource getIdlingResource() {
        return idlingResource;
    }
}
