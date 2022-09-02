package org.demo.controller

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import org.demo.model.ProductStockView
import org.demo.model.Products
import org.demo.repository.ProductRepositoryCoroutines
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

class ProductControllerCoroutines {
    @Autowired
    lateinit var webClient: WebClient

    @Autowired
    lateinit var productRepository: ProductRepositoryCoroutines

    @GetMapping("/{id}")
    suspend fun findOne(@PathVariable id: Int): Products? {
        return productRepository.getProductById(id)
    }

    @GetMapping("/{id}/stock")
    suspend fun findOneInStock(@PathVariable id: Int): ProductStockView = coroutineScope {
        val product: Deferred<Products?> = async(start = CoroutineStart.LAZY) {
            productRepository.getProductById(id)
        }
        val quantity: Deferred<Int> = async(start = CoroutineStart.LAZY) {
            webClient.get()
                .uri("/stock-service/product/$id/quantity")
                .accept(APPLICATION_JSON)
                .retrieve().awaitBody<Int>()
        }
        ProductStockView(product.await()!!, quantity.await())
    }

    @FlowPreview
    @GetMapping("/")
    fun findAll(): Flow<Products> {
        return productRepository.getAllProducts()
    }
}