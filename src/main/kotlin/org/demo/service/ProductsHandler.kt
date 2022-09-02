package org.demo.service


import org.demo.model.Products
import org.demo.model.ProductStockView
import org.demo.repository.ProductRepositoryCoroutines
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.server.*

@Component
class ProductsHandler(
    @Autowired var webClient: WebClient,
    @Autowired var productRepository: ProductRepositoryCoroutines
) {

    @FlowPreview
    suspend fun findAll(request: ServerRequest): ServerResponse =
        ServerResponse.ok().json().bodyAndAwait(productRepository.getAllProducts())

    suspend fun findOneInStock(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id").toInt()

        val products: Deferred<Products?> = GlobalScope.async {
            productRepository.getProductById(id)
        }
        val quantity: Deferred<Int> = GlobalScope.async {
            webClient.get()
                .uri("/stock-service/product/$id/quantity")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().awaitBody<Int>()
        }

        return ServerResponse.ok().json().bodyValueAndAwait(ProductStockView(products.await()!!, quantity.await()))
    }

    suspend fun findOne(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id").toInt()
        return ServerResponse.ok().json().bodyValueAndAwait(productRepository.getProductById(id)!!)
    }
}
