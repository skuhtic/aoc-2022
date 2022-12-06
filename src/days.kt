fun main() {
    val onlyTests = false
    val forceBothParts = true

    listOf(
        day01,
        day02,
        day03,
        day04,
        day05,
        day06,
    ).forEach {
        it.execute(
            onlyTests = onlyTests,
            onlyRealData = !onlyTests,
            forceBothParts = forceBothParts)
    }
}
