package com.testtask.project.presentation.detail.store

import com.arkivanov.mvikotlin.core.store.Store
import com.testtask.project.data.ProductsItem

interface DetailStore : Store<DetailStore.Intent, DetailStore.State, Nothing> {
    sealed class Intent {
        data object BackPressed : Intent()
    }

    data class State(
        val product: ProductsItem? = null,
        val loading: Boolean = true,
        val error: String? = null
    )

}