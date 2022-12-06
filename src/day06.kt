fun main() {
    day06.execute()
}

val day06 = object : Day<Int>(6, 7, 19) {
    override val testInput: InputData
        get() = "mjqjpqmgbljsphdztnvjfqwrcgsmlb".lines()

    override fun part1(input: InputData): Int = solution(input, 4)

    override fun part2(input: InputData): Int = solution(input, 14)

    fun solution(input: InputData, size: Int) = input.first().let { comm ->
        comm.windowed(size)
            .first { it.toSet().size == size }
            .let { comm.indexOf(it) + size }
    }
}