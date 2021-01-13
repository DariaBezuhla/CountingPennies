package com.example.countingpenniesapp;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class MainActivity_InstrumentationTests {


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    MainActivity activity = null;

    public static final String TEST_STRING = "Some string";
    public static final String CURRENT_BALANCE = "balance";



    @Before
    public void setUp() {
        activity = mActivityTestRule.getActivity();
    }

    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.countingpenniesapp", appContext.getOpPackageName());
    }


    @Test
    public void clickingOnFabButtonOpensAddingValueLayout() {
        assertNotNull(activity.findViewById(R.id.fab));
        onView(withId(R.id.fab)).perform(click());

        ViewInteraction addingValueView = onView(
                withId(R.id.addingValueButton));
        addingValueView.check(matches(isDisplayed()));
        assertNotNull(addingValueView);

    }

    @Test
    public void afterItemIsAddedDialogHandlerClosesAddingNewValueView() {
        assertNotNull(activity.findViewById(R.id.fab));
        onView(withId(R.id.fab)).perform(click());

        ViewInteraction spendingAmountView = onView(
                withId(R.id.spendingAmountView));
        spendingAmountView.check(matches(isDisplayed()));
        assertNotNull(spendingAmountView);

        onView(withId(R.id.spendingAmountView)).perform(typeText("7.80"));
        onView(withId(R.id.spendingNameView)).perform(typeText("coffee and test"));
        onView(withId(R.id.addingValueButton)).perform(click());

        ViewInteraction addedSpending = onView(
                allOf(withId(R.id.spendingNameView), withText("coffee and test"),
                        isDisplayed()));
        assertNotNull(addedSpending);
    }


}