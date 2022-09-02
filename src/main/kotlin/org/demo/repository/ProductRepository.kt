package org.demo.repository

import kotlinx.coroutines.FlowPreview
import org.demo.model.Products
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface ProductRepository : ReactiveCrudRepository<Products, Long> {
    @Query("SELECT * FROM products WHERE id = :id")
     fun getProductById(id: Int): Mono<Products>

    @Query("INSERT INTO products (name, price) VALUES(:name, :price)")
     fun addNewProduct(name: String, price: Float): Mono<Void>

    @FlowPreview
    @Query("select p.* from products p")
    fun getAllProducts(): Flux<Products>

}
