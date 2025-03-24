package com.nabssam.bestbook.domain.usecase.cart

//import com.nabssam.bestbook.domain.repository.LocalCartRepository
import javax.inject.Inject

class ClearCartUseCase @Inject constructor(
//    private val repository: LocalCartRepository
) {
    suspend operator fun invoke() {
//        repository.clearCart()
    }
}

