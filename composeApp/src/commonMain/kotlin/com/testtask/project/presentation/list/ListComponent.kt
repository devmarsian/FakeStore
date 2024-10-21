package com.testtask.project.presentation.list

import com.arkivanov.decompose.value.Value
import com.testtask.project.data.ProductsItem
import com.testtask.project.presentation.list.store.ListStore


interface ListComponent {
    val state: Value<ListStore.State>

    fun onEvent(event: ListStore.Intent)
}