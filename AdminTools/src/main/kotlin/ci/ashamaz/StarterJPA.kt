package ci.ashamaz

import org.springframework.context.support.ClassPathXmlApplicationContext
import ci.ashamaz.util.QuestionCompleter

fun main() {
    val context = ClassPathXmlApplicationContext("adminContext.xml")
    val questionCompleter: QuestionCompleter = context.getBean(QuestionCompleter::class.java)
    questionCompleter.start()
}

