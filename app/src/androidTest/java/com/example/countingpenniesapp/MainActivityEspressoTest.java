package com.example.countingpenniesapp;


import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityIsSet() {
        ViewInteraction textView2 = onView(
                allOf(withText("Balance:"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.euroSign), withText("€ "),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView3.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.currentBalance),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView5.check(matches(isDisplayed()));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.fab),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView6.check(matches(isDisplayed()));

    }

    @Test
    public void test_that_touchOutside_DialogWindow_closes_Dialog_and_Value_is_Not_Saved() {

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.spendingAmountView),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_bottom_sheet),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("700"), closeSoftKeyboard());

        ViewInteraction view = onView(
                allOf(withId(R.id.touch_outside),
                        childAtPosition(
                                allOf(withId(R.id.coordinator),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                0),
                        isDisplayed()));
        view.perform(click());


    }


    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }


    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex;
            int viewObjHash;

            @SuppressLint("DefaultLocale")
            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("with index: %d ", index));
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (matcher.matches(view) && currentIndex++ == index) {
                    viewObjHash = view.hashCode();
                }
                return view.hashCode() == viewObjHash;
            }
        };
    }


}
