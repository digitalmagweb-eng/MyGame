package io.github.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.ScreenUtils

class GameScreen(private val gameState: GameState) : Screen {
    private val camera = OrthographicCamera()
    private val shape = ShapeRenderer()
    private val car = Car()
    private val city = City()

    private var missionTimer = 0f
    private var missionActive = true
    private var slowMotion = false

    // Touch control states
    private var touchSteer = 0f
    private var touchThrottle = 0f
    private var touchSlowMotion = false

    // Button rectangles
    private val btnSize = 90f
    private val gasBtn = Rectangle(700f, 40f, btnSize, btnSize)
    private val brakeBtn = Rectangle(600f, 40f, btnSize, btnSize)
    private val leftBtn = Rectangle(40f, 40f, btnSize, btnSize)
    private val rightBtn = Rectangle(140f, 40f, btnSize, btnSize)
    private val slowBtn = Rectangle(700f, 350f, btnSize, btnSize)

    init {
        camera.setToOrtho(false, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        car.position.set(100f, 100f)
    }

    override fun render(delta: Float) {
        val dt = if (slowMotion) delta * 0.3f else delta

        // Reset touch states
        touchSteer = 0f
        touchThrottle = 0f
        touchSlowMotion = false

        // Check touch input
        if (Gdx.input.isTouched) {
            val touchX = Gdx.input.x.toFloat()
            val touchY = (Gdx.graphics.height - Gdx.input.y).toFloat()

            if (gasBtn.contains(touchX, touchY)) touchThrottle = 1f
            if (brakeBtn.contains(touchX, touchY)) touchThrottle = -1f
            if (leftBtn.contains(touchX, touchY)) touchSteer = -1f
            if (rightBtn.contains(touchX, touchY)) touchSteer = 1f
            if (slowBtn.contains(touchX, touchY)) touchSlowMotion = true
        }

        // Use touch values
        val steer = touchSteer
        val throttle = touchThrottle
        slowMotion = touchSlowMotion

        // Update
        car.update(dt, steer, throttle, gameState.speedLevel)
        camera.position.set(car.position.x, car.position.y, 0f)
        camera.update()

        // Mission logic
        if (missionActive) {
            missionTimer += dt
            if (missionTimer > 10f) {
                gameState.money += 200
                gameState.showDialog("Mission complete! +200")
                missionTimer = 0f
            }
        }

        // Clear
        ScreenUtils.clear(0.05f, 0.05f, 0.08f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Draw
        shape.projectionMatrix = camera.combined
        city.draw(shape)
        drawCar()

        // Draw buttons on top
        drawButtons()
    }

    private fun drawCar() {
        shape.begin(ShapeRenderer.ShapeType.Filled)
        shape.color.set(1f, 0.4f, 0f, 1f)
        shape.rect(car.position.x - 10f, car.position.y - 5f, 20f, 10f)
        shape.end()
    }

    private fun drawButtons() {
        shape.begin(ShapeRenderer.ShapeType.Filled)

        // Camera to screen coords (so buttons stay fixed on screen)
        shape.projectionMatrix = Matrix4().setToOrtho2D(
            0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()
        )

        // Gas button - green
        shape.color.set(0.2f, 0.8f, 0.2f, 0.6f)
        shape.rect(gasBtn.x, gasBtn.y, gasBtn.width, gasBtn.height)

        // Brake button - red
        shape.color.set(0.8f, 0.2f, 0.2f, 0.6f)
        shape.rect(brakeBtn.x, brakeBtn.y, brakeBtn.width, brakeBtn.height)

        // Left button - blue
        shape.color.set(0.2f, 0.4f, 0.8f, 0.6f)
        shape.rect(leftBtn.x, leftBtn.y, leftBtn.width, leftBtn.height)

        // Right button - blue
        shape.color.set(0.2f, 0.4f, 0.8f, 0.6f)
        shape.rect(rightBtn.x, rightBtn.y, rightBtn.width, rightBtn.height)

        // Slow motion button - yellow
        shape.color.set(0.9f, 0.8f, 0.1f, 0.6f)
        shape.rect(slowBtn.x, slowBtn.y, slowBtn.width, slowBtn.height)

        shape.end()

        // Reset projection for next frame
        shape.projectionMatrix = camera.combined
    }

    override fun resize(width: Int, height: Int) {
        camera.viewportWidth = width.toFloat()
        camera.viewportHeight = height.toFloat()
        camera.update()
    }

    override fun dispose() {
        shape.dispose()
    }

    override fun show() {}
    override fun hide() {}
    override fun pause() {}
    override fun resume() {}
}
