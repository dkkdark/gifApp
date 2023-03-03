package com.kseniabl.gifapp.network.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kseniabl.gifapp.network.OkHttpSourceInterface
import com.kseniabl.gifapp.network.models.GifModel

class GifPagingSource(
    private val query: String,
    private val source: OkHttpSourceInterface
): PagingSource<Int, GifModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GifModel> {
        val pageIndex = params.key ?: 0

        return try {
            val offset = params.loadSize * pageIndex
            val gif = source.gifCall(query, params.loadSize, offset).data

            return LoadResult.Page(
                data = gif,
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if (gif.size == params.loadSize) pageIndex + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GifModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}