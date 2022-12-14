/*
fun main() {
    dayXX.execute(forceBothParts = true)
}

val dayXX = object : Day<Int>(XX, 0, null) {
    override val testInput: InputData
        get() = """""".trimIndent().lines()

    override fun part1(input: InputData): Int  = solution(input)

    override fun part2(input: InputData): Int = TODO("Not yet implemented")

    fun solution(input: InputData): Int {
        TODO("Not yet implemented")
    }
}
*/

fun main() {
    val onlyTests = false
    val forceBothParts = true

    listOf(
        day01, day02, day03, day04, day05,
        day06, day07, day08, day09, day10,
        day11, day13,
    ).forEach {
        it.execute(
            onlyTests = onlyTests,
            onlyRealData = !onlyTests,
            forceBothParts = forceBothParts
        )
    }
}
