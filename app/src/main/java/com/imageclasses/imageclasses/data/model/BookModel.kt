package com.imageclasses.imageclasses.data.model

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.imageclasses.imageclasses.R

data class BookModel(
    val bookId: String = "DUMMY_ID",
    val title:String = "AMU Booster 11th Entrance Science Stream Chapterwise with Solution",
    val author:String = "Nawab Saquib Ibrahim, Dr. Maaz Hashmi",
    val language:String = "English",
    val publishDate:String = "10/02/2023",
    val publisher:String = "Sunflower publishing PVT LTD",
    val price:Int= 520,
    val discount:Int= 34,
    val image:Int = R.drawable.book1,
    val noOfPages:Int = 127,
    val rating:Float = 4.5f,
    val description: String = "Susan Sontag's essay Against Interpretation is excellent, providing us with a well-needed reminded of the importance of the spiritual effect of the aesthetic: \"in place of a hermeneutics we need an erotics of art\". (I would also recommend the eponymous collection of essays)."
)