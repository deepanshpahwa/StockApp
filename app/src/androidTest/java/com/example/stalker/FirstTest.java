package com.example.stalker;



import android.content.ClipData;
import android.view.View;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.greaterThan;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class FirstTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void MainActivityWidgetsDisplayed() {
        onView(withId(R.id.rvStocks)).check(matches(isDisplayed()));
        onView(withId(R.id.my_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.rvStocks))
                .check(matches(isDisplayed()));
    }

    @Test
    public void MainActivityRowOnClicked() throws InterruptedException {

        RecyclerViewInteraction.<ClipData.Item>onRecyclerView(withId(R.id.rvStocks))
                .withItems(items)
                .check(new RecyclerViewInteraction.ItemViewAssertion<ClipData.Item>() {
                    @Override
                    public void check(ClipData.Item item, View view, NoMatchingViewException e) {
                        matches(hasDescendant(withText(item.getDisplayName())))
                                .check(view, e);
                    }
                });//        onView(withId(R.id.rvStocks)).check(matches(isDisplayed()));
//Thread.sleep(5000);
//        onView(withId(R.id.rvStocks))
//                .perform(RecyclerViewActions.scrollToPosition(1));
//        onView(withId(R.id.ASD_indicator1)).check(matches(isDisplayed()));

    }


}
