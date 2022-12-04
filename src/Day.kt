import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

typealias InputData = List<String>

abstract class Day(day: Int, private val expectedPart1: Int, private val expectedPart2: Int?) {
    private val dayTxt = if (day < 10) "0$day" else "$day"

    init {
        require(day in 1..25) { "Day should be in range from 01 to 25" }
    }

    @Suppress("SpellCheckingInspection")
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

    @OptIn(ExperimentalTime::class)
    private fun <T, R> runMeasured(input: T, block: (T) -> R) = measureTimedValue {
        block(input)
    }.let { (result, duration) ->
        println("Execution duration: ${duration.toString(DurationUnit.SECONDS, 4)}")
        result
    }

    private fun runPart1(input: InputData) = try {
        println(separator)
        println("START FOR PART 1")
        val result = runMeasured(input, ::part1) //part1(input)
        println("RESULT FOR PART 1: $result")
    } catch (ex: NotImplementedError) {
        println("PART 1 NOT IMPLEMENTED")
    }

    private fun runPart1(inputTxtFile: String = "day$dayTxt") = runPart1(readInput(inputTxtFile))

    private fun runPart2(input: InputData) = try {
        println(separator)
        println("START FOR PART 2")
        val result = runMeasured(input, ::part2) //part2(input)
        println("RESULT FOR PART 2: $result")
    } catch (ex: NotImplementedError) {
        println("PART 2 NOT IMPLEMENTED")
    }

    private fun runPart2(inputTxtFile: String = "day$dayTxt") = runPart2(readInput(inputTxtFile))

    fun execute(onlyTests: Boolean = false, onlyRealData: Boolean = false, forceBothParts: Boolean = false) {
        if (expectedPart2 == null || forceBothParts) {
            if (!onlyRealData) checkPart1(expectedPart1)
            if (!onlyTests) runPart1()
        }
        if (expectedPart2 != null) {
            if (!onlyRealData) checkPart2(expectedPart2)
            if (!onlyTests) runPart2()
        } else if (forceBothParts) error("No expected value for part 2")
    }

    @Suppress("unused")
    fun <T : Any> T.logIt(): T = this.also { log(it) }

    companion object {
        const val separator = "---------------------------------------------"

        private fun check(expected: Int, partNo: Int? = null, block: () -> Int) {
            val part = partNo?.let { " $it" } ?: ""
            println("Checking part$part")
            val result = block()
            check(expected == result) { "Part$part check failed! Expected $expected but got $result" }
            println("Part$part check ok! Result: $result")
        }

        private fun check(
            block: (lines: List<String>) -> Int,
            expected: Int,
            input: List<String>,
            partNo: Int? = null
        ) =
            check(expected, partNo) { block(input) }

    }
}


