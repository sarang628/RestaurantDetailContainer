package com.sarang.torang

class RootNavController {
    fun profile(i: Int) {}
    fun review(i: Int) {}
    fun popBackStack() {}
    fun map(i: Int) {}
    fun modReview(): (Int) -> Unit { return  {}}
    fun emailLogin() {}
    fun like(reviewId: Int) {}
    fun imagePager(reviewId: Int, lastPage: Int) {}
    fun restaurant(restaurantId: Int) {}
}