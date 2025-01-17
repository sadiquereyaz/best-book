package com.nabssam.bestbook.presentation.ui.book.bookDetail

import com.nabssam.bestbook.R
import com.nabssam.bestbook.domain.model.Book

data class StateBookDetail(
    var loading: Boolean = true,
    var errorMessage: String? = null,
    var fetchedBook: Book = Book(),
    val isListFetching: Boolean = true,
    val fetchedList: List<Book> = emptyList(),
    val listError: String? = null,
    val isEbookSelected: Boolean = true,
    val buttonState: ButtonType = ButtonType.ADD_TO_CART
)

sealed class ButtonState(){
    object EBOOK : ButtonState()
    object GO_TO_CART : ButtonState()
    object ADD_TO_CART : ButtonState()
}


enum class ButtonType(val btnText: String, val iconId: Int) {
    EBOOK("Buy Ebook Now", R.drawable.ebook),
    GO_TO_CART("Go to cart", R.drawable.go_to_cart),
    ADD_TO_CART(btnText = "Add to cart", R.drawable.add_cart)
}

/*

sealed class EventBookDetail{
    data object Retry : EventBookDetail()
    data object AddToCart : EventBookDetail()
    data class OnEbookSelect(val isEbookSelected: Boolean = true) : EventBookDetail()
}

*/
