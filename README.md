# gifApp

Проект состоит из MainActivity и двух фрагментов. MainViewModel обрабатывает поисковой текст и нажатие на гифку.

В проекте использовалась библиотека Paging 3 для пагинации.

На страницу загружаются по 25 гифок.


'''Pager(
    config = PagingConfig(
        pageSize = 25,
        enablePlaceholders = false,
        initialLoadSize = 25
    ),
    pagingSourceFactory = { GifPagingSource(it, okHttpSource) }
).flow'''


![YouCut_20230303_170617968_AdobeExpress](https://user-images.githubusercontent.com/49618961/222763017-4b6ed844-0ad1-49ce-a08c-672f8814068d.gif)
