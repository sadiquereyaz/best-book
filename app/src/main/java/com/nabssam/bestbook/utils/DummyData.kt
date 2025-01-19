package com.nabssam.bestbook.utils

import com.nabssam.bestbook.data.remote.dto.Review
import com.nabssam.bestbook.domain.model.OrdersListItem
import com.nabssam.bestbook.presentation.ui.order.detail.OrderDetail
import com.nabssam.bestbook.presentation.ui.order.detail.OrderStatusMain
import java.util.Date

object DummyData {
    val dummyOrdersList = listOf(
        OrdersListItem(
            id = "1",
            status = "Pending",
            date = "2024-12-18",
            productImage = "https://example.com/image1.jpg",
            productName = "Product A",
            productDescription = "Description for Product A"
        ),
        OrdersListItem(
            id = "2",
            status = "Shipped",
            date = "2024-12-15",
            productImage = "https://example.com/image2.jpg",
            productName = "Product B",
            productDescription = "Description for Product B"
        ),
        OrdersListItem(
            id = "3",
            status = "Delivered",
            date = "2024-12-12",
            productImage = "https://example.com/image3.jpg",
            productName = "Product C",
            productDescription = "Description for Product C"
        ),
        OrdersListItem(
            id = "4",
            status = "Cancelled",
            date = "2024-12-10",
            productImage = "https://example.com/image4.jpg",
            productName = "Product D",
            productDescription = "Description for Product D"
        ),
        OrdersListItem(
            id = "5",
            status = "Processing",
            date = "2024-12-08",
            productImage = "https://example.com/image5.jpg",
            productName = "Product E",
            productDescription = "Description for Product E"
        )
    )
    val categories: List<String> = listOf("CBSE Board Exams", "ISC Board Exams", "State Board Exams", "JEE Main", "NEET")
    val standards: List<Standard> = listOf(standard1, standard2, standard3, standard4)
    fun dummyOrderDetail() = OrderDetail(
        orderId = "37q98djfkad=idjfa93",
        productId = "1",
        quantity = 2,
        price = 1000,
        items = "XI-Entrance preparation guide for AMU by Nawab Saquib Ibrahim",
        image = "https://rukminim2.flixcart.com/image/832/832/xif0q/book/c/i/o/oswaal-164-chapter-wise-topic-wise-solved-papers-jee-main-23-original-imah83uuz7phrgzq.jpeg?q=70&crop=false",
        status = OrderStatusMain.PLACED,
        orderDate = Date(),
        returnBefore = Date(),
        productName = "XI-Entrance preparation guide for AMU by Nawab Saquib Ibrahim",
       seller = "Penguin publication",
       // userReview = Review(comment = "Nice book", rating = 3)
    )
}

data class Standard(
    val name: String,
    val exams: List<String>
)

val standard1 = Standard(
    name = "Class 10",
    exams = listOf("CBSE Board Exams", "ICSE Board Exams", "State Board Exams")
)

val standard2 = Standard(
    name = "Class 12",
    exams = listOf("CBSE Board Exams", "ISC Board Exams", "State Board Exams", "JEE Main", "NEET")
)

val standard3 = Standard(
    name = "Engineering",
    exams = listOf("GATE", "JEE Advanced", "UPSC ESE")
)

val standard4 = Standard(
    name = "Medical",
    exams = listOf("NEET PG", "AIIMS PG", "JIPMER PG")
)


