package com.eventpro.ai.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eventpro.ai.ui.screens.auth.LoginScreen
import com.eventpro.ai.ui.screens.auth.RegisterScreen
import com.eventpro.ai.ui.screens.events.EventDetailScreen
import com.eventpro.ai.ui.screens.events.EventListScreen
import com.eventpro.ai.ui.screens.home.HomeScreen
import com.eventpro.ai.ui.screens.profile.ProfileScreen
import com.eventpro.ai.ui.screens.splash.SplashScreen
import com.eventpro.ai.ui.viewmodel.AuthViewModel

@Composable
fun EventProNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()
    val authState by authViewModel.authState.collectAsStateWithLifecycle()
    
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // Splash Screen
        composable(Screen.Splash.route) {
            SplashScreen(
                navController = navController,
                isLoggedIn = authState.isLoggedIn
            )
        }
        
        // Authentication
        composable(Screen.Login.route) {
            LoginScreen(
                navController = navController,
                viewModel = authViewModel
            )
        }
        
        composable(Screen.Register.route) {
            RegisterScreen(
                navController = navController,
                viewModel = authViewModel
            )
        }
        
        // Main App
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        
        composable(Screen.EventList.route) {
            EventListScreen(navController = navController)
        }
        
        composable(
            route = Screen.EventDetail.route,
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
            EventDetailScreen(
                navController = navController,
                eventId = eventId
            )
        }
        
        composable(Screen.Profile.route) {
            ProfileScreen(
                navController = navController,
                viewModel = authViewModel
            )
        }
        
        // Add more routes as needed
    }
}
