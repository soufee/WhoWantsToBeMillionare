package repository

import model.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepository: JpaRepository<Question, Int> {
    fun getQuestionByQuestion(string: String): Question?
    fun getQuestionsByLevel(level: Int): List<Question>
    fun deleteAllByLevel(level: Int)
}