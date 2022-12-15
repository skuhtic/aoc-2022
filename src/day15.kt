import kotlin.math.abs

fun main() {
    day15.execute(onlyTests = false, forceBothParts = false)
}

val day15 = object : Day<Long>(15, 26, 56000011) {
    override val testInput: InputData
        get() = """
            Sensor at x=2, y=18: closest beacon is at x=-2, y=15
            Sensor at x=9, y=16: closest beacon is at x=10, y=16
            Sensor at x=13, y=2: closest beacon is at x=15, y=3
            Sensor at x=12, y=14: closest beacon is at x=10, y=16
            Sensor at x=10, y=20: closest beacon is at x=10, y=16
            Sensor at x=14, y=17: closest beacon is at x=10, y=16
            Sensor at x=8, y=7: closest beacon is at x=2, y=10
            Sensor at x=2, y=0: closest beacon is at x=2, y=10
            Sensor at x=0, y=11: closest beacon is at x=2, y=10
            Sensor at x=20, y=14: closest beacon is at x=25, y=17
            Sensor at x=17, y=20: closest beacon is at x=21, y=22
            Sensor at x=16, y=7: closest beacon is at x=15, y=3
            Sensor at x=14, y=3: closest beacon is at x=15, y=3
            Sensor at x=20, y=1: closest beacon is at x=15, y=3
        """.trimIndent().lines()

    override fun part1(input: InputData): Long {
        val parsed = parsedInput(input)
        val sensors = parsed.map { (s, b) ->
            s to abs(s.first - b.first) + abs(s.second - b.second)
        }

        val onLine = if (sensors.maxOf { it.first.second } < 50) 10 else 2_000_000
        val beacons = parsed.filter { it.second.second == onLine }.map { it.second.first }.toSet()
        val line = sensors.asSequence().flatMap { (s, d) ->
            val dy = abs(s.second - onLine)
            val dx = d - dy
            ((s.first - dx)..(s.first + dx)).toList()
        }.toSet()

        return (line - beacons).count().toLong()
    }

    override fun part2(input: InputData): Long {
        val parsed = parsedInput(input)
        val sensors = parsed.map { (s, b) ->
            s to abs(s.first - b.first) + abs(s.second - b.second)
        }
        val range = if (sensors.maxOf { it.first.second } < 50) 0..20 else 0..4_000_000

        for (x in range) {
//            x.logIt("XXX")
            var y = 0
            do {
                if (!sensors.any { (s, sd) -> abs(s.first - x) + abs(s.second - y) <= sd }) {
                    return x * 4_000_000L + y
                }
                val md = sensors.minOf { (s, sd) -> abs(s.first - x) + abs(s.second - y) - sd }
//                md.logIt()
                val dx = if (md == 0) 1 else if (md < 0) -md else error("$x, $y")
                y += dx
            } while (y in range)
        }
        return 1
    }

    fun parsedInput(input: InputData) = input.map { line ->
        line.split(':').let { (s, b) ->
            s.split(',').let { (sx, sy) ->
                sx.substringAfterLast('=').toInt() to sy.substringAfterLast('=').toInt()
            } to b.split(',').let { (bx, by) ->
                bx.substringAfterLast('=').toInt() to by.substringAfterLast('=').toInt()
            }
        }
    }
}
