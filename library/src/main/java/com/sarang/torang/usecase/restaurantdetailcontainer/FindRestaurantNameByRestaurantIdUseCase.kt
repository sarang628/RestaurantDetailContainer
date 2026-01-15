package com.sarang.torang.usecase.restaurantdetailcontainer

interface FindRestaurantNameByRestaurantIdUseCase {
    suspend fun invoke(restaurantId: Int): String
}