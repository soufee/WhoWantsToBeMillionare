package service.impl

import model.Question
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import repository.QuestionRepository
import service.IQuestionService
import java.lang.IllegalArgumentException
import java.util.*

@Service
class QuestionService(@Autowired
                      val repository: QuestionRepository?) : IQuestionService {

    override fun getQuestion(i: Int): Question? {
        return repository?.getOne(i)
    }

    override fun getQuestionByText(question: String): Question? {
        return repository?.getQuestionByQuestion(question)
    }

    override fun getAllQuestions(): List<Question> {
        return repository?.findAll().orEmpty()
    }

    override fun getQuestionsByLevel(level: Int): List<Question> {
        return repository?.getQuestionsByLevel(level).orEmpty()
    }

    override fun saveOrUpdate(question: Question): Question? {
        val q = getQuestionByText(question.question) ?: return repository?.save(question)
        q.level = question.level
        q.wrongAnswer1 = question.wrongAnswer1
        q.wrongAnswer2 = question.wrongAnswer2
        q.wrongAnswer3 = question.wrongAnswer3
        q.correctAnswer = question.correctAnswer
        return repository?.save(q)
    }

    override fun updateQuestionText(question: Question): Question? {
        val q = getQuestion(question.id) ?: return repository?.save(question)
        q.question = question.question
        return repository?.save(q)
    }

    override fun remove(question: Question) {
        val q = getQuestion(question.id)
                ?: throw IllegalArgumentException("There is not question with id ${question.id}")
        repository?.delete(question)
    }

    override fun isExist(question: Question): Boolean {
        getQuestionByText(question.question) ?: return false
        return true
    }

    override fun removeAllQuestions() {
        repository?.deleteAll()
    }

    override fun removeAllQuestionsOfLevel(level: Int) {
        repository?.deleteAllByLevel(level)
    }

    override fun getQuestionsForGame(): Queue<Question> {
        val result = LinkedList<Question>()
        for (i in 1..15) {
            repository?.getQuestionsByLevel(i)?.shuffled()?.get(0)?.let { result.add(it) }
        }
        return result
    }
}