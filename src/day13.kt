fun main() {
    day13.execute(forceBothParts = true)
}

val day13 = object : Day<Int>(13, 13, 140) {
    override val testInput: InputData
        get() = """
            [1,1,3,1,1]
            [1,1,5,1,1]

            [[1],[2,3,4]]
            [[1],4]

            [9]
            [[8,7,6]]

            [[4,4],4,4]
            [[4,4],4,4,4]

            [7,7,7,7]
            [7,7,7]

            []
            [3]

            [[[]]]
            [[]]

            [1,[2,[3,[4,[5,6,7]]]],8,9]
            [1,[2,[3,[4,[5,6,0]]]],8,9]
        """.trimIndent().lines()

    override fun part1(input: InputData): Int = input.chunked(3).map { pair ->
        pair.let { pair[0] to pair[1] }
    }.mapIndexed { i, (f, s) -> (i + 1) * if (f.compare(s)) 1 else 0 }.sum()

    override fun part2(input: InputData): Int {
        val add1 = "[[2]]"
        val add2 = "[[6]]"
        return buildList {
            input.forEach { line -> if (line.isNotEmpty()) add(line) }
            add(add1)
            add(add2)
        }.sortedWith(comparator).reversed().let { (it.indexOf(add1) + 1) * (it.indexOf(add2) + 1) }
    }

    val comparator = Comparator<String> { a, b -> if (a.compare(b)) 1 else -1 }

    fun String.compare(other: String): Boolean {
        val f = get(0)
        val s = other[0]
        fun getNumber(str: String) = str.takeWhile { it.isDigit() }.let { it.toInt() to str.drop(it.length) }
        fun putBracketsOnNumber(str: String): String = getNumber(str).let { (no, rest) -> "[$no]$rest" }
//        log("$f - $s -> $this vs $other")
        return when {
            f == s -> drop(1).compare(other.drop(1))
            f == '[' && s.isDigit() -> compare(putBracketsOnNumber(other))
            f.isDigit() && s == '[' -> putBracketsOnNumber(this).compare(other)
            f == ']' && s == ',' -> true
            f == ',' && s == ']' -> false
            f == ']' && s.isDigit() -> true
            f.isDigit() && s == ']' -> false
            f == ']' && s == '[' -> true
            f == '[' && s == ']' -> false
            f == ',' && s.isDigit() -> true
            f.isDigit() && s == ',' -> false
            f.isDigit() && s.isDigit() -> getNumber(this).let { fno ->
                getNumber(other).let { sno ->
                    if (fno.first == sno.first) fno.second.compare(sno.second) else fno.first < sno.first
                }
            }

            else -> error("$this vs $s")
        }
    }
}
