package com.example.zaster

import org.junit.Test

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*

import com.example.zaster.models.ResponseMessage

import org.junit.Rule
import org.junit.runner.RunWith

import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4



@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule @JvmField val mActivityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun testIncorrectPassword() {
        mActivityTestRule.runOnUiThread {
            mActivityTestRule.activity.handleMessage(ResponseMessage("ServerResponse", 401))
        }
        onView(withText("Incorrect password"))
                .inRoot(ToastMatcher())
                .check(matches(isDisplayed()))
    }
    @Test
    fun testInternalIssue() {
        mActivityTestRule.runOnUiThread{
            mActivityTestRule.activity.handleMessage(ResponseMessage("ServerResponse", 500))
        }
        onView(withText("Internal issue, please try again later"))
                .inRoot(ToastMatcher())
                .check(matches(isDisplayed()))
    }
    @Test
    fun testButtonEnabled(){
        onView(withId(R.id.loginButton))
                .check(matches(isEnabled()))
    }

}