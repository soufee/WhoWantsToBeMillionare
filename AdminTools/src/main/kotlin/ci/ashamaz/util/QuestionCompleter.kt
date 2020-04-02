package ci.ashamaz.util

import model.Question
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import service.impl.QuestionService
import java.io.BufferedReader
import java.io.InputStreamReader

@Component
class QuestionCompleter(@Autowired
                        val questionService: QuestionService) {
    fun start(){
        val scanner = BufferedReader(InputStreamReader(System.`in`))
        while (true) {
            println("Введите вопрос: ")
            var str = scanner.readLine()
            if (str == "exit")
                break
            else {
                val question = Question()
                question.question = str
                println("Введите правильный ответ: ")
                str = scanner.readLine()
                if (str == "exit") break
                question.correctAnswer = str

                println("Введите неправильный ответ 1: ")
                str = scanner.readLine()
                if (str == "exit") break
                question.wrongAnswer1 = str

                println("Введите неправильный ответ 2: ")
                str = scanner.readLine()
                if (str == "exit") break
                question.wrongAnswer2 = str

                println("Введите неправильный ответ 3: ")
                str = scanner.readLine()
                if (str == "exit") break
                question.wrongAnswer3 = str

                println("Введите уровень: (1-15)")
                try {
                    val lvl = scanner.readLine().toInt()
                    if (lvl !in 1..15) break
                    question.level = lvl
                } catch (e: Exception) {
                    println("Неверное значение. Устанавливается уровень вопроса 1")
                    question.level = 1
                }


                println("""
                Сформирован вопрос: 
                ${question.question}
                Правильный ответ: 
                ${question.correctAnswer}
                Неправильные ответы: 
                ${question.wrongAnswer1}
                ${question.wrongAnswer2}
                ${question.wrongAnswer3}
                 Уровень: ${question.level}

            """.trimIndent())
                println("Сохраняем? Да|Нет")
                val toSave = scanner.readLine()
                println(toSave)
                if ("да".equals(toSave.toLowerCase().trim())) {
                    questionService.saveOrUpdate(question)
                    println("Вопрос сохранен. Еще вопрос? да = еще вопрос. нет = закончить")
                    str = scanner.readLine()
                    if ("да" != str.toLowerCase().trim()) break
                } else {
                    println("Следующий вопрос...")
                }
            }
        }

    }
}