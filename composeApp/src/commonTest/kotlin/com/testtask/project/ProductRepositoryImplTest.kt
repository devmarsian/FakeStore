package com.testtask.project

import com.testtask.project.api.IProductApiService
import com.testtask.project.api.ProductApiService
import com.testtask.project.api.ProductRepository
import com.testtask.project.api.ProductRepositoryImpl
import com.testtask.project.data.ProductsItem
import com.testtask.project.data.Rating
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

open class ProductRepositoryImplTest {

    private val productApiService = mock<IProductApiService>()
    private val productRepository: ProductRepository = ProductRepositoryImpl(productApiService)


    @Test
    fun fetchProducts_returns_list_of_products_when_successful() = runBlocking {
        val expectedProducts = listOf(
            ProductsItem(
                id = 1,
                title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
                price = 109.95,
                description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
                category = "men's clothing",
                image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
                rating = Rating(rate = 3.9, count = 120)
            ),
            ProductsItem(
                id = 2,
                title = "Mens Casual Premium Slim Fit T-Shirts ",
                price = 22.3,
                description = "Slim-fitting style, contrast raglan long sleeve, three-button henley placket, light weight & soft fabric for breathable and comfortable wearing.",
                category = "men's clothing",
                image = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
                rating = Rating(rate = 4.1, count = 259)
            )
        )

        everySuspend { productApiService.fetchProducts() } returns expectedProducts

        val actualProducts = productRepository.fetchProducts()

        assertEquals(expectedProducts, actualProducts)
        verifySuspend { productApiService.fetchProducts() }
    }

    @Test
    fun fetchProducts_throws_exception_when_api_call_fails() {
        runBlocking {
            everySuspend { productApiService.fetchProducts() } throws Exception("API call failed")

            assertFailsWith<Exception> {
                productRepository.fetchProducts()
            }
        }
    }
}
