package com.dicoding.jetreward

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.dicoding.jetreward.model.FakeRewardDataSource
import com.dicoding.jetreward.ui.navigation.Screen
import com.dicoding.jetreward.ui.theme.JetRewardTheme
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class JetRewardAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()


    private lateinit var navController: TestNavHostController

    @Before
    fun setup(){
        composeTestRule.setContent {
            JetRewardTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                JetRewardApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_VerifityStartDestination(){
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailScreen(){
        composeTestRule.onNodeWithTag("RewardList").performScrollToIndex(10)
        composeTestRule.onNodeWithText(FakeRewardDataSource.dummyRewards[10].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailReward.route)
        composeTestRule.onNodeWithText(FakeRewardDataSource.dummyRewards[10].title).assertIsDisplayed()
    }

    @Test
    fun navHost_bottomNavigation_working(){
        composeTestRule.onNodeWithTag("Profile").performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)

        composeTestRule.onNodeWithTag("Home").performClick()
        navController.assertCurrentRouteName(Screen.Home.route)

        composeTestRule.onNodeWithTag("Keranjang").performClick()
        navController.assertCurrentRouteName(Screen.Cart.route)
    }

    @Test
    fun navHost_clickItem_navigatesBack(){
        composeTestRule.onNodeWithText(FakeRewardDataSource.dummyRewards[0].title).performClick()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back)).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_checkOut_OnTheRightBackStack(){
        composeTestRule.onNodeWithText(FakeRewardDataSource.dummyRewards[4].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailReward.route)
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").performClick()
        navController.assertCurrentRouteName(Screen.Cart.route)
        // does not work on api 32 higher
//        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
//        navController.assertCurrentRouteName(Screen.Home.route)
    }

}