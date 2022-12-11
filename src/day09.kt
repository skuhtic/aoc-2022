import kotlin.math.abs
import kotlin.math.max

fun main() {
    day09.execute(onlyTests = false, forceBothParts = true)
}

val day09 = object : Day<Int>(9, 88, 36) {
    override val testInput: InputData
        get() = """
            R 5
            U 8
            L 8
            D 3
            R 17
            D 10
            L 25
            U 20
        """.trimIndent().lines()

    override fun part1(input: InputData): Int {
        val directions = input.map { line ->
            line.first() to line.drop(2).toInt()
        }
        var head = (0 to 0)
        var tail = (0 to 0)
        val visited = mutableSetOf(tail)

        directions.forEach { (dir, dist) ->
            repeat(dist) {
                head = head.moveOneInDirection(dir)
                if (!tail.isInRangeOfOne(head)) {
                    tail = tail.moveOneTowards(head) // tail =
                    visited.add(tail)
                }
            }
        }
        return visited.count()
    }

    override fun part2(input: InputData): Int {
        val directions = input.map { line ->
            line.first() to line.drop(2).toInt()
        }
        val knots = MutableList(10) { 0 to 0 }
        val visited = mutableSetOf(knots.last())

        directions.forEach { (dir, dist) ->
            repeat(dist) {// Direction
                knots.indices.forEach move@{ kNo ->
                    if (kNo == 0) { // Move head
                        knots[kNo] = knots[kNo].moveOneInDirection(dir)
                    } else { // rest follows
                        if (knots[kNo].isInRangeOfOne(knots[kNo - 1])) return@move
                        knots[kNo] = knots[kNo].moveOneTowards(knots[kNo - 1])
                    } // move done
                    visited.add(knots.last())
                }
            } // One direction done
        } // Directions done
        return visited.count()
    }

    fun Pair<Int, Int>.moveOneInDirection(direction: Char): Pair<Int, Int> = when (direction) {
        'L' -> first - 1 to second
        'R' -> first + 1 to second
        'U' -> first to second + 1
        'D' -> first to second - 1
        else -> error("Invalid input, direction = $direction")
    }
}

fun Pair<Int, Int>.moveOneTowards(other: Pair<Int, Int>): Pair<Int, Int> {
    val x = if (other.first == first) 0 else if (other.first > first) 1 else -1
    val y = if (other.second == second) 0 else if (other.second > second) 1 else -1
    return first + x to second + y
}

fun Pair<Int, Int>.isInRangeOfOne(other: Pair<Int, Int>): Boolean {
    val max = max(abs(other.first - first), abs(other.second - second))
    return max <= 1
}
