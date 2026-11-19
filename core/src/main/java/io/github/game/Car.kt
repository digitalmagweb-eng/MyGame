package com.mygame

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input

class Car {
    var x: Float = 0f
    var y: Float = 0f
    var angle: Float = 0f
    var speed: Float = 0f

    private val maxSpeed = 300f
    private val acceleration = 200f
    private val friction = 0.98f
    private val turnSpeed = 2.5f

    fun update(dt: Float) {
        // گاز
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) speed += acceleration * dt
        // ترمز
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) speed -= acceleration * dt

        speed *= friction
        speed = MathUtils.clamp(speed, -maxSpeed * 0.5f, maxSpeed)

        // فرمان
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) angle += turnSpeed * dt
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) angle -= turnSpeed * dt

        x += MathUtils.sin(angle) * speed * dt
        y += MathUtils.cos(angle) * speed * dt
    }

    fun draw(shape: ShapeRenderer) {
        shape.setColor(0.8f, 0.2f, 0.1f, 1f) // بدنه قرمز
        shape.identity()
        shape.translate(x, y, 0f)
        shape.rotate(0f, 0f, 1f, angle * MathUtils.radiansToDegrees)

        // بدنه
        shape.rect(-20f, -12f, 40f, 24f)
        // کابین (شیشه)
        shape.setColor(0.4f, 0.7f, 0.9f, 1f)
        shape.rect(-5f, -6f, 12f, 12f)
        // چرخها
        shape.setColor(0f, 0f, 0f, 1f)
        shape.rect(-22f, -16f, 8f, 8f)
        shape.rect(14f, -16f, 8f, 8f)
        shape.rect(-22f, 8f, 8f, 8f)
        shape.rect(14f, 8f, 8f, 8f)

        shape.identity()
    }
}
