import java.lang.StringBuilder

fun splitIntoBlocks(string: String): List<IntArray> {
    val blocks = mutableListOf<IntArray>()
    for (char in string) {
        blocks.add(intToBits(char.toInt(), 4))
        blocks.add(intToBits(char.toInt() shr 4, 4))
    }
    return blocks.toList()
}

fun encodeBlock(block: IntArray): IntArray {
    val encodingMatrix = listOf(
            intArrayOf(1, 0, 0, 0),
            intArrayOf(0, 1, 0, 0),
            intArrayOf(0, 0, 1, 0),
            intArrayOf(0, 0, 0, 1),
            intArrayOf(1, 1, 1, 0),
            intArrayOf(0, 1, 1, 1),
            intArrayOf(1, 1, 0, 1),
    )
    val encodedBlock = IntArray(7)

    for (row in 0..6) {
        for (col in 0..3) {
            encodedBlock[row] += encodingMatrix[row][col] * block[col]
            encodedBlock[row] %= 2
        }
    }
    return encodedBlock
}

fun calculateControlSums(encodedBlock: IntArray): IntArray {
    val checkingMatrix = listOf(
            intArrayOf(1, 1, 1, 0, 1, 0, 0),
            intArrayOf(0, 1, 1, 1, 0, 1, 0),
            intArrayOf(1, 1, 0, 1, 0, 0, 1)
    )
    val controlSums = IntArray(3)

    for (row in 0..2) {
        for (col in 0..6) {
            controlSums[row] += checkingMatrix[row][col] * encodedBlock[col]
            controlSums[row] %= 2
        }
    }
    return controlSums
}

fun decodeBlockWithCorrection(encodedBlock: IntArray): Int {
    var result = bitsToInt(encodedBlock.slice(0..3).toIntArray())

    val controlSums = calculateControlSums(encodedBlock)
    val syndrome = bitsToInt(controlSums)

    result = result xor when(syndrome) {
        5 -> (1 shl 0)
        7 -> (1 shl 1)
        3 -> (1 shl 2)
        6 -> (1 shl 3)
        else -> 0
    }
    return result
}

fun decodeBlockWithoutCorrection(encodedBlock: IntArray): Int =
        bitsToInt(encodedBlock.slice(0..3).toIntArray())

fun decodeToString(encodedBlocks: List<IntArray>, decodeBlock: (encodedBlock: IntArray) -> Int): String {
    val sb = StringBuilder()
    for (i in encodedBlocks.indices step 2) {
        val asciiCode = decodeBlock(encodedBlocks[i]) +
                (decodeBlock(encodedBlocks[i + 1]) shl 4)
        sb.append(asciiCode.toChar())
    }
    return sb.toString()
}
