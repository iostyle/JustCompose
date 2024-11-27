package com.iostyle.compoil.application

import com.iostyle.compoil.ui.page.PageViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { PageViewModel(get()) }
}