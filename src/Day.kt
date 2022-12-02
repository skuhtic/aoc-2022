

typealias InputData = List<String>

abstract class Day(day: Int, private val expectedPart1: Int, private val expectedPart2: Int? = null) {
    private val dayTxt = if (day < 10) "0$day" else "$day"
    init {
        require(day in 1..25) { "Day should be in range from 01 to 25" }
    }

    abstract val testInput: InputData

    abstract fun part1(input: InputData): Int

    abstract fun part2(input: InputData): Int

    @Suppress("unused", "SameParameterValue")
    protected open fun log(item: Any) = println("  day$dayTxt: $item")

    private fun checkPart1(expectedPart: Int, input: InputData = testInput) {
        println(separator)
        check(::part1, expectedPart, input, 1)
    }

    private fun checkPart2(expectedPart: Int, input: InputData = testInput) {
        println(separator)
        check(::part2, expectedPart, input, 2)
    }

    private fun runPart1(input: InputData) = try {
        println(separator)
        println("START FOR PART 1")
        val result = part1(input)
        println("RESULT FOR PART 1: $result")
    } catch (ex: NotImplementedError) {
        println("PART 1 NOT IMPLEMENTED")
    }

    private fun runPart1(inputTxtFile: String = "day$dayTxt") = runPart1(readInput(inputTxtFile))

    private fun runPart2(input: InputData) = try {
        println(separator)
        println("START FOR PART 2")
        val result = part2(input)
        println("RESULT FOR PART 2: $result")
    } catch (ex: NotImplementedError) {
        println("PART 2 NOT IMPLEMENTED")
    }

    private fun runPart2(inputTxtFile: String = "day$dayTxt") = runPart2(readInput(inputTxtFile))

    fun execute(onlyWithRealData: Boolean = false) {
        if (expectedPart2 == null) {
            if(!onlyWithRealData) checkPart1(expectedPart1)
            runPart1()
            return
        }
        if(!onlyWithRealData) checkPart2(expectedPart2)
        runPart2()
    }

    companion object {
        const val separator = "---------------------------------------------"

        private fun check(expected: Int, partNo: Int? = null, block: () -> Int) {
            val part = partNo?.let { " $it" } ?: ""
            println("Checking part$part")
            val result = block()
            check(expected == result) { "Part$part check failed! Expected $expected but got $result" }
            println("Part$part check ok! Result: $result")
        }

        private fun check(block: (lines: List<String>) -> Int, expected: Int, input: List<String>, partNo: Int? = null) =
            check(expected, partNo) { block(input) }

    }
}

