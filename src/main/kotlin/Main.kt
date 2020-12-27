import java.lang.IllegalArgumentException

fun myPrint(x: Int) {
    println(x)
}

fun main() {
    print("Enter the length of the string: ")
    val length: String = readLine() ?: throw IllegalArgumentException()
    val randomString = generateRandomString(length.toInt())

    println("Random string of length $length: $randomString")

    val blocks = splitIntoBlocks(randomString)

    val encodedBlocks = blocks.toMutableList()
    encodedBlocks.replaceAll { encodeBlock(it) }
    encodedBlocks.forEach { changeRandomBit((it)) }
    println("After changing random bits: ${decodeToString(encodedBlocks, ::decodeBlockWithoutCorrection)}")

    val decodedString = decodeToString(encodedBlocks, ::decodeBlockWithCorrection)
    println("Decoded string: $decodedString")
}
