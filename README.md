# gifApp

Проект состоит из MainActivity и двух фрагментов. MainViewModel обрабатывает поисковой текст и нажатие на гифку.

В проекте использовалась библиотека Paging 3 для пагинации.

На страницу загружаются по 25 гифок.


```kotlin
Pager(
    config = PagingConfig(
        pageSize = 25,
        enablePlaceholders = false,
        initialLoadSize = 25
    ),
    pagingSourceFactory = { GifPagingSource(it, okHttpSource) }
).flow
```



![YouCut_20230303_192152108-min](https://user-images.githubusercontent.com/49618961/222774864-02ba77c5-d8f9-4360-ae9a-836ae85c8572.gif)

