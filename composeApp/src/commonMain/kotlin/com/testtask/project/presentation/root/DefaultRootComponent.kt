package com.testtask.project.presentation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.testtask.project.api.ProductApiService
import com.testtask.project.api.ProductRepositoryImpl
import com.testtask.project.data.ProductsItem
import com.testtask.project.presentation.detail.DefaultDetailComponent
import com.testtask.project.presentation.list.DefaultListComponent
import kotlinx.serialization.Serializable
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory


class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val nav = StackNavigation<Config>()
    private val productRepository = ProductRepositoryImpl(ProductApiService())


    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = nav,
        serializer = Config.serializer(),
        initialConfiguration = Config.List,
        handleBackButton = true,
        childFactory = ::child,
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child = when (config) {
        is Config.List -> RootComponent.Child.List(
            DefaultListComponent(
                componentContext = componentContext,
                postClicked = { post -> nav.pushNew(Config.Detail(post)) },
                repository = productRepository,
                storeFactory = DefaultStoreFactory()
            )
        )

        is Config.Detail -> RootComponent.Child.Detail(
            DefaultDetailComponent(
                componentContext = componentContext,
                storeFactory = DefaultStoreFactory(),
                productsItem = config.post,
                onFinished = { nav.pop() },
            )
        )
    }


    @Serializable
    private sealed interface Config {
        @Serializable
        data object List : Config

        @Serializable
        data class Detail(val post: ProductsItem) : Config
    }
}