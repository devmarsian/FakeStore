package com.testtask.project.presentation.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.testtask.project.data.ProductsItem
import com.testtask.project.utils.getStore
import com.testtask.project.presentation.detail.store.DetailStore
import com.testtask.project.presentation.detail.store.DetailStoreProvider
import com.testtask.project.utils.asValue

class DefaultDetailComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    productsItem: ProductsItem,
    private val onFinished: () -> Unit
) : DetailComponent, ComponentContext by componentContext {


    private val store = instanceKeeper.getStore {
        DetailStoreProvider(
            storeFactory = storeFactory,
            productItem = productsItem,
            onFinished = onFinished
        ).provide()
    }

    override val state: Value<DetailStore.State> = store.asValue()


    override fun onEvent(event: DetailStore.Intent) {
        store.accept(event)
    }
}