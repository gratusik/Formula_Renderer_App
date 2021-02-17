package com.gratus.formularendererapp.view.activity

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.gratus.formularendererapp.R
import com.gratus.formularendererapp.view.adapter.RecentAdapter
import com.gratus.formularendererapp.view.adapter.SearchAdapter
import org.hamcrest.Matchers.not
import org.hamcrest.core.AllOf.allOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Matcher
import java.util.regex.Pattern.matches


@RunWith(AndroidJUnit4ClassRunner::class)
class FormulaActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(FormulaActivity::class.java)

    @Test
    fun test_isActivityInView() {
        Espresso.onView(ViewMatchers.withId(R.id.formulaRL))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.goBt))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.formulaWrapper))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.clearBt))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_EditTExtOfMoreThan1Letters() {
        onView(withId(R.id.imageRl))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

        onView(withId(R.id.recentRecV))
            .check(matches(isDisplayed()))

        onView(withId(R.id.formulaEt))
            .perform(typeText("ngengesenior"))

        onView(withId(R.id.searchRecV))
            .check(matches(isDisplayed()))

        onView(withId(R.id.recentRecV))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

        onView(ViewMatchers.withId(R.id.goBt))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(click())

//        onView(withId(R.id.imageRl))
//            .check(matches(isDisplayed()))
//
//        onView(withId(R.id.recentRecV))
//            .check(matches(isDisplayed()))
//
//        onView(withId(R.id.searchRecV)).check(matches(not(isDisplayed())))

    }

    @Test
    fun test_ClearEditTExt() {
        onView(withId(R.id.formulaEt))
            .perform(typeText("ngengesenior"))
        onView(ViewMatchers.withId(R.id.clearBt))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(click())
        onView(withId(R.id.formulaEt))
            .perform(typeText(""))
        onView(withId(R.id.imageRl))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }

    @Test
    fun test_recylerview_click_item() {
        onView(withId(R.id.formulaEt))
            .perform(typeText("ngengesenior"))
        onView(withId(R.id.searchRecV))
            .perform(actionOnItemAtPosition<SearchAdapter.SearchListViewHolder>(0, click()))
    }

    @Test
    fun test_recent_click_item() {
        onView(withId(R.id.recentRecV))
            .perform(actionOnItemAtPosition<RecentAdapter.SearchListViewHolder>(0, click()))

    }

}