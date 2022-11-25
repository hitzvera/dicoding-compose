package com.dicoding.jetreward.ui.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.dicoding.jetreward.model.OrderReward
import com.dicoding.jetreward.model.Reward
import com.dicoding.jetreward.R
import com.dicoding.jetreward.onNodeWithStringId
import com.dicoding.jetreward.ui.theme.JetRewardTheme
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeOrderReward = OrderReward(
        reward = Reward(4, R.drawable.reward_4, "Jaket Hoodie Dicoding", 7500),
        count = 0
    )

    @Before
    fun setup(){
        composeTestRule.setContent {
            JetRewardTheme {
                DetailContent(
                    fakeOrderReward.reward.image,
                    fakeOrderReward.reward.title,
                    fakeOrderReward.reward.requiredPoint,
                    fakeOrderReward.count,
                    onBackClick = {},
                    onAddToCart = {},
                )
            }

        }
        composeTestRule.onRoot().printToLog("currentLabelExists")

        // scenario testing
        // memastikan data pada halaman detail tampil
        // memastikan ketika jumlah produk ditambah, status tombol menjadi aktif
        // memastikan ketika + ditekan 2 kali, jumlah produk menjadi 2
        // memastikan ketika tombol kurang dari 0, jumlah produk tetap 0
    }
    @Test
    fun detailContent_isDipslayed(){
        composeTestRule.onNodeWithText("Jaket Hoodie Dicoding").assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.required_point,
                fakeOrderReward.reward.requiredPoint
            )
        ).assertIsDisplayed()
    }

    @Test
    fun increaseProduct_button_enabled(){
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsNotEnabled()
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsEnabled()
    }

    @Test
    fun increaseProduct_2times_displayed2(){
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick().performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("2"))
    }

    @Test
    fun decreaseProduct_belowZero_ZeroIsStillDisplayed(){
        composeTestRule.onNodeWithStringId(R.string.minus_symbol).performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("0"))
    }
}
