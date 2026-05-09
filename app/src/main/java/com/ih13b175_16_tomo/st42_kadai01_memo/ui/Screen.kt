package com.ih13b175_16_tomo.st42_kadai01_memo.ui

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Add: Screen("add")
    object Detail: Screen("detail/{memoId}") {
        fun createRoute(memoId: Int) = "detail/$memoId"
    }
    object Edit: Screen("edit/{memoId}") {
        fun createRoute(memoId: Int) = "edit/$memoId"
    }
}