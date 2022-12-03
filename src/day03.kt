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
            .map { line -> line.splitBySize(2).map { it.toSet() } }
            .map { it.intersectSoloElementOrNull ?: error("Invalid input") }
            .sumOf { it.priority }

        override fun part2(input: InputData): Int = input.map { it.toSet() }
            .chunked(3)
            .map { it.intersectSoloElementOrNull ?: error("Invalid input") }
            .sumOf { it.priority }

        val Char.priority
            get() = when {
                isLowerCase() -> this - 'a' + 1
                isUpperCase() -> this - 'A' + 27
                else -> error("Invalid input")
            }

    }.execute()

}

fun String.splitBySize(noOfParts: Int) = (this.length / noOfParts).let {
    listOf(this.take(it), this.drop(it))
}

val <T> List<Set<T>>.intersectSoloElementOrNull
    get() = this.reduce { acc, items ->
        acc.intersect(items)
    }.let {
        when (it.size) {
            1 -> it.first()
            else -> null
        }
    }