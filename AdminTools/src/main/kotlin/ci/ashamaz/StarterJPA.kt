package ci.ashamaz

import model.Question
import org.springframework.context.support.ClassPathXmlApplicationContext
import ci.ashamaz.util.QuestionCompleter

fun main() {
    val context = ClassPathXmlApplicationContext("applicationContext.xml")
    val questionCompleter: QuestionCompleter = context.getBean(QuestionCompleter::class.java)
    questionCompleter.start()
//    val questionService: IQuestionService = context.getBean(QuestionService::class.java)
//
//    questionService.getQuestionsForGame().forEach { q->
//        ci.ashamaz.askQuestion(q)
//    }

}

fun askQuestion(q: Question) {
    val questionOptions = q.getQuestionOptions()
    println("""
        Раунд ${q.level}
        ${q.question}
        A. ${questionOptions[0]}
        B. ${questionOptions[1]}
        C. ${questionOptions[2]}
        D. ${questionOptions[3]}
    """.trimIndent())
    println()
}
