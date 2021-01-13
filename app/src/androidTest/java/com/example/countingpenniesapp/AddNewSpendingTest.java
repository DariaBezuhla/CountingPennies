package com.example.countingpenniesapp;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.countingpenniesapp.MainActivityEspressoTest.withIndex;
import static org.hamcrest.Matchers.allOf;

public class AddNewSpendingTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void test_insert_Expense_spending_in_recycler_view() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.spendingAmountView),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_bottom_sheet),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("4.60"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.spendingNameView),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_bottom_sheet),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("coffee"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.addingValueButton), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_bottom_sheet),
                                        0),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withIndex(withId(R.id.nameOfSpending), 0), withText("coffee"), isDisplayed()));
        textView.check(matches(withText("coffee")));

        ViewInteraction textView1 = onView(
                allOf(withIndex(withId(R.id.valueOfSpending), 0), withText("4.60"), isDisplayed()));
        textView1.check(matches(withText("4.60")));

    }

    @Test
    public void test_insert_income_spending() {
        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.spendingAmountView),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_bottom_sheet),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("-2000.30"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.spendingNameView),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_bottom_sheet),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("monthly salary"), closeSoftKeyboard());


        //TODO: the part where espresso does not want to take the root of the spinner item
//        ViewInteraction appCompatSpinner = onView(
//                allOf(withId(R.id.categories_spinner),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.design_bottom_sheet),
//                                        0),
//                                1),
//                        isDisplayed()));
//        appCompatSpinner.perform(click());
//
//        DataInteraction appCompatCheckedTextView = onData(anything())
//                .inAdapterView(childAtPosition(
//                        withSpinnerText(containsString("Income")),
//                        0))
//                .atPosition(1);
//        appCompatCheckedTextView.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.addingValueButton), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_bottom_sheet),
                                        0),
                                3),
                        isDisplayed()));
        materialButton.perform(click());
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