fun main() {

    object : Day(2, 15, 12) {

        override val testInput: InputData
            get() = """
A Y
B X
C Z""".readInput

        override fun part1(input: InputData): Int = input
            .map { it.parseRound() }
            .fold(0) { score, (theirHand, ourHand) ->
                when {
                    theirHand == ourHand -> 3
                    (theirHand + 1).mod(3) == ourHand -> 6
                    theirHand == (ourHand + 1).mod(3) -> 0
                    else -> error("Invalid input")
                }.let { winScore ->
                    score + winScore + ourHand + 1
                }
            }

        override fun part2(input: InputData): Int = input
            .map { it.parseRound() }
            .fold(0) { score, (theirHand, outcome) ->
                when (outcome) {
                    0 -> (theirHand - 1).let { if (it < 0) it + 3 else it }
                    1 -> theirHand
                    2 -> (theirHand + 1).mod(3)
                    else -> error("Invalid input")
                }.let { ourHand ->
                    val winScore = outcome * 3
                    score + winScore + ourHand + 1
                }
            }

        fun String.parseRound() = split(' ').let { it.first()[0] - 'A' to it.last()[0] - 'X' }

    }.execute()

}
