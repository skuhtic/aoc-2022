fun main() {
    day11.execute(onlyTests = false, forceBothParts = true)
}

val day11 = object : Day<Long>(11, 10605, 2713310158) {
    override val testInput: InputData
        get() = TEST_INPUT.trimIndent().lines()

    override fun part1(input: InputData): Long { // divisibleBys: [23, 19, 13, 17]
        val monkeys = mutableListOf<Monkey>()
        input.chunked(7).map { Monkey.fromLinesToGroup(it, monkeys) }
        repeat(20) {
            monkeys.forEach { it.turn() }
        }
        return monkeys.map { it.inspections }.sortedDescending().take(2).fold(1L) { acc, i -> acc * i }
    }

    override fun part2(input: InputData): Long { // divisibleBys: [2, 7, 13, 3, 19, 17, 11, 5]
        val monkeys = mutableListOf<Monkey>()
        input.chunked(7).map { Monkey.fromLinesToGroup(it, monkeys) }
        val common = monkeys.fold(1) { acc, m -> acc * m.testDivBy }
        monkeys.forEach { m ->
            m.setReliefFunctionTo { it % common }
        }
        repeat(10_000) {
            monkeys.forEach { it.turn() }
        }
        return monkeys.map { it.inspections }.sortedDescending().take(2).fold(1L) { acc, i -> acc * i }
    }
}

private class Monkey private constructor(
    val id: Int,
    val operation: (Long) -> Long,
    val monkeyGroup: List<Monkey>,
    val testDivBy: Int,
    val toIfTestTrue: Int,
    val toIfTestFalse: Int,
    initialItems: List<Long>
) {
    private val items: ArrayDeque<Long> = ArrayDeque(initialItems)
    override fun toString(): String = "Monkey$id ($inspections): $items"
    var inspections: Int = 0
        private set
    fun add(item: Long) = items.addLast(item)
    var relief: (Long) -> Long = { it / 3 }
        private set
    fun setReliefFunctionTo(reliefFunction: (Long) -> Long) {
        relief = reliefFunction
    }

    fun turn() {
        items.removeAll { i ->
            val x = relief(operation(i))
            inspections++
            val to = if (x % testDivBy == 0L) toIfTestTrue else toIfTestFalse
            monkeyGroup[to].add(x)
            true
        }
    }

    companion object {
        fun fnSum(with: Long): (Long) -> Long = { it + with }
        fun fnProduct(with: Long): (Long) -> Long = { it * with }
        val fnSquare: (Long) -> Long = { it * it }

        fun fromLinesToGroup(lines: InputData, monkeyGroup: MutableList<Monkey>): Monkey {
            val l = if (lines.first().isEmpty()) lines.drop(1) else lines
            require(l.size in 6..7)
            val id = l[0].substringAfterLast("Monkey ").trimEnd(':').toInt()
            val items = l[1].substringAfterLast("items: ").split(", ").map { it.toLong() }
            val opDef = l[2].substringAfter("= old ")
            val testDivBy = l[3].substringAfter("divisible by ").toInt()
            val toIfTestTrue = l[4].substringAfter("true: throw to monkey ").toInt()
            val toIfTestFalse = l[5].substringAfter("false: throw to monkey ").toInt()
            val operation: (Long) -> Long = when {
                opDef.startsWith('+') -> fnSum(opDef.substringAfter(' ').toLong())
                opDef == "* old" -> fnSquare
                opDef.startsWith('*') -> fnProduct(opDef.substringAfter(' ').toLong())
                else -> error("Invalid input")
            }
            val monkey = Monkey(id, operation, monkeyGroup, testDivBy, toIfTestTrue, toIfTestFalse, items)
            monkeyGroup.add(monkey)
            return monkey
        }
    }
}

private const val TEST_INPUT = """
Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1"""