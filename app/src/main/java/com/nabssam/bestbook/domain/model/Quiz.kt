package  com.nabssam.bestbook.domain.model

data class Quiz(
    val id:String,
    val question: String,
    val options: List<String>, // List of answer options
    val correctAnswerIndex: Int, // Index of the correct answer in the options list,,
    val subject:String
)
