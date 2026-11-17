package io.github.game

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

// Simple math-based car physics (no Box2D)
class Car {
    val position = Vector2(0f, 0f)
    var angle: Float = 0f // degrees
    var speed: Float = 0f

    private val maxSpeed = 400f
    private val acceleration = 200f
    private val friction = 150f

    fun update(delta: Float, steer: Float, throttle: Float, speedLevel: Int) {
        val effectiveMax = maxSpeed + speedLevel * 30f

        // Throttle
        speed += throttle * acceleration * delta
        speed = MathUtils.clamp(speed, -effectiveMax * 0.5f, effectiveMax)

        // Friction when no throttle
        if (throttle == 0f) {
            if (speed > 0f) speed = maxOf(0f, speed - friction * delta)
            else if (speed < 0f) speed = minOf(0f, speed + friction * delta)
        }

        // Steering only when moving
        if (Math.abs(speed) > 10f) {
            angle += steer * 90f * delta * (speed / effectiveMax)
        }

        // Move position
        position.x += MathUtils.cosDeg(angle) * speed * delta
        position.y += MathUtils.sinDeg(angle) * speed * delta
    }
}
