typealias InputData = List<String>

abstract class Day {
    private val day: String by lazy {
        val name = this::class.simpleName
        require(name != null) { "Cannot get class name" }
        require(name.length == 5) { "Class name should be DayXX (XX is number from 01 to 25)" }
        require(name.startsWith("Day", ignoreCase = true)) { "Class name should be start with Day" }
        val dayTxt = name.takeLast(2)
        val dayNo = dayTxt.toIntOrNull()
        require(dayNo != null) { "Last two characters should form a number" }
        require(dayNo in 1..25) { "Last two characters should a number in range from 01 to 25" }
        dayTxt
    }

    abstract fun part1(input: InputData): Int
    abstract fun part2(input: InputData): Int

    @Suppress("SameParameterValue")
    protected open fun log(item: Any) = println("  day$day: $item")

    fun checkPart1(expectedPart: Int, input: InputData) {
        println(separator)
        check(::part1, expectedPart, input, 1)
    }

    fun checkPart2(expectedPart: Int, input: InputData) {
        println(separator)
        check(::part2, expectedPart, input, 2)
    }

    private fun runPart1(input: InputData) = try {
        println(separator)
        println("START FOR PART 2")
        val result = part1(input)
        println("RESULT FOR PART 1: $result")
    } catch (ex: NotImplementedError) {
        println("PART 1 NOT IMPLEMENTED")
    }

    fun runPart1(inputTxtFile: String) = runPart1(readInput(inputTxtFile))

    private fun runPart2(input: InputData) = try {
        println(separator)
        println("START FOR PART 2")
        val result = part2(input)
        println("RESULT FOR PART 2: $result")
    } catch (ex: NotImplementedError) {
        println("PART 2 NOT IMPLEMENTED")
    }

    fun runPart2(inputTxtFile: String) = runPart2(readInput(inputTxtFile))

    companion object {
        const val separator = "---------------------------------------------"
    }
}

fun check(expected: Int, partNo: Int? = null, block: () -> Int) {
    val part = partNo?.let { " $it" } ?: ""
    println("Checking part$part")
    val result = block()
    check(expected == result) { "Part$part check failed! Expected $expected but got $result" }
    println("Part$part check ok! Result: $result")
}

fun check(block: (lines: List<String>) -> Int, expected: Int, input: List<String>, partNo: Int? = null) =
    check(expected, partNo) { block(input) }

//fun ((lines: List<String>) -> Int).check(expected: Int, input: List<String>, partNo: Int? = null) =
//    check(expected, partNo) { this(input) }

//fun List<String>.check1(expected: Int, block: (lines: List<String>) -> Int) = check(expected, 1) {
//    block(this)
//}

//    check(199) { part1(test1) }
//    check(::part1, 199, test1)
//    check(2256) { part2(test1) }
