package io.github.game

import com.badlogic.gdx.Game

class Main : Game() {
    val gameState = GameState()

    override fun create() {
        showGame()
    }

    fun showGame() {
        screen = GameScreen(gameState)
    }

    fun showUpgrade() {
        screen = UpgradeScreen(gameState)
    }
}
