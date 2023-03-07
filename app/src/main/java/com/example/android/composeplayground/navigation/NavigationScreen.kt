package com.example.android.composeplayground.navigation

sealed class NavigationScreen(val route : String){
    object MainScreen : NavigationScreen("main-screen")
    object Animations : NavigationScreen("animations")
    object MotionLayout : NavigationScreen("motion")
    object ButtonsAndSnack : NavigationScreen("buttons-and-snack")
    object Constraint : NavigationScreen("constraint")
    object MoreAnimations : NavigationScreen("more-animations")
    object CardComposable : NavigationScreen("card-composable")
    object ColorBoxState : NavigationScreen("box-state")
    object TextStyling : NavigationScreen("texts-style")
    object TheModifiers : NavigationScreen("modifiers")
    object TheLazyListAndScrollableColumn : NavigationScreen("LazyList")
}