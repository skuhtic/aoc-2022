fun main() {
    day03.execute(onlyTests = false, forceBothParts = true)
}

val day03 = object : Day<Int>(3, 157, 70) {
    @Suppress("SpellCheckingInspection")
    override val testInput: InputData
        get() = """
                vJrwpWtwJgWrhcsFMMfFFhFp
                jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
                PmmdzqPrVvPwwTWBwg
                wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
                ttgJtRGJQctTZtZT
                CrZsJsPPZsGzwwsLwLmpwMDw
            """.trimIndent().lines()

    override fun part1(input: InputData): Int = input.asSequence()
        .map { line -> line.splitIntoPartsExact(2).map { it.toSet() } }
        .map { it.intersectSoloElementOrNull ?: error("Invalid input") }
        .sumOf { it.priority }

    override fun part2(input: InputData): Int = input.asSequence()
        .map { it.toSet() }
        .chunked(3)
        .map { it.intersectSoloElementOrNull ?: error("Invalid input") }
        .sumOf { it.priority }

    val Char.priority
        get() = when {
            isLowerCase() -> this - 'a' + 1
            isUpperCase() -> this - 'A' + 27
            else -> error("Invalid input")
        }
}

fun String.splitIntoPartsExact(noOfParts: Int): List<String> = (length / noOfParts).let { partLength ->
    require(length == noOfParts * partLength) { "String not splittable into $noOfParts parts with same size" }
    chunked(partLength)
}

val <T> List<Set<T>>.intersectSoloElementOrNull: T?
    get() = this.reduce { acc, items -> acc.intersect(items) }.singleOrNull()
