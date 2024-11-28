package com.iostyle.compoil.ui.page

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iostyle.compoil.data.Records
import com.iostyle.compoil.data.addNewRecords
import com.iostyle.compoil.data.getCacheRecords
import com.iostyle.compoil.ui.dialog.CreateOilRecordsDialog
import com.iostyle.compoil.ui.dialog.CreateOilRecordsDialog.ICreateOilRecords
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PageViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<PageState> = MutableStateFlow(PageState())

    val stateFlow: StateFlow<PageState> = _stateFlow.asStateFlow()

    fun refreshPage() {
        _stateFlow.update {
            it.copy(
                isRefreshing = true
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            val data = getCacheRecords()
            delay(500)
            _stateFlow.update {
                it.copy(
                    isRefreshing = false,
                    pageItems = data
                )
            }
        }
    }

    fun floatingButtonClick(fragmentManager: FragmentManager) {
        CreateOilRecordsDialog.show(fragmentManager, object : ICreateOilRecords {
            override fun callback(currentMileage: Int, oilInjection: Float) {
                _stateFlow.update {
                    it.copy(
                        isRefreshing = true
                    )
                }
                viewModelScope.launch(Dispatchers.IO) {
                    addNewRecords(Records(System.currentTimeMillis(), currentMileage, oilInjection))
                    _stateFlow.update {
                        it.copy(
                            isRefreshing = false,
                            pageItems = getCacheRecords()
                        )
                    }
                }
            }
        })
    }
}