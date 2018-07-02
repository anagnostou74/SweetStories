package gr.mobap.sweetstories.ui.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import gr.mobap.sweetstories.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipesListActivityTest {

    @Rule
    public ActivityTestRule<RecipesListActivity> mActivityTestRule = new ActivityTestRule<>(RecipesListActivity.class);

    @Test
    public void recipesListActivityTest() {
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.iv_main_pic), withContentDescription("Sweet image"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.widget.CardView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageView.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.tv_content), isDisplayed()));
        appCompatTextView.perform(replaceText(" : 2.0 CUP"), closeSoftKeyboard());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.tv_content), isDisplayed()));
        appCompatTextView2.perform(replaceText(" : 6.0 TBLSP"), closeSoftKeyboard());

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.tv_content), isDisplayed()));
        appCompatTextView3.perform(replaceText(" : 0.5 CUP"), closeSoftKeyboard());

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(R.id.tv_content), isDisplayed()));
        appCompatTextView4.perform(replaceText(" : 1.5 TSP"), closeSoftKeyboard());

        ViewInteraction appCompatTextView5 = onView(
                allOf(withId(R.id.tv_content), isDisplayed()));
        appCompatTextView5.perform(replaceText(" : 5.0 TBLSP"), closeSoftKeyboard());

        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(R.id.tv_content), isDisplayed()));
        appCompatTextView6.perform(replaceText(" : 1.0 K"), closeSoftKeyboard());

        ViewInteraction appCompatTextView7 = onView(
                allOf(withId(R.id.tv_content), isDisplayed()));
        appCompatTextView7.perform(replaceText(" : 500.0 G"), closeSoftKeyboard());

        ViewInteraction appCompatTextView8 = onView(
                allOf(withId(R.id.tv_content), isDisplayed()));
        appCompatTextView8.perform(replaceText(" : 1.0 CUP"), closeSoftKeyboard());

        ViewInteraction appCompatTextView9 = onView(
                allOf(withId(R.id.tv_content), isDisplayed()));
        appCompatTextView9.perform(replaceText(" : 4.0 OZ"), closeSoftKeyboard());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.steps_rv),
                        childAtPosition(
                                withId(R.id.recipe_steps_container),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

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
