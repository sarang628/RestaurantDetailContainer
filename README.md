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

```kotlin
CompositionLocalProvider(LocalRestaurantOverviewInRestaurantDetailContainer provides customRestaurantOverviewInRestaurantDetailContainer) {
    RestaurantNavScreen(restaurantId = 234)
}

@OptIn(ExperimentalMaterial3Api::class)
val customRestaurantOverviewInRestaurantDetailContainer : RestaurantOverviewInRestaurantDetailContainer = {
    CompositionLocalProvider(
        LocalRestaurantOverViewImageLoader provides restaurantOverViewImageLoader,
        LocalRestaurantOverviewRestaurantInfo provides restaurantOverViewRestaurantInfo,
    ) {
        RestaurantOverViewScreen(restaurantId = 234)
    }
}
```

## [RestaurantOverView](https://github.com/sarang628/RestaurantOverView)


