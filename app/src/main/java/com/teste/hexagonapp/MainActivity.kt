package com.teste.hexagonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.teste.hexagonapp.ui.theme.HexagonAppTheme
import com.teste.hexagonapp.view.EditUserScreen
import com.teste.hexagonapp.view.InsertUserScreen
import com.teste.hexagonapp.view.MainScreen
import com.teste.hexagonapp.view.InactiveUsersScreen
import com.teste.hexagonapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HexagonAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val userViewModel: UserViewModel = hiltViewModel()

                    AppNavHost(navController = navController, userViewModel = userViewModel)
                }
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController, userViewModel: UserViewModel) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                navController = navController,
                userViewModel = userViewModel,
                onInsertClick = { navController.navigate("insert") },
                onEditClick = { user ->
                    userViewModel.selectUser(user)
                    navController.navigate("edit")
                }
            )
        }
        composable("insert") {
            InsertUserScreen(
                onSaveClick = { user ->
                    userViewModel.insertUser(user)
                    navController.popBackStack()
                },
                onCancelClick = { navController.popBackStack() }
            )
        }
        composable("edit") {
            val user = userViewModel.selectedUser.collectAsState().value
            user?.let {
                EditUserScreen(
                    user = it,
                    onSaveClick = { updatedUser ->
                        userViewModel.updateUser(updatedUser)
                        navController.popBackStack()
                    },
                    onCancelClick = { navController.popBackStack() }
                )
            }
        }
        composable("inactiveUsers") {
            InactiveUsersScreen(
                navController = navController,
                userViewModel = userViewModel,
                onReactivateClick = { user ->
                    userViewModel.toggleUserActiveStatus(user)
                }
            )
        }
    }
}