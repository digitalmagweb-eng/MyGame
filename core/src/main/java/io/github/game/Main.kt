package io.github.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.ScreenUtils

class Main : Game() {
    val gameState = GameState()
    var errorText: String? = null
    lateinit var batch: SpriteBatch
    lateinit var font: BitmapFont

    override fun create() {
        batch = SpriteBatch()
        font = BitmapFont()
        
        try {
            showGame()
        } catch (e: Exception) {
            // اگه خطایی پیش بیاد، متنش رو ذخیره می‌کنیم تا نشون بدیم
            errorText = "ERROR: " + e.message + "\n" + e.stackTraceToString().take(200)
        }
    }

    fun showGame() {
        screen = GameScreen(gameState)
    }

    fun showUpgrade() {
        screen = UpgradeScreen(gameState)
    }

    override fun render() {
        if (errorText != null) {
            // اگه خطا داشتیم، صفحه قرمز با متن خطا نشون می‌دیم
            ScreenUtils.clear(0.8f, 0.1f, 0.1f, 1f)
            batch.begin()
            font.draw(batch, errorText, 20f, Gdx.graphics.height - 20f)
            batch.end()
        } else {
            // اگه خطایی نبود، بازی رو اجرا می‌کنیم
            super.render()
        }
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
    }
}
