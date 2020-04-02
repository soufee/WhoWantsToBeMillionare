package service

import model.Question
import java.util.*

interface IQuestionService {
    fun getQuestion(i: Int): Question?
    fun getQuestionByText(question: String): Question?
    fun getAllQuestions(): List<Question>
    fun getQuestionsByLevel(level: Int): List<Question>
    fun saveOrUpdate(question: Question): Question?
    fun updateQuestionText(question: Question): Question?
    fun remove (question: Question)
    fun isExist(question: Question): Boolean
    fun removeAllQuestions()
    fun removeAllQuestionsOfLevel(level: Int)
    fun getQuestionsForGame(): Queue<Question>
}