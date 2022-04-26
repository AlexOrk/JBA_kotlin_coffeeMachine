package alex

import java.util.*

class CoffeeMachine {
    private var water = InitialData.receiveData("water")
    private var milk = InitialData.receiveData("milk")
    private var beans = InitialData.receiveData("beans")
    private var cups = InitialData.receiveData("cups")
    private var money = InitialData.receiveData("money")

    private val stopList = mutableListOf<String>()

    private enum class Action {
        BUY, FILL, TAKE, REMAINING
    }

    private enum class CoffeeType(val water: Int, val milk: Int, val beans: Int, val money: Int) {
        ESPRESSO(250, 0, 16, 4),
        LATTE(350, 75, 20, 7),
        CAPPUCINO(200, 100, 12, 6)
    }

    private fun getCommand() = readLine()!!

    fun start() {
        var action: String

        while (true) {
            println("Write action (buy, fill, take, remaining, exit):")
            action = getCommand().uppercase(Locale.getDefault())

            when (action) {
                Action.REMAINING.name -> printMessage()
                Action.BUY.name -> buy()
                Action.FILL.name -> fill()
                Action.TAKE.name -> take()
                else -> break
            }
        }
    }

    private fun printMessage() {
        println(
            "The coffee machine has:\n" +
                    "$water ml of water\n" +
                    "$milk ml of milk\n" +
                    "$beans g of coffee beans\n" +
                    "$cups disposable cups\n" +
                    "\$$money of money\n"
        )
    }

    private fun buy() {
        println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
        val input = getCommand().toIntOrNull() ?: return
        makeCoffee(CoffeeType.values()[input - 1])
    }

    private fun fill() {
        println("Write how many ml of water do you want to add:")
        water += getCommand().toInt()

        println("Write how many ml of milk do you want to add:")
        milk += getCommand().toInt()

        println("Write how many grams of coffee beans do you want to add:")
        beans += getCommand().toInt()

        println("Write how many disposable cups of coffee do you want to add:")
        cups += getCommand().toInt()
    }

    private fun take() {
        println("I gave you \$$money\n")
        money = 0
    }

    private fun makeCoffee(coffee: CoffeeType) {
        if (cups == 0) stopList.add("cups")
        if (water - coffee.water < 0) stopList.add("water")
        if (milk - coffee.milk < 0) stopList.add("milk")
        if (beans - coffee.beans < 0) stopList.add("beans")

        if (stopList.isEmpty()) {
            println("I have enough resources, making you a coffee!\n")

            water -= coffee.water
            milk -= coffee.milk
            beans -= coffee.beans
            money += coffee.money
            cups--
        } else {
            println("Sorry, not enough ${stopList.joinToString(", ")}!\n")
            stopList.clear()
        }
    }
}