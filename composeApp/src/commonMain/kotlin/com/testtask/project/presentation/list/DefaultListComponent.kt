package com.testtask.project.presentation.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.testtask.project.api.ProductRepository
import com.testtask.project.data.ProductsItem
import com.testtask.project.presentation.list.store.ListStore
import com.testtask.project.presentation.list.store.ListStoreProvider
import com.testtask.project.utils.asValue
import com.testtask.project.utils.getStore

class DefaultListComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val repository: ProductRepository,
    private val postClicked: (ProductsItem) -> Unit
) : ListComponent, ComponentContext by componentContext {


    private val store = instanceKeeper.getStore {
        ListStoreProvider(
            storeFactory = storeFactory,
            postClicked = postClicked,
            repository = repository
        ).provide()
    }

    override val state: Value<ListStore.State> = store.asValue()

    override fun onEvent(event: ListStore.Intent) {
        store.accept(event)
    }
}
