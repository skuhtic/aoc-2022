fun main() {

    class Day01 : Day() {
        override fun part1(input: InputData): Int = input.splitByEmpty()
            .maxOf { elf ->
                elf.sumOf { it.toInt() }
            }

        override fun part2(input: InputData): Int = input.splitByEmpty()
            .map { e -> e.sumOf { it.toInt() } }
            .sortedDescending().take(3).sum()
    }

    val day = Day01()

    day.checkPart1(24000, test)
    day.runPart1("day01")

    day.checkPart2(45000, test)
    day.runPart2("day01")
}

fun InputData.splitByEmpty(): List<List<String>> = this.flatMapIndexed { i, s ->
    when {
        i == 0 || i == this.lastIndex -> listOf(i)
        s.isEmpty() -> listOf(i - 1, i + 1)
        else -> emptyList()
    }
}.windowed(2, 2) { (from, to) ->
    this.slice(from..to)
}

val test = """
1000
2000
3000

4000

5000
6000

7000
8000
9000

10000""".readInput