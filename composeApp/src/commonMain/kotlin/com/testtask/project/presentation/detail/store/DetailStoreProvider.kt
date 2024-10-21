package com.testtask.project.presentation.detail.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.testtask.project.data.ProductsItem
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor


class DetailStoreProvider(
    private val storeFactory: StoreFactory,
    private val productItem: ProductsItem,
    private val onFinished: () -> Unit
) {

    fun provide(): DetailStore =
        object : DetailStore, Store<DetailStore.Intent, DetailStore.State, Nothing> by storeFactory.create(
            name = "DetailStore",
            initialState = DetailStore.State(loading = true),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private inner class ExecutorImpl : CoroutineExecutor<DetailStore.Intent, Unit, DetailStore.State, Result, Nothing>() {

        override fun executeAction(action: Unit) {
            fetchProduct()
        }

        override fun executeIntent(intent: DetailStore.Intent) {
            when (intent) {
                is DetailStore.Intent.BackPressed -> handleBackPressed()
            }
        }

        private fun fetchProduct() {
            dispatch(Result.ProductFetched(productItem))
        }

        private fun handleBackPressed() {
            onFinished()
        }
    }

    private object ReducerImpl : Reducer<DetailStore.State, Result> {
        override fun DetailStore.State.reduce(msg: Result): DetailStore.State =
            when (msg) {
                is Result.ProductFetched -> copy(product = msg.product, loading = false)
            }
    }

    private sealed class Result {
        data class ProductFetched(val product: ProductsItem) : Result()
    }
}

