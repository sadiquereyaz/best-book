package com.nabssam.bestbook.presentation.ui.book.bookDetail

import com.nabssam.bestbook.R
import com.nabssam.bestbook.data.remote.dto.ProductType
import com.nabssam.bestbook.domain.model.Book

data class StateBookDetail(
    var loading: Boolean = true,
    var errorMessage: String? = null,
    var fetchedBook: Book = Book(),
    val isListFetching: Boolean = true,
    val fetchedList: List<Book> = emptyList(),
    val listError: String? = null,
    val buttonState: ButtonType = ButtonType.ADD_TO_CART,
    val productType: ProductType = ProductType.Book
)

enum class ButtonType(val btnText: String, val iconId: Int) {
//    EBOOK("Buy Ebook Now", R.drawable.ebook),
    GO_TO_CART("Go to cart", R.drawable.go_to_cart),
    ADD_TO_CART(btnText = "Add to cart", R.drawable.add_cart)
}
