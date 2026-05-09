package com.ih13b175_16_tomo.st42_kadai01_memo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ih13b175_16_tomo.st42_kadai01_memo.data.database.AppDatabase
import com.ih13b175_16_tomo.st42_kadai01_memo.data.model.MemoDao
import com.ih13b175_16_tomo.st42_kadai01_memo.data.repository.MemoRepository
import com.ih13b175_16_tomo.st42_kadai01_memo.ui.MemoViewModel
import com.ih13b175_16_tomo.st42_kadai01_memo.ui.Screen
import com.ih13b175_16_tomo.st42_kadai01_memo.ui.screens.AddScreen
import com.ih13b175_16_tomo.st42_kadai01_memo.ui.screens.DetailScreen
import com.ih13b175_16_tomo.st42_kadai01_memo.ui.screens.EditScreen
import com.ih13b175_16_tomo.st42_kadai01_memo.ui.screens.HomeScreen
import com.ih13b175_16_tomo.st42_kadai01_memo.ui.theme.ST42_kadai01_memoTheme

class MainActivity : ComponentActivity() {
    private lateinit var database: AppDatabase;
    private lateinit var memoDao: MemoDao;
    private lateinit var memoRepository: MemoRepository;
    private lateinit var memoViewModel: MemoViewModel;

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        database = AppDatabase.getInstance(this)
        memoDao = database.memoDao()
        memoRepository = MemoRepository(memoDao)
        memoViewModel = MemoViewModel(memoRepository)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ST42_kadai01_memoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 32.dp,
                            )
                        ) {
                            MemoAppNavigation(memoViewModel)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MemoAppNavigation(viewModel: MemoViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // ホーム画面
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToAdd = {
                    navController.navigate(Screen.Add.route)
                },
                onNavigateToDetail = { memoId ->
                    navController.navigate(Screen.Detail.createRoute(memoId))
                },
            )
        }

        // 追加画面
        composable(Screen.Add.route) {
            AddScreen(viewModel) {
                navController.popBackStack()
            }
        }

        // 詳細画面
        composable(
            Screen.Detail.route,
            arguments = listOf(
                navArgument("memoId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val memoId = backStackEntry.arguments?.getInt("memoId")
            DetailScreen(
                memoId = memoId,
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToEdit = { memoId ->
                    navController.navigate(Screen.Edit.createRoute(memoId))
                }
            )
        }

        // 編集画面
        composable(
            Screen.Edit.route,
            arguments = listOf(
                navArgument("memoId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val memoId = backStackEntry.arguments?.getInt("memoId")
            EditScreen(
                memoId = memoId,
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

    }
}