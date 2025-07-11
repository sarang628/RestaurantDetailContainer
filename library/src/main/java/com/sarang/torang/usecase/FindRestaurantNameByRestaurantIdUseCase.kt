package com.sarang.torang.usecase

interface FindRestaurantNameByRestaurantIdUseCase {
    suspend fun invoke(restaurantId: Int): String
}