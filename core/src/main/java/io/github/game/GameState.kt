package io.github.game

// Holds money, upgrades, and dialog state
class GameState {
    var money: Int = 1000
    var speedLevel: Int = 0
    var handlingLevel: Int = 0
    var currentDialog: String = ""

    fun upgradeSpeed(): Boolean {
        val cost = (speedLevel + 1) * 500
        if (money >= cost) {
            money -= cost
            speedLevel++
            return true
        }
        return false
    }

    fun upgradeHandling(): Boolean {
        val cost = (handlingLevel + 1) * 400
        if (money >= cost) {
            money -= cost
            handlingLevel++
            return true
        }
        return false
    }

    fun showDialog(text: String) {
        currentDialog = text
    }

    fun clearDialog() {
        currentDialog = ""
    }
}
