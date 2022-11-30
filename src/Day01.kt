fun main() {

    class Day01 : Day() {
        override fun part1(input: InputData): Int {
            log("Test")
            return input.first().toInt()
        }

        override fun part2(input: InputData): Int {
            return input.sumOf { it.toInt() }
        }

    }

    val day = Day01()

    day.checkPart1(199, test1)
    day.checkPart2(2256, test1)

    day.runPart1("day01_part1")
    day.runPart2("day01_part1")
}

val test1 = """
199
200
208
210
200
207
240
269
260
263""".readInput