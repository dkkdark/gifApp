package com.kseniabl.gifapp.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.kseniabl.gifapp.network.OkHttpProvider
import com.kseniabl.gifapp.network.OkHttpSource
import com.kseniabl.gifapp.network.OkHttpSourceInterface
import com.kseniabl.gifapp.network.models.GifModel
import com.kseniabl.gifapp.network.paging.GifPagingSource
import com.kseniabl.gifapp.ui.adapter.GifAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class MainViewModel(
    private val okHttpSource: OkHttpSourceInterface = OkHttpSource(OkHttpProvider.okkHttpData)
): ViewModel(), GifAdapter.Listener {

    val gifList: Flow<PagingData<GifModel>>

    private val searchBy = MutableStateFlow("")

    private val _pageNumber = MutableStateFlow(1)
    val pageNumber = _pageNumber.asStateFlow()

    private val _actionTrigger = MutableSharedFlow<UIActions>()
    val actionTrigger = _actionTrigger.asSharedFlow()

    init {
        gifList = searchBy
            // Data shouldn't fetch often
            .debounce(500)
            .flatMapLatest {
                Pager(
                    config = PagingConfig(
                        pageSize = 25,
                        enablePlaceholders = false,
                        initialLoadSize = 25
                    ),
                    pagingSourceFactory = { GifPagingSource(it, okHttpSource) }
                ).flow
            }.cachedIn(viewModelScope)
    }

    fun onSearchQueryChange(value: String) {
        searchBy.value = value
    }

    override fun onItemClick(item: GifModel) {
        viewModelScope.launch {
            _actionTrigger.emit(UIActions.MoveToDetails(item))
        }
    }

    sealed class UIActions {
        data class MoveToDetails(val item: GifModel): UIActions()
    }

}