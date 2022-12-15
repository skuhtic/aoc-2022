import kotlin.math.max
import kotlin.math.min

fun main() {
    day14.execute(onlyTests = false, forceBothParts = true)
}

val day14 = object : Day<Int>(14, 24, 93) { // 93
    override val testInput: InputData
        get() = """
            498,4 -> 498,6 -> 496,6
            503,4 -> 502,4 -> 502,9 -> 494,9
        """.trimIndent().lines()

    override fun part1(input: InputData): Int = solution(input)

    override fun part2(input: InputData): Int = solution(input, true)

    fun solution(input: InputData, floorInsteadAbyss: Boolean = false): Int {
        val (cave, abyss) = parseCave(input)
        val work = cave.toMutableSet()
        val px = listOf(0, -1, 1)

        fun drop(floorInsteadAbyss: Boolean): Rock? {
            var y = 0
            var x = 500
            while (true) {
                val dx = px.firstOrNull { !work.contains(Rock(x + it, y + 1)) }
                if (dx == null && y == 0 && !work.contains(Rock(x, y))) {
                    "full".logIt()
                    return Rock(x, y)
                }
                x += dx ?: return Rock(x, y)
                if (floorInsteadAbyss && y == abyss + 1) return Rock(x, y)
                if (++y == abyss && !floorInsteadAbyss) break
            }
            return null
        }
        while (true) {
            val new = drop(floorInsteadAbyss) ?: break
//            work.debugPrint(cave)
            if (!work.add(new)) break
        }
        work.debugPrint(cave)
        return work.size - cave.size
    }

    fun parseCave(input: InputData): Pair<Set<Rock>, Int> {
        var abyss = 0
        return buildSet {
            input.forEach { line ->
                line.split(" -> ").map { p ->
                    p.split(',').let { it.first().toInt() to it.last().toInt() }
                }.windowed(2) { (from, to) ->
                    abyss = max(abyss, max(from.second, to.second))
                    when {
                        from.first == to.first -> {
                            val f = min(from.second, to.second)
                            val t = max(from.second, to.second)
                            (f..t).map { from.first to it }
                        }

                        from.second == to.second -> {
                            val f = min(from.first, to.first)
                            val t = max(from.first, to.first)
                            (f..t).map { it to to.second }
                        }

                        else -> error("Input: $line")
                    }.forEach { add(it) }
                }
            }
        } to abyss
    }

}

private typealias Rock = Pair<Int, Int>

private fun Set<Rock>.debugPrint(init: Set<Rock>) {
    var xMax = 500
    var xMin = 500
    var yMin = 0
    var yMax = 0
    forEach {
        xMin = min(xMin, it.first)
        xMax = max(xMax, it.first + 1)
        yMin = min(yMin, it.second)
        yMax = max(yMax, it.second + 1)
    }
    val board = (yMin..yMax).map { ".".repeat(xMax - xMin).toCharArray() }
    forEach { (x, y) -> board[y - yMin][x - xMin] = 'o' }
    init.forEach { (x, y) -> board[y - yMin][x - xMin] = '#' }
    println(board.joinToString("\n", "\n") { it.joinToString("") })
}