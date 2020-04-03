package ci.ashamaz

import ci.ashamaz.logic.Game
import org.springframework.context.support.ClassPathXmlApplicationContext


fun main() {
    val context = ClassPathXmlApplicationContext("gameContext.xml")
    val game = context.getBean(Game::class.java)
    game.start()

}
