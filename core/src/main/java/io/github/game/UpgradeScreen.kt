package io.github.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.ScreenUtils

class UpgradeScreen(private val gameState: GameState) : Screen {
    private val batch = SpriteBatch()
    private val font = BitmapFont()

    override fun render(delta: Float) {
        ScreenUtils.clear(0.1f, 0.1f, 0.12f, 1f)
        batch.begin()
        font.color = Color.WHITE
        font.draw(batch, "UPGRADES", 100f, 400f)
        font.draw(batch, "Money: ${gameState.money}", 100f, 360f)
        font.draw(batch, "Speed Lv: ${gameState.speedLevel}", 100f, 320f)
        font.draw(batch, "Handling Lv: ${gameState.handlingLevel}", 100f, 280f)
        font.draw(batch, "[S] Upgrade Speed (500)", 100f, 220f)
        font.draw(batch, "[H] Upgrade Handling (400)", 100f, 180f)
        font.draw(batch, "[B] Back to Game", 100f, 120f)
        batch.end()

        // Simple input
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            gameState.upgradeSpeed()
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            gameState.upgradeHandling()
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            (Gdx.app.applicationListener as? Main)?.showGame()
        }
    }

    override fun resize(width: Int, height: Int) {}
    override fun dispose() {
        batch.dispose()
        font.dispose()
    }
    override fun show() {}
    override fun hide() {}
    override fun pause() {}
    override fun resume() {}
}
