package com.iostyle.compoil.ui.page

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.iostyle.compoil.bean.Records
import com.iostyle.compoil.ui.dialog.CreateOilRecordsDialog
import com.iostyle.compoil.ui.dialog.CreateOilRecordsDialog.ICreateOilRecords
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class PageViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<PageState> = MutableStateFlow(PageState())

    val stateFlow: StateFlow<PageState> = _stateFlow.asStateFlow()

    suspend fun refreshPage() {
        withContext(Dispatchers.IO) {
            delay(2000)
        }
    }

    fun floatingButtonClick(fragmentManager: FragmentManager) {
        CreateOilRecordsDialog.show(fragmentManager, object : ICreateOilRecords {
            override fun callback(currentMileage: Int, oilInjection: Float) {
                stateFlow.value.pageItems.add(Records(System.currentTimeMillis(), currentMileage, oilInjection))
            }
        })
    }

}