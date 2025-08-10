package com.example.moneyme.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.moneyme.presentation.home.HomeScreen
import com.example.moneyme.presentation.classification.SmsClassificationScreen
import com.example.moneyme.presentation.money.MoneyInOutViewModel
import com.example.moneyme.presentation.money.MoneyInScreen
import com.example.moneyme.presentation.money.MoneyOutScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Classify : Screen("classify?amount={amount}&body={body}") {
        fun withArgs(amount: Double, body: String): String {
            val encodedBody = java.net.URLEncoder.encode(body, "UTF-8")
            return "classify?amount=$amount&body=$encodedBody"
        }
    }
    object MoneyIn : Screen("money_in?amount={amount}&body={body}") {
        fun withArgs(amount: Double, body: String): String {
            val encodedBody = java.net.URLEncoder.encode(body, "UTF-8")
            return "money_in?amount=$amount&body=$encodedBody"
        }
    }

    object MoneyOut : Screen("money_out?amount={amount}&body={body}") {
        fun withArgs(amount: Double, body: String): String {
            val encodedBody = java.net.URLEncoder.encode(body, "UTF-8")
            return "money_out?amount=$amount&body=$encodedBody"
        }
    }

}



@Composable
fun AppNavHost(startDestination: String = Screen.Home.route, modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(
            route = Screen.Classify.route,
            arguments = listOf(
                navArgument("amount") {
                    type = NavType.FloatType
                    defaultValue = -1f
                    nullable = false
                },
                navArgument("body") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val amount = backStackEntry.arguments?.getFloat("amount")?.toDouble() ?: -1.0
            val body = backStackEntry.arguments?.getString("body") ?: ""
            SmsClassificationScreen(navController, amount, body)
        }

        composable(
            route = Screen.MoneyIn.route,
            arguments = listOf(
                navArgument("amount") { type = NavType.FloatType },
                navArgument("body") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val amount = backStackEntry.arguments?.getFloat("amount")?.toDouble() ?: 0.0
            val body = backStackEntry.arguments?.getString("body") ?: ""
            val viewModel: MoneyInOutViewModel = hiltViewModel()
            // Initialize state from arguments
            LaunchedEffect(Unit) {
                viewModel.initializeFromArgs(amount, body)
            }
            MoneyInScreen(viewModel, onBack = { navController.popBackStack() }, navController, modifier)
        }

        composable(
            route = Screen.MoneyOut.route,
            arguments = listOf(
                navArgument("amount") { type = NavType.FloatType },
                navArgument("body") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val amount = backStackEntry.arguments?.getFloat("amount")?.toDouble() ?: 0.0
            val body = backStackEntry.arguments?.getString("body") ?: ""
            val viewModel: MoneyInOutViewModel = hiltViewModel()
            // Initialize state from arguments
            LaunchedEffect(Unit) {
                viewModel.initializeFromArgs(amount, body)
            }
            MoneyOutScreen(viewModel, onBack = { navController.popBackStack() }, navController, modifier)
        }

    }
}