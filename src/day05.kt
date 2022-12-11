fun main() {
    day05.execute(onlyTests = false, forceBothParts = true)
}

val day05 = object : Day<String>(5, "CMZ", "MCD") {
    override val testInput: InputData
        get() = """
                    [D]    
                [N] [C]    
                [Z] [M] [P]
                 1   2   3 
                
                move 1 from 2 to 1
                move 3 from 1 to 3
                move 2 from 2 to 1
                move 1 from 1 to 2
            """.trimIndent().lines()

    override fun part1(input: InputData): String = solution(input, false)

    override fun part2(input: InputData): String = solution(input, true)

    fun solution(input: InputData, multipleCreatesMove: Boolean): String {
        val (stackInput, procedureInput) = input.splitByEmpty()

        val stacks = stackInput
            .dropLast(1)
            .map { line -> line.chunked(4) { it.trim('[', ']', ' ').singleOrNull() } }
            .reversed()//.logIt()
            .transposedWithNullIfMissing()//.logIt()
            .map { ArrayDeque(it.filterNotNull()) }//.logIt()

        val procedures = procedureInput
            .map { line ->
                line.split(' ')
                    .filterIndexed { i, _ -> i % 2 == 1 }
                    .map { it.toInt() }
            }.map { Triple(it[0], it[1] - 1, it[2] - 1) }//.logIt()

        procedures.forEach { (amount, from, to) ->
            if (!multipleCreatesMove) repeat(amount) {
                stacks[to].addLast(stacks[from].removeLast())
            }
            else List(amount) { stacks[from].removeLast() }.reversed().forEach {
                stacks[to].addLast(it)
            }
        }

        return stacks.fold("") { result, stack -> result + stack.last() }
    }

}

fun <T> List<List<T>>.transposed(): List<List<T>> =
    List(this.maxOf { it.size }) { j -> List(this.size) { i -> this[i][j] } }

fun <T> List<List<T>>.transposedWithNullIfMissing(): List<List<T?>> =
    List(this.maxOf { it.size }) { j -> List(this.size) { i -> this.getOrNull(i)?.getOrNull(j) } }
