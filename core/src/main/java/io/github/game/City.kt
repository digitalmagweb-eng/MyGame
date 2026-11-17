package io.github.game

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

// Simple city with roads and buildings (drawn with shapes)
class City {
    data class Building(val x: Float, val y: Float, val w: Float, val h: Float)

    val buildings = mutableListOf<Building>()

    init {
        // Simple grid of buildings
        for (i in 0..5) {
            for (j in 0..5) {
                if (i % 2 == 0 && j % 2 == 0) continue // leave road gaps
                buildings.add(Building(i * 200f, j * 200f, 120f, 120f))
            }
        }
    }

    fun draw(shape: ShapeRenderer) {
        // Draw roads (background lines)
        shape.begin(ShapeRenderer.ShapeType.Filled)
        shape.color.set(0.15f, 0.15f, 0.15f, 1f)
        shape.rect(-1000f, -1000f, 3000f, 3000f)

        // Draw buildings
        shape.color.set(0.3f, 0.3f, 0.35f, 1f)
        for (b in buildings) {
            shape.rect(b.x, b.y, b.w, b.h)
        }
        shape.end()
    }

    fun isOnRoad(pos: Vector2): Boolean {
        // Simple check: roads are gaps between buildings
        val cellX = (pos.x / 200f).toInt()
        val cellY = (pos.y / 200f).toInt()
        return cellX % 2 != 0 || cellY % 2 != 0
    }
}
