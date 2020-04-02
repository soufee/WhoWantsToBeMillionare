package model

import javax.persistence.*

@Entity
@Table(name="question")
 data class Question (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int = 0,
        @Column(name="level")
        var level: Int = 1,
        @Column(nullable = false, name = "question", length = 256)
        var question: String = "",
        @Column(nullable = false, name = "correct_answer", length = 128)
        var correctAnswer: String = "",
        @Column(nullable = false, name = "wrong_answer1", length = 128)
        var wrongAnswer1: String = "",
        @Column(nullable = false, name = "wrong_answer2", length = 128)
        var wrongAnswer2: String = "",
        @Column(nullable = false, name = "wrong_answer3", length = 128)
        var wrongAnswer3: String = ""
) {
        fun getQuestionOptions(): List<String>{
                return listOf(correctAnswer, wrongAnswer1, wrongAnswer2,wrongAnswer3).shuffled()
        }
}


