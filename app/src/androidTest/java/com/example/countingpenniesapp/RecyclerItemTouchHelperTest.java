package com.example.countingpenniesapp;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
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
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;


@MediumTest
@RunWith(AndroidJUnit4.class)
public class RecyclerItemTouchHelperTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void test_deleting_item_when_alert_dialog_after_swiping_left() {
        //adding the item to the list
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.spendingAmountView),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_bottom_sheet),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("5"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.spendingNameView),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_bottom_sheet),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("test"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.addingValueButton), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_bottom_sheet),
                                        0),
                                3),
                        isDisplayed()));
        materialButton2.perform(click());

        //swiping left
        onView(ViewMatchers.withId(R.id.spendingsRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));


        //canceling the deletion
        ViewInteraction materialButton4 = onView(
                allOf(withId(android.R.id.button2), withText("Cancel"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                2)));
        materialButton4.perform(scrollTo(), click());

        //check that added item is still there
        ViewInteraction addedSpending = onView(
                allOf(withId(R.id.spendingNameView), withText("test"),
                        isDisplayed()));
        assertNotNull(addedSpending);

    }


    @Test
    public void test_confirming_deletion_of_newly_added_item() {
        onView(ViewMatchers.withId(R.id.spendingsRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));


      //confirming the deletion
        ViewInteraction materialButton4 = onView(
                allOf(withId(android.R.id.button1), withText("Confirm"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton4.perform(scrollTo(), click());
    }

    @Test
    public void test_editing_alert_dialog_appears_when_swiping_right() {
        //swiping right
        onView(ViewMatchers.withId(R.id.spendingsRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeRight()));

        //adding new spending
        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.spendingAmountView),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_bottom_sheet),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("7.90"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.spendingNameView),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_bottom_sheet),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("testing lunch"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.addingValueButton), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_bottom_sheet),
                                        0),
                                3),
                        isDisplayed()));
        materialButton2.perform(click());

        //check that added item is there
        ViewInteraction addedSpending2 = onView(
                allOf(withId(R.id.spendingNameView), withText("testing lunch"),
                        isDisplayed()));
        assertNotNull(addedSpending2);

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


}