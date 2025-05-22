package com.example.yumifi1.navigation

/**
 * Маршруты экрана
 */
sealed class Screen(val route: String) {
    /**
     * Сплеш экран
     */
    data object Splash : Screen("splash")

    /**
     * Экран авторизации
     */
    data object Auth : Screen("auth")

    /**
     * Экран регистрации
     */
    data object Reg : Screen("reg")

    /**
     * Экран с табами
     */
    data object Home : Screen("home")

    data class ProductDetails(
        val productId: Long? = null
    ) : Screen(
        route = if (productId == null) {
            "product_details"
        } else {
            "product_details?productId=$productId"
        }
    ) {
        companion object {
            fun getNavigationRoute(): String = "product_details?productId={productId}"
        }
    }

    data class RecipeDetails(
        val recipeId: Long? = null,
    ) : Screen(
        route = if (recipeId == null) {
            "recipe_details"
        } else {
            "recipe_details?recipeId=$recipeId"
        }
    ) {
        companion object {
            fun getNavigationRoute(): String = "recipe_details?recipeId={recipeId}"
        }
    }

    data object AddIngredient : Screen("add_ingredient?isRoot=false")

    data object Search : Screen("search")

    data class Comment(
        val commentId: Long? = null,
        val recipeId: Long,
    ) : Screen(
        route = if (commentId == null) {
            "comment/$recipeId"
        } else {
            "comment/$recipeId?commentId=$commentId"
        }
    ) {
        companion object {
            fun getNavigationRoute(): String = "comment/{recipeId}?commentId={commentId}"
        }
    }
}

sealed class TabScreen(route: String) : Screen(route) {

    /**
     * Экран рецептов
     */
    data object Recipes : TabScreen("recipes")

    /**
     * Экран продуктов
     */
    data object Products : TabScreen("products")

    companion object {
        private val tabRoutes = listOf(
            Recipes.route,
            Products.route,
        )

        fun String.isTabRoute(): Boolean = tabRoutes.contains(this)
    }
}