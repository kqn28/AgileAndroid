package edu.umd.cs.agileandroid;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.PositionAssertions.isAbove;
import static android.support.test.espresso.assertion.PositionAssertions.isLeftOf;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SprintActivityEspressoTest extends BaseActivityEspressoTest {
    @Rule
    public ActivityTestRule<SprintActivity> activityRule = new ActivityTestRule<SprintActivity>(SprintActivity.class);

    @Test
    public void useAppContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("edu.umd.cs.agileandroid", appContext.getPackageName());
    }

    @Test
    public void testUiElements() {
        // 3 second level LinearLayouts
        onView(withId(R.id.todo_column)).check(matches(isDisplayed()));
        onView(withId(R.id.inprogress_column)).check(matches(isDisplayed()));
        onView(withId(R.id.done_column)).check(matches(isDisplayed()));

        // labels
        onView(withText(R.string.todo_label)).check(matches(isDisplayed()));
        onView(withText(R.string.inprogress_label)).check(matches(isDisplayed()));
        onView(withText(R.string.done_label)).check(matches(isDisplayed()));
    }

    @Test
    public void testStoriesInColumns() {
        onView((withText("Story 3"))).check(matches(isDisplayed()));
        onView((withText("Story 6"))).check(matches(isDisplayed()));
        onView((withText("Story 3"))).check(isAbove(withText("Story 6")));

        onView((withText("Story 4"))).check(matches(isDisplayed()));
        onView((withText("Story 7"))).check(matches(isDisplayed()));
        onView((withText("Story 4"))).check(isAbove(withText("Story 7")));

        onView((withText("Story 2"))).check(matches(isDisplayed()));
        onView((withText("Story 8"))).check(matches(isDisplayed()));
        onView((withText("Story 2"))).check(isAbove(withText("Story 8")));

        onView((withText("Story 3"))).check(matches(isDisplayed()));
        onView((withText("Story 1"))).check(matches(isDisplayed()));
        onView((withText("Story 2"))).check(matches(isDisplayed()));
        onView((withText("Story 3"))).check(isLeftOf(withText("Story 1")));
        onView((withText("Story 1"))).check(isLeftOf(withText("Story 2")));
    }

    @Test
    public void testStoryDetailToast() {
        onView(withText("Story 10")).perform(click());
        onView(withText("Story 10 : 1.0 : CURRENT : IN_PROGRESS")).inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Override
    public Activity getActivity() {
        return (Activity)activityRule.getActivity();
    }
}
