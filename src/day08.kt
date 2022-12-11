import kotlin.math.max

fun main() {
    day08.execute(forceBothParts = true)
}

val day08 = object : Day<Int>(8, 21, 8) {
    override val testInput: InputData
        get() = """
            30373
            25512
            65332
            33549
            35390
        """.trimIndent().lines()

    override fun part1(input: InputData): Int {
        val trans = transposedInput(input)
        val mapMaxIndex = input.size - 1
        val visible = mutableSetOf<Pair<Int, Int>>()
        (0..mapMaxIndex).forEach { c ->
            visible.addAll(input[c].visibleIndexesFromOutside().map { it to c })
            visible.addAll(input[c].reversed().visibleIndexesFromOutside().map { mapMaxIndex - it to c })
            visible.addAll(trans[c].visibleIndexesFromOutside().map { c to it })
            visible.addAll(trans[c].reversed().visibleIndexesFromOutside().map { c to mapMaxIndex - it })
        }
        return visible.count()
    }

    override fun part2(input: InputData): Int {
        val trans = transposedInput(input)
        val mapMaxIndex = input.size - 1
        var maxScore = 0
        (0..mapMaxIndex).forEach { x ->
            (0..mapMaxIndex).forEach { y ->
                val tl = input[y].visibleCountFromTree(x)
                val tr = input[y].reversed().visibleCountFromTree(mapMaxIndex - x)
                val td = trans[x].visibleCountFromTree(y)
                val tu = trans[x].reversed().visibleCountFromTree(mapMaxIndex - y)
                (tl * tr * td * tu).let {
                    maxScore = max(maxScore, it)
                }
            }
        }
        return maxScore
    }

    fun String.visibleCountFromTree(pos: Int): Int = drop(pos).visibleCountFromTree()

    fun String.visibleCountFromTree(): Int {
        if (length <= 1) return 0
        val h = first()
        val blocked = asSequence().drop(1).indexOfFirst { c -> c >= h } + 1
        return if (blocked != 0) blocked else length - 1
    }

    fun String.visibleIndexesFromOutside(): Set<Int> {
        return asSequence().drop(1)
            .scanIndexed(0 to first()) { i, acc, c ->
                if (c > acc.second) i + 1 to c else acc
            }.map { it.first }.toSet()
    }

    fun transposedInput(input: InputData): List<String> {
        require(input.size == input.first().length) { "Input is not a square" }
        return input.map { it.toList() }.transposed().map { it.joinToString("") }
    }
}