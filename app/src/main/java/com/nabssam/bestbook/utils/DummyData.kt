package com.nabssam.bestbook.utils

object DummyData {
    val categories: List<String> = listOf("CBSE Board Exams", "ISC Board Exams", "State Board Exams", "JEE Main", "NEET")
    val standards: List<Standard> = listOf(standard1, standard2, standard3, standard4)
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
