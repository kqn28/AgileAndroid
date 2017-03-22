package edu.umd.cs.agileandroid;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.PositionAssertions.isAbove;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BacklogActivityEspressoTest extends BaseActivityEspressoTest {
    @Rule
    public ActivityTestRule<BacklogActivity> activityRule = new ActivityTestRule<BacklogActivity>(BacklogActivity.class);

    @Test
    public void useAppContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("edu.umd.cs.agileandroid", appContext.getPackageName());
    }

    @Test
    public void testUiElements() {
        Espresso.closeSoftKeyboard();

        // create story icon
        onView(withId(R.id.menu_item_create_story)).check(matches(isDisplayed()));

        // active sprint icon
        onView(withId(R.id.menu_item_active_sprint)).check(matches(isDisplayed()));

        onView(withText("Story 1")).check(matches(isDisplayed()));
        onView(withText("Story 2")).check(matches(isDisplayed()));
        onView(withText("Story 3")).check(matches(isDisplayed()));
    }

    @Test
    public void testCreateStoryButton() {
        onView(withId(R.id.menu_item_create_story)).perform(click());

        Activity currentActivity = getActivityInstance();
        assertTrue(currentActivity.getClass().isAssignableFrom(StoryActivity.class));

        Espresso.pressBack();
    }

    @Test
    public void testActiveSprintButton() {
        onView(withId(R.id.menu_item_active_sprint)).perform(click());

        Activity currentActivity = getActivityInstance();
        assertTrue(currentActivity.getClass().isAssignableFrom(SprintActivity.class));

        Espresso.pressBack();
    }

    @Test
    public void testStoryListToasts() {
        onView(withText("Story 1")).perform(click());
        onView(withText("Story 1 : 1.0 : CURRENT : IN_PROGRESS")).inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void testStoryListOrder() {
        onView((withText("Story 1"))).check(isAbove(withText("Story 2")));
        onView((withText("Story 2"))).check(isAbove(withText("Story 3")));
    }

    @Override
    public Activity getActivity() {
        return (Activity)activityRule.getActivity();
    }
}
