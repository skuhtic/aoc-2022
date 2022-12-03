fun main() {

    object : Day(3, 157, 70) {

        override val testInput: InputData
            get() = """vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw""".readInput

        override fun part1(input: InputData): Int = input
            .map { line ->
                line.length.let { line.take(it / 2).toSet() to line.drop(it / 2).toSet() }
            }.map { (first, second) ->
                first.intersect(second).let {
                    when (it.size) {
                        1 -> it.first()
                        else -> error("Invalid input")
                    }
                }
            }.sumOf { it.priority }

        override fun part2(input: InputData): Int = input
            .chunked(3) {
                Triple(it[0].toSet(), it[1].toSet(), it[2].toSet())
            }
            .map { (first, second, third) ->
                first.intersect(second).intersect(third).let {
                    when (it.size) {
                        1 -> it.first()
                        else -> error("Invalid input")
                    }
                }
            }.sumOf { it.priority }

        val Char.priority
            get() = when {
                isLowerCase() -> this - 'a' + 1
                isUpperCase() -> this - 'A' + 27
                else -> error("Invalid input")
            }

    }.execute(onlyTests = true)

}

fun List<Set<Char>>.intersectSoloElement() = this.reduce { acc, items ->
    acc.intersect(items)
}.let {
    when (it.size) {
        1 -> it.first()
        else -> error("Invalid input")
    }
}