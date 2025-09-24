fun main () {
    println("Use the val keyword when the value doesn't change. ")
    println("Use the var keyword when the value can change.")
    println("When you define a function, you define the parameters that can be passed to it. ")
    println("When you call a function, you pass arguments for the parameters.")
}

fun main() { 
    println("New chat message from a friend")
}

// String templates
fun main() {
    val discountPercentage: Int = 20
    val item = "Google Chromecast"
    val offer = "Sale - Up to $discountPercentage% discount on $item! Hurry up!"
    
    println(offer)
}

// String concatenation
fun main() {
    val numberOfAdults = 20
    val numberOfKids = 30
    val total = numberOfAdults + numberOfKids

    println("The total party size is: $total")
}

// Message formatting
fun main() {
    val baseSalary = 5000
    val bonusAmount = 1000
    val totalSalary = baseSalary + bonusAmount

    println("Congratulations for your bonus! You will receive a total of $totalSalary (additional bonus).")
}

// Implement basic math operations
fun main() {
    val firstNumber = 10
    val secondNumber = 5
    val thirdNumber = 8
    
    val sumResult = add(firstNumber, secondNumber)
    val substractionResult = substract(thirdNumber, secondNumber)

    println("$firstNumber + $secondNumber = $sumResult")
    println("$thirdNumber - $secondNumber = $substractionResult")
}

fun add(a: Int, b: Int): Int {
    return a + b
}

fun substract(a: Int, b: Int): Int {
    return a - b
}

// Default parameters
fun main() {
    val firstUserEmailId = "user_one@gmail.com"

    println(displayAlertMessage(emailId = firstUserEmailId))
    println()

    val secondUserOperatingSystem = "Windows"
    val secondUserEmailId = "user_two@gmail.com"

    println(displayAlertMessage(secondUserOperatingSystem, secondUserEmailId))
    println()

    val thirdUserOperatingSystem = "Mac OS"
    val thirdUserEmailId = "user_three@gmail.com"

    println(displayAlertMessage(thirdUserOperatingSystem, thirdUserEmailId))
    println()
}

fun displayAlertMessage(
    operatingSystem: String = "Unknown OS",
    emailId: String
): String {
    return "There's a new sign-in request on $operatingSystem for your Google Account $emailId."
}

// Pedometer
fun main() {
    val steps = 4000
    val caloriesBurned = pedometerStepsToCalories(steps);

    println("Walking $steps steps burns $caloriesBurned calories") 
}

fun pedometerStepsToCalories(numberOfSteps: Int): Double {
    val caloriesBurnedForEachStep = 0.04
    val totalCaloriesBurned = numberOfSteps * caloriesBurnedForEachStep
    return totalCaloriesBurned
}

// Compare two numbers
fun main() {
    val timeSpentToday = 5
    val timeSpentYesterday = 3

    val isMore = isTimeSpentTodayGreater(timeSpentToday, timeSpentYesterday)
    println("You have spent more time today than yesterday: $isMore")
}

fun isTimeSpentTodayGreater(timeToday: Int, timeYesterday: Int): Boolean {
    return timeToday > timeYesterday
}

// Move duplicate code into a function
fun main() {
    printWeatherReport("Ankara", 27, 31, 82)
    printWeatherReport("Tokyo", 32, 36, 10)
    printWeatherReport("Cape Town", 59, 64, 2)
    printWeatherReport("Guatemala City", 50, 55, 7)
}

fun printWeatherReport(city: String, lowTemp: Int, highTemp: Int, rainChance: Int) {
    println("City: $city")
    println("Low temperature: $lowTemp, High temperature: $highTemp")
    println("Chance of rain: $rainChance%")
    println()
}
