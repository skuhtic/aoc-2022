fun main() {

    object : Day(2, 15, 12) {
        private val heCode = 'A'.code
        private val weCode = 'X'.code

        override val testInput: InputData
            get() = """
A Y
B X
C Z""".readInput

        override fun part1(input: InputData): Int = input
            .map { round ->
                round.split(' ').let { it.first()[0].code - heCode to it.last()[0].code - weCode }
            }
//            .also { log(it) }
            .fold(0) { s, (he, we) ->
                when {
                    he == we -> 3
                    (he + 1).mod(3) == we -> 6
                    he == (we + 1).mod(3) -> 0
                    else -> error("Invalid input")
                }.let { win ->
                    s + win + we + 1
                }
            }

        override fun part2(input: InputData): Int = input
            .map { round ->
                round.split(' ').let { it.first()[0].code - heCode to it.last()[0].code - weCode }
            }
//            .also { log(it) }
            .fold(0) { s, (he, outcome) ->
                when (outcome) {
                    0 -> (he - 1).let { if (it < 0) it + 3 else it }
                    1 -> he
                    2 -> (he + 1).mod(3)
                    else -> error("Invalid input")
                }.let { we ->
                    val win = outcome * 3
                    s + win + we + 1
                }
//                    .also { log(it) }
            }

    }.execute()

}
