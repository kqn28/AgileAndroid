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

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class StoryActivityEspressoTest extends BaseActivityEspressoTest {
    private Activity currentActivity;

    @Rule
    public ActivityTestRule<StoryActivity> activityRule = new ActivityTestRule<StoryActivity>(StoryActivity.class);

    @Test
    public void useAppContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("edu.umd.cs.agileandroid", appContext.getPackageName());
    }

    @Test
    public void testUiElements() {
        Espresso.closeSoftKeyboard();

        // summary label & field
        onView(withText(R.string.summary_label)).check(matches(isDisplayed()));
        onView(withId(R.id.summary)).check(matches(isDisplayed()));

        // criteria label & field
        onView(withText(R.string.criteria_label)).check(matches(isDisplayed()));
        onView(withId(R.id.criteria)).check(matches(isDisplayed()));

        // points label & field
        onView(withText(R.string.points_label)).check(matches(isDisplayed()));
        onView(withId(R.id.points)).check(matches(isDisplayed()));

        // priority label & radio group
        onView(withText(R.string.priority_label)).check(matches(isDisplayed()));
        onView(withId(R.id.radio_group)).check(matches(isDisplayed()));
        onView(withId(R.id.radio_current)).check(matches(isDisplayed()));
        onView(withId(R.id.radio_next)).check(matches(isDisplayed()));
        onView(withId(R.id.radio_later)).check(matches(isDisplayed()));

        // status label & spinner
        onView(withText(R.string.status_label)).check(matches(isDisplayed()));
        onView(withId(R.id.spinner)).check(matches(isDisplayed()));

        // save button
        onView(withId(R.id.save_story_button)).check(matches(isDisplayed()));
        onView(withText(R.string.save_button)).check(matches(isDisplayed()));

        // cancel button
        onView(withId(R.id.cancel_story_button)).check(matches(isDisplayed()));
        onView(withText(R.string.cancel_button)).check(matches(isDisplayed()));
    }

    @Test
    public void testRadioGroup() {
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.radio_current)).perform(click());
        onView(withId(R.id.radio_current)).check(matches(isChecked()));
        onView(withId(R.id.radio_next)).check(matches(isNotChecked()));
        onView(withId(R.id.radio_later)).check(matches(isNotChecked()));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.radio_next)).perform(click());
        onView(withId(R.id.radio_next)).check(matches(isChecked()));
        onView(withId(R.id.radio_current)).check(matches(isNotChecked()));
        onView(withId(R.id.radio_later)).check(matches(isNotChecked()));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.radio_later)).perform(click());
        onView(withId(R.id.radio_later)).check(matches(isChecked()));
        onView(withId(R.id.radio_current)).check(matches(isNotChecked()));
        onView(withId(R.id.radio_next)).check(matches(isNotChecked()));
    }

    @Test
    public void testSpinner() {
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("To Do"))).perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString("To Do"))));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("In Progress"))).perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString("In Progress"))));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Done"))).perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString("Done"))));
    }

    @Test
    public void testSaveButton() {
        onView(withId(R.id.summary)).perform(typeText("story 1"));
        onView(withId(R.id.criteria)).perform(typeText("implement and test"));
        onView(withId(R.id.points)).perform(typeText("1"));

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.save_story_button)).perform(click());

        // test whether story activity finished
        assertTrue(activityRule.getActivity().isFinishing());
    }

    @Test
    public void testCancelButton() {
        onView(withId(R.id.summary)).perform(typeText("story 2"));
        onView(withId(R.id.criteria)).perform(typeText("implement and test"));
        onView(withId(R.id.points)).perform(typeText("2"));

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.cancel_story_button)).perform(click());

        // test whether story activity finished
        assertTrue(activityRule.getActivity().isFinishing());
    }

    @Override
    public Activity getActivity() {
        return (Activity)activityRule.getActivity();
    }
}
