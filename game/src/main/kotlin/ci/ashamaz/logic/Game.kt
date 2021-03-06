package ci.ashamaz.logic

import ci.ashamaz.logic.hint.HintFactory
import ci.ashamaz.logic.hint.HintType
import model.Question
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import service.IQuestionService
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import java.util.stream.Collectors
import kotlin.system.exitProcess

@Component
class Game(@Autowired
           val questionServise: IQuestionService) {
    var bank = 0
    var fixedBank = 0
    private val reader = BufferedReader(InputStreamReader(System.`in`))
    private val validInputs = listOf(
            "A",
            "a",
            "B",
            "b",
            "C",
            "c",
            "D",
            "d"
    )
    private val mapping = mapOf(
            0 to "A",
            1 to "B",
            2 to "C",
            3 to "D"
    )
    val hints = initHints()

    fun start() {
        val questions = questionServise.getQuestionsForGame()
        println("Итак, начинаем игру!")
        for (i in 0..14) {
            val question = questions.poll()
            println("Вопрос номер ${question.level} на сумму ${getMoneyOfRound(question.level)}")
            val options = question.getQuestionOptions()
            println("""
                ${question.question}
                A. ${options[0]}
                B. ${options[1]}
                C. ${options[2]}
                D. ${options[3]}
            """.trimIndent())
            println()
            val result = getAnswer(question, options)
            if (!result) {
                wrongAnswer(question)
                break
            } else {
                correctAnswer(question)
            }
        }
        println("Игра окончена!")
    }

    private fun correctAnswer(question: Question) {
        increaseBank(question.level)
        println("Это правильный ответ! Вы выиграли $bank рублей!")
    }

    private fun wrongAnswer(question: Question) {
        println("Увы, это неправильный ответ. Правильный ответ - ${question.correctAnswer}")
        println("Ваш выигрыш составил ${fixedBank} рублей")
    }

    private fun getAnswer(question: Question, options: List<String>): Boolean {
        println("Введите букву выбранного ответа или введите exit для того чтоб забрать деньги и уйти. \n" +
                "Из подсказок вам доступны ${getListOfAvailableHints()}")
        val answer = reader.readLine()
        when {
            answer.equals("exit", true) -> {
                println("Вы завершили игру. Ваш выигрыш составил  ${bank}")
                exitProcess(0)
            }
            answer.equals("fifty-fifty", true) -> {
                if (hints.containsKey(HintType.FIFTY_FIFTY)) {
                    HintFactory.getHint(HintType.FIFTY_FIFTY).invoke(question, options)
                    hints.remove(HintType.FIFTY_FIFTY)
                } else {
                    println("Эта подсказка вам не доступна!")
                }
                return getAnswer(question, options)
            }
            answer.equals("call-friend", true) -> {
                if (hints.containsKey(HintType.CALL_FRIEND)) {
                    HintFactory.getHint(HintType.CALL_FRIEND).invoke(question, options)
                    hints.remove(HintType.CALL_FRIEND)
                } else {
                    println("Эта подсказка вам не доступна!")
                }
                return getAnswer(question, options)
            }
            answer.equals("ask-people", true) -> {
                if (hints.containsKey(HintType.SPECTATORS_HELP)) {
                    HintFactory.getHint(HintType.SPECTATORS_HELP).invoke(question, options)
                    hints.remove(HintType.SPECTATORS_HELP)
                } else {
                    println("Эта подсказка вам не доступна!")
                }
                return getAnswer(question, options)
            }
            else -> {
                return if (validateAnswer(answer)) {
                    checkAnswer(answer, question, options)
                } else {
                    getAnswer(question, options)
                }
            }
        }
    }

    private fun initHints(): EnumMap<HintType, (Question, Any?) -> Unit> {
        val map = EnumMap<HintType, (Question, Any?) -> Unit>(HintType::class.java)
        map.put(HintType.FIFTY_FIFTY, HintFactory.getHint(HintType.FIFTY_FIFTY))
        map.put(HintType.SPECTATORS_HELP, HintFactory.getHint(HintType.SPECTATORS_HELP))
        map.put(HintType.CALL_FRIEND, HintFactory.getHint(HintType.CALL_FRIEND))
        return map
    }

    private fun increaseBank(questionLevel: Int) {
        val wonSum = getMoneyOfRound(questionLevel)
        bank = wonSum
        if (questionLevel == 5 || questionLevel == 10) {
            fixedBank = wonSum
            println("Это несгараемая сумма! ")
        }
    }

    private fun getMoneyOfRound(level: Int): Int {
        return when (level) {
            1 -> 500
            2 -> 1000
            3 -> 2000
            4 -> 3000
            5 -> 5000
            6 -> 10000
            7 -> 15000
            8 -> 25000
            9 -> 50000
            10 -> 100000
            11 -> 200000
            12 -> 400000
            13 -> 800000
            14 -> 1500000
            15 -> 3000000
            else -> 0
        }
    }

    private fun validateAnswer(answer: String): Boolean {
        return validInputs.contains(answer)
    }

    private fun checkAnswer(answer: String, question: Question, options: List<String>): Boolean {
        val index = options.indexOf(question.correctAnswer)
        return answer.equals(mapping[index], true)
    }

    private fun getListOfAvailableHints(): String {
        return hints.keys.stream().map { it.hintName }.collect(Collectors.toList()).joinToString(",")
    }
}