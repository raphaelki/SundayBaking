package com.example.rapha.sundaybaking.ui.recipes;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.example.rapha.sundaybaking.EspressoTestUtil;
import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.data.local.RecipeDatabase;
import com.example.rapha.sundaybaking.util.TaskExecutorWithIdlingResourceRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class RecipesActivityTest {

    @Rule
    public ActivityTestRule<RecipesActivity> activityTestRule = new ActivityTestRule<>(RecipesActivity.class);

    @Rule
    public TaskExecutorWithIdlingResourceRule executorRule = new TaskExecutorWithIdlingResourceRule();

    @Before
    public void disableRecyclerViewAnimations() {
        EspressoTestUtil.disableAnimations(activityTestRule);
    }

    @Test
    public void activityOpensAndShowsRecyclerViewInItsFragment() {
        onView(withId(R.id.recipes_rc)).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnFirstItemOfRecyclerView_opensDetailsActivityWithIngredientsAndInstructionSteps() {
        onView(withContentDescription(R.string.recipe_rv_cd)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withContentDescription(R.string.ingredient_list_cd)).check(matches(isDisplayed()));
        onView(withContentDescription(R.string.instruction_steps_list_cd)).check(matches(isDisplayed()));
        onView(withContentDescription(R.string.recipe_rv_cd)).check(doesNotExist());
    }

    @Test
    public void deleteDatabase() {
        deleteRecipeDatabase();
    }

    @Test
    public void swipeDownOnRecipesList_showsLoadingIndicator() {
        RecipesActivity activity = activityTestRule.getActivity();
        onView(withId(R.id.recipes_swipe_refresh)).perform(swipeDown());
        onView(withId(R.id.recipes_swipe_refresh)).check(matches(isDisplayed()));
        onView(withText(R.string.no_connection_message)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    private void deleteRecipeDatabase() {
        InstrumentationRegistry.getTargetContext().deleteDatabase(RecipeDatabase.NAME);
    }

}