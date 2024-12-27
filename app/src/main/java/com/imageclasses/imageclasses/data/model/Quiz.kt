data class Quiz(
    val question: String,
    val options: List<String>, // List of answer options
    val correctAnswerIndex: Int, // Index of the correct answer in the options list,,
    val subject:String
)

fun getQuestionList(): List<Quiz> {
    return listOf(
        Quiz(
            question = "What is the capital of India?",
            options = listOf("Delhi", "Mumbai", "Kolkata", "Chennai"),
            correctAnswerIndex = 0,
            subject = "General Knowledge"
        ),
        Quiz(
            question = "What is 5 + 7?",
            options = listOf("10", "11", "12", "13"),
            correctAnswerIndex = 2,
            subject = "Math"
        ),
        Quiz(
            question = "Which planet is known as the Red Planet?",
            options = listOf("Earth", "Mars", "Venus", "Jupiter"),
            correctAnswerIndex = 1,
            subject = "Science"
        ),
        Quiz(
            question = "Choose the correct spelling:",
            options = listOf("Receive", "Recieve", "Receve", "Receeve"),
            correctAnswerIndex = 0,
            subject = "English"
        ),
        Quiz(
            question = "What is the boiling point of water at sea level?",
            options = listOf("50°C", "100°C", "200°C", "150°C"),
            correctAnswerIndex = 1,
            subject = "Science"
        ),
        Quiz(
            question = "Who wrote the play 'Romeo and Juliet'?",
            options = listOf("William Wordsworth", "William Shakespeare", "George Orwell", "Jane Austen"),
            correctAnswerIndex = 1,
            subject = "English"
        ),
        Quiz(
            question = "Which is the largest mammal in the world?",
            options = listOf("Elephant", "Blue Whale", "Shark", "Giraffe"),
            correctAnswerIndex = 1,
            subject = "General Knowledge"
        ),
        Quiz(
            question = "What is the square root of 64?",
            options = listOf("6", "8", "10", "12"),
            correctAnswerIndex = 1,
            subject = "Math"
        ),
        Quiz(
            question = "Which gas do plants absorb during photosynthesis?",
            options = listOf("Oxygen", "Carbon Dioxide", "Nitrogen", "Hydrogen"),
            correctAnswerIndex = 1,
            subject = "Science"
        ),
        Quiz(
            question = "Find the antonym of 'Ancient':",
            options = listOf("Modern", "Old", "Historic", "Antique"),
            correctAnswerIndex = 0,
            subject = "English"
        ),
        Quiz(
            question = "Which country is known as the Land of the Rising Sun?",
            options = listOf("China", "Japan", "South Korea", "India"),
            correctAnswerIndex = 1,
            subject = "General Knowledge"
        ),
        Quiz(
            question = "What is the value of π (pi) up to two decimal places?",
            options = listOf("3.14", "3.15", "3.16", "3.13"),
            correctAnswerIndex = 0,
            subject = "Math"
        ),
        Quiz(
            question = "Which part of the plant conducts photosynthesis?",
            options = listOf("Roots", "Stem", "Leaves", "Flowers"),
            correctAnswerIndex = 2,
            subject = "Science"
        ),
        Quiz(
            question = "Identify the correct plural of 'Child':",
            options = listOf("Childs", "Children", "Childrens", "Childes"),
            correctAnswerIndex = 1,
            subject = "English"
        ),
        Quiz(
            question = "Who is the first President of India?",
            options = listOf("Mahatma Gandhi", "Jawaharlal Nehru", "Dr. Rajendra Prasad", "Sardar Patel"),
            correctAnswerIndex = 2,
            subject = "General Knowledge"
        )
    )
}
