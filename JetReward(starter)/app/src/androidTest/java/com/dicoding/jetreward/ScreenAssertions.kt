package com.dicoding.jetreward

import androidx.navigation.NavController
import org.junit.Assert
import kotlin.math.exp

fun NavController.assertCurrentRouteName(expectedRoute: String){
    Assert.assertEquals(expectedRoute, currentBackStackEntry?.destination?.route)
}