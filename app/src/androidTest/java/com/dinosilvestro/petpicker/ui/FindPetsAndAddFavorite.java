package com.dinosilvestro.petpicker.ui;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.dinosilvestro.petpicker.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FindPetsAndAddFavorite {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void findPetsAndAddFavorite() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.getSheltersButtonwithGps), withText("Find nearby pet shelters"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.shelterRecyclerView), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(5, click()));

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.getPetsButton), withText("PICK PETS"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.petRecyclerView), isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction floatingActionButton = onView(
                withId(R.id.pickActionButton));
        floatingActionButton.perform(scrollTo(), click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.menu_pet_picks), withContentDescription("My Pet Picks"), isDisplayed()));
        actionMenuItemView.perform(click());

    }

}
