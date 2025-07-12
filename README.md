# RestaurantDetailContainer

```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```
```
ependencies {
    implementation("com.github.sarang628:RestaurantDetailContainer:567c4737a1")
}
```

## [Hilt 추가](https://github.com/sarang628/HiltTest?tab=readme-ov-file#for-torang)

## [RestaurantOverView](https://github.com/sarang628/RestaurantOverView)
## [RestaurantMenus](https://github.com/sarang628/RestaurantMenus)

```kotlin
CompositionLocalProvider(
    LocalRestaurantOverviewInRestaurantDetailContainer provides customRestaurantOverviewInRestaurantDetailContainer,
    LocalRestaurantMenuInRestaurantDetailContainer provides customRestaurantMenuInRestaurantDetailContainer,
    LocalRestaurantReviewInRestaurantDetailContainer provides customRestaurantReviewInRestaurantDetailContainer,
    LocalRestaurantGalleryInRestaurantDetailContainer provides customRestaurantGalleryInRestaurantDetailContainer,
) {
    RestaurantNavScreen(restaurantId = 234)
}


