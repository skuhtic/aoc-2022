fun main() {

    object : Day(4, 2, 4) {
        override val testInput: InputData
            get() = """
                2-4,6-8
                2-3,4-5
                5-7,7-9
                2-8,3-7
                6-6,4-6
                2-6,4-8
            """.trimIndent().lines()

        override fun part1(input: InputData): Int = input.fold(0) { cnt, line ->
            line.split(',').let { (first, last) -> first.getRange() to last.getRange() }
                .let { (first, last) -> if (first.contains(last) || last.contains(first)) cnt + 1 else cnt }
        }

        override fun part2(input: InputData): Int = input.fold(0) { cnt, line ->
            line.split(',').let { (first, last) ->
                if (first.getRange().intersect(last.getRange()).isNotEmpty()) cnt + 1 else cnt
            }
        }

    }.execute(onlyTests = true, forceBothParts = true)

}

fun String.getRange(separator: Char = '-'): IntRange =
    split(separator).let { (start, end) -> start.toInt()..end.toInt() }

fun IntRange.contains(other: IntRange) = contains(other.first) && contains(other.last)

