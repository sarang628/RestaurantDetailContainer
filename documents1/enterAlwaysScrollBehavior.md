# [Scroll behavior](https://developer.android.com/develop/ui/compose/components/app-bars#scroll)
scaffold의 안에 있는 컨텐츠로 AppBar를 조정할 수 있음. 

TopAppBarScrollBehavior 생성. TopAppBar의 파라미터로 넣어준다.

TopAppBarScrollBehavior은 3가지 타입이 있음.
- enterAlwaysScrollBehavior
- exitUntilCollapsedScrollBehavior
- pinnedScrollBehavior


[RestaurantNavScreen.kt](../library/src/main/java/com/sarang/torang/compose/restaurantdetailcontainer/RestaurantNavScreen.kt) 에 구현
```
val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
```

[Nested scrolling](https://developer.android.com/develop/ui/compose/touch-input/pointer-input/nested-scroll)
