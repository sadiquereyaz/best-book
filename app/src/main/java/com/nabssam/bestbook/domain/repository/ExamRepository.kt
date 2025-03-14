package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.data.remote.dto.Chapter
import com.nabssam.bestbook.data.remote.dto.Quize
import com.nabssam.bestbook.data.remote.dto.Subject
import com.nabssam.bestbook.domain.model.Exam
import com.nabssam.bestbook.utils.Resource
import com.nabssam.bestbook.utils.Standard
import kotlinx.coroutines.flow.Flow

interface ExamRepository {
    fun fetchAllExams(): Flow<Resource<List<Exam>>>
    fun fetchAllTarget(): Flow<Resource<List<String>>>
    fun fetchAllStandard1(): Flow<Resource<List<Standard>>>
    suspend fun fetchAllStandard(): Result<List<Standard>>
    fun fetchAllSubjects(examId: String): Flow<Resource<List<Subject>>>
    fun fetchAllChapters(subjectId: String): Flow<Resource<List<Chapter>>>
    fun fetchAllQuizzes(chapterId: String): Flow<Resource<List<Quize>>>

}