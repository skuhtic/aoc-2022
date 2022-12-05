fun main() {
    val onlyTests = true
    val forceBothParts = true

    listOf(
        day01,
        day02,
        day03,
        day04,
        day05,
    ).forEach {
        it.execute(onlyTests = onlyTests, forceBothParts = forceBothParts)
    }
}
