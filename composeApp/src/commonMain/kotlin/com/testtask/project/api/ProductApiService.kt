package com.testtask.project.api

import com.testtask.project.data.ProductsItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class ProductApiService : IProductApiService{
    companion object{
        private const val STORE_ENDPOINT = "https://fakestoreapi.com/products"
    }
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    override suspend fun fetchProducts(): List<ProductsItem> {
        return try {
            val response: HttpResponse = httpClient.get(STORE_ENDPOINT)
            if (response.status.isSuccess()) {
                val products: List<ProductsItem> = response.body()
                products
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}