# RestaurantDetailContainer
음식점 정보, 메뉴, 리뷰, 갤러리 등의 정보를 모아놓은 화면

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

# Tech Stack
- JetpackCompose
  - enterAlwaysScrollBehavior
- Android App Architecture
- [CompositionLocalProvider](./documents/CoreAreas/UI/UIArchitecture/CompositionLocal.md)
  - 이미지 로더, 피드, 확장 텍스트 기능, 스크롤 리프레시 레이아웃 등 제공 용도

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
