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
                    run { println("call friends ${q.question}") }

                }
                HintType.CHANGE_QUESTION -> return {q, a->
                    run { println("change question ${q.question}") }
                }
                HintType.SPECTATORS_HELP -> return {q, a->
                    run { println("spectator help ${q.question}") }
                }
                HintType.RIGHT_TO_MISTAKE -> return {q, a->
                    run { println("RIGHT_TO_MISTAKE ${q.question}") }
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
