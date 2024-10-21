package com.testtask.project.presentation.list.store

import com.arkivanov.mvikotlin.core.store.Store
import com.testtask.project.data.ProductsItem
import com.testtask.project.presentation.detail.store.DetailStore

interface ListStore : Store<ListStore.Intent, ListStore.State, Nothing> {

    data class State(
        val products: List<ProductsItem> = emptyList(),
        val loading: Boolean = false,
        val error: String? = null
    )

    sealed class Intent {
        data class PostClicked(val product: ProductsItem) : Intent()
    }

}