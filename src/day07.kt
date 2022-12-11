fun main() {
    day07.execute(forceBothParts = true)
}

val day07 = object : Day<Long>(7, 95437, 24933642) {
    override val testInput: InputData = """
        ${'$'} cd /
        ${'$'} ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        ${'$'} cd a
        ${'$'} ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        ${'$'} cd e
        ${'$'} ls
        584 i
        ${'$'} cd ..
        ${'$'} cd ..
        ${'$'} cd d
        ${'$'} ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent().lines()

    override fun part1(input: InputData): Long = getDirsFromInput(input)
        .fold(0) { sum, dir -> if (dir.dirSize <= 100_000) sum + dir.dirSize else sum }

    override fun part2(input: InputData): Long {
        val dirs = getDirsFromInput(input)
        val needToFree = 30_000_000 - ( 70_000_000 - dirs.first().dirSize)
        return dirs.asSequence().sortedBy { it.dirSize }.first { it.dirSize >= needToFree }.dirSize
    }

    fun getDirsFromInput(input: InputData) = buildList {
        var cd = Dir("", null).also { add(it) }
        input.asSequence().drop(1).forEach { cp ->
            when {
                cp.first().isDigit() -> cd.add(cp.substringBefore(' ').toIntOrNull() ?: error("Input error: $cp"))
                cp.startsWith("$ cd ..") -> cd = cd.parent ?: error("cd .. on root")
                cp.startsWith("$ cd ") -> cd = Dir(cp.drop(5), cd).also {
                    cd.add(it)
                    add(it)
                }

                cp == "$ ls" -> Unit
                cp.startsWith("dir") -> Unit
                else -> error("Check input: $cp")
            }
        }
    }
}

class Dir(private val name: String, val parent: Dir?) {
    private val children = mutableListOf<Dir>()
    private var sizeOfFiles = 0
    val dirSize: Long by lazy {
        sizeOfFiles + children.sumOf { it.dirSize }
    }
    private val fullPath: String by lazy {
        (parent?.fullPath ?: "/").let {
            if (it.endsWith("/")) it + name else "$it/$name"
        }
    }

    override fun toString(): String = "Dir(${dirSize.toString().padStart(10)}, $name, $fullPath)"

    fun add(fileSize: Int) {
        sizeOfFiles += fileSize
    }

    fun add(dir: Dir) {
        children.add(dir)
    }
}
