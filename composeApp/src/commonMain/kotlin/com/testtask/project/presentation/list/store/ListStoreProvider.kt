package com.testtask.project.presentation.list.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.testtask.project.api.ProductRepository
import com.testtask.project.data.ProductsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListStoreProvider(
    private val storeFactory: StoreFactory,
    private val postClicked: (ProductsItem) -> Unit,
    private val repository: ProductRepository
    ) {
    fun provide(): ListStore =
        object : ListStore, Store<ListStore.Intent, ListStore.State, Nothing> by storeFactory.create(
            name = "ListStore",
            initialState = ListStore.State(loading = true),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private inner class ExecutorImpl : CoroutineExecutor<ListStore.Intent, Unit, ListStore.State, Result, Nothing>() {
        override fun executeAction(action: Unit) {
            fetchProducts()
        }

        override fun executeIntent(intent: ListStore.Intent) {
            when (intent) {
                is ListStore.Intent.PostClicked -> postClicked(intent.product)
            }
        }

        private fun fetchProducts() {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val products = repository.fetchProducts()
                    withContext(Dispatchers.Main) {
                        dispatch(Result.ProductFetched(productsItem = products))
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        dispatch(Result.Error(e.message))
                    }
                }
            }
        }
    }
    private sealed class Result {
        data class ProductFetched(val productsItem: List<ProductsItem>) : Result()
        data class Error(val message: String?) : Result()
    }

    private object ReducerImpl : Reducer<ListStore.State, Result> {
        override fun ListStore.State.reduce(msg: Result): ListStore.State {
            return when (msg) {
                is Result.ProductFetched -> copy(products = msg.productsItem, loading = false)
                is Result.Error -> copy(error = msg.message, loading = false)
            }
        }
    }
}