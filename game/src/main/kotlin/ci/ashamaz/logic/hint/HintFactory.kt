package ci.ashamaz.logic.hint

import model.Question
import java.util.*
import kotlin.streams.toList

class HintFactory {
    companion object {
        val random: Random = Random()
        fun getHint(type: HintType): (Question, Any?)-> Unit{
             when (type) {
                HintType.FIFTY_FIFTY -> return {q, a->
                    run {
                        println("Вы выбрали подсказку 50/50")
                        if (a is List<Any?>) {
                            val list = ArrayList(a.stream().map { it.toString() }.toList())
                            val correct = list.indexOf(q.correctAnswer)
                            val r = random.nextValueFromListButNotExcluded(4,correct)

                            for (i in 0..3) {
                               if (i!=correct && i!=r) list[i] = ""
                           }
                            println("""
         Раунд ${q.level}
        ${q.question}
        A. ${list[0]}
        B. ${list[1]}
        C. ${list[2]}
        D. ${list[3]}
        """.trimIndent())

                        }
                    }
                }
                HintType.CALL_FRIEND -> return {q, a->
                    run { println("вы выбрали звонок другу")
                        println("===> Друг: Насколько я помню, это ${q.correctAnswer}")
                    }

                }
                HintType.CHANGE_QUESTION -> return {q, a->
                    run { println("Данная подсказка еще не реализована. Сменить вопрос сейчас нельзя") }
                }
                HintType.SPECTATORS_HELP -> return {q, a->
                    run { println("Вы обратились к аудитории")
                        println("===> Большинство за ${q.correctAnswer}")
                    }                }
                HintType.RIGHT_TO_MISTAKE -> return {q, a->
                    run { println("Данная подсказка еще не реализована. Права на ошибку у вас нет") }
                }
            }

        }
    }
}
    fun Random.nextValueFromListButNotExcluded(size: Int, excluded: Int): Int{
        val r = Random()
        val result = r.nextInt(size)
        if (result!=excluded) return result
        else return nextValueFromListButNotExcluded(size, excluded)
    }
