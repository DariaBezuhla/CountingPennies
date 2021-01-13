package com.example.countingpenniesapp;

import androidx.test.espresso.PerformException;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withText;




@RunWith(AndroidJUnit4.class)
public class RecyclerViewTests {


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test(expected = PerformException.class)
    public void itemWithText_doesNotExist() {
        onView(ViewMatchers.withId(R.id.spendingsRecyclerView))
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText("not in the list"))
                ));
    }

}