package com.testtask.project.presentation.detail

import com.arkivanov.decompose.value.Value
import com.testtask.project.presentation.detail.store.DetailStore

interface DetailComponent {
    val state: Value<DetailStore.State>

    fun onEvent(event: DetailStore.Intent)
}