/*
* Images:
https://rukminim2.flixcart.com/image/832/832/xif0q/book/c/i/o/oswaal-164-chapter-wise-topic-wise-solved-papers-jee-main-23-original-imah83uuz7phrgzq.jpeg?q=70&crop=false
https://rukminim2.flixcart.com/image/832/832/kg5fzww0/regionalbooks/f/g/v/how-to-win-friends-and-influence-people-original-imafwgc3sggw2s9m.jpeg?q=70&crop=false
https://rukminim2.flixcart.com/image/832/832/kg5fzww0/regionalbooks/f/g/v/how-to-win-friends-and-influence-people-original-imafwgc36znxuqmy.jpeg?q=70&crop=false
https://rukminim2.flixcart.com/image/832/832/kg5fzww0/regionalbooks/f/g/v/how-to-win-friends-and-influence-people-original-imafwgc3sggw2s9m.jpeg?q=70&crop=false
https://rukminim2.flixcart.com/image/832/832/xif0q/book/3/k/6/elementary-problems-in-organic-chemistry-for-neet-11-edition-original-imah6mzq3ksdmq4z.jpeg?q=70&crop=false
https://rukminim2.flixcart.com/image/832/832/xif0q/regionalbooks/k/d/t/elementary-problems-in-organic-chemistry-for-neet-10th-edition-original-imaguqrepzfdg8j9.jpeg?q=70&crop=false
https://rukminim2.flixcart.com/image/832/832/xif0q/book/0/o/f/neet-original-imagmnzjkrkpegvn.jpeg?q=70&crop=false
https://rukminim2.flixcart.com/image/832/832/xif0q/regionalbooks/w/a/w/neet-2025-previous-12-years-solved-papers-with-solutions-original-imah8ew2pxhgsthv.jpeg?q=70&crop=false
https://rukminim2.flixcart.com/image/832/832/xif0q/regionalbooks/i/u/h/neet-2025-previous-12-years-solved-papers-with-solutions-original-imah8ew28dn3y8ap.jpeg?q=70&crop=false
https://rukminim2.flixcart.com/image/832/832/xif0q/diary-notebook/l/l/c/omr-sheets-for-neet-practice-2023-200-mcq-omr-sheets-for-neet-original-imah7uwaqzkvymbm.jpeg?q=70&crop=false
https://rukminim2.flixcart.com/image/612/612/kkec4280/book/1/o/j/11-years-solved-papers-cbse-aipmt-neet-original-imafzr7ahsrqzss3.jpeg?q=70
https://rukminim2.flixcart.com/image/612/612/k73nlow0/book/3/2/9/neet-most-wanted-biology-original-imafpf424ctphajw.jpeg?q=70
https://rukminim2.flixcart.com/image/832/832/xif0q/book/v/0/y/disha-144-jee-main-mathematics-online-2023-2012-offline-2018-original-imahyg26ygvqedrn.jpeg?q=70&crop=false
https://rukminim2.flixcart.com/image/832/832/xif0q/book/p/u/x/disha-144-jee-main-mathematics-online-2023-2012-offline-2018-original-imahyg26tsmy2hyz.jpeg?q=70&crop=false
https://rukminim2.flixcart.com/image/832/832/xif0q/book/q/h/0/disha-144-jee-main-physics-online-2023-2012-offline-2018-2002-original-imahyg23d9rte8dh.jpeg?q=70&crop=false
Cover Images:
https://rukminim2.flixcart.com/image/612/612/xif0q/book/c/i/o/oswaal-164-chapter-wise-topic-wise-solved-papers-jee-main-23-original-imah83uuz7phrgzq.jpeg?q=70
https://rukminim2.flixcart.com/image/612/612/xif0q/book/3/k/6/elementary-problems-in-organic-chemistry-for-neet-11-edition-original-imah6mzq3ksdmq4z.jpeg?q=70
https://www.flipkart.com/elementary-problems-organic-chemistry-neet-11-edition-2024/p/itm7b1e34903458d?pid=9788197796180&lid=LSTBOK9788197796180IQWLIW&marketplace=FLIPKART&q=neet&store=bks&srno=s_1_14&otracker=search&otracker1=search&fm=productRecommendation%2Fsimilar&iid=85e35d2d-37aa-45bd-a71c-d080be192bc3.9788197796180.SEARCH&ppt=pp&ppn=pp&ssid=9lh5zpddeo0000001737028170318&qH=c70147b3ef60bac7
https://rukminim2.flixcart.com/image/312/312/kg5fzww0/regionalbooks/f/g/v/how-to-win-friends-and-influence-people-original-imafwgc36znxuqmy.jpeg?q=70
http://res.cloudinary.com/dniu1zxdq/image/upload/v1736688764/g0qttstmddfnqgmczoob.png
http://res.cloudinary.com/dniu1zxdq/image/upload/v1736689141/ucrl3fo8dryluiacy96s.jpg
https://rukminim2.flixcart.com/image/612/612/kkec4280/book/1/o/j/11-years-solved-papers-cbse-aipmt-neet-original-imafzr7ahsrqzss3.jpeg?q=70
https://rukminim2.flixcart.com/image/612/612/xif0q/book/h/i/g/pw-jee-main-and-advanced-physics-5-years-original-imah6k2wxqx6tyvj.jpeg?q=70*/