import kotlin.random.Random

fun intToBits(x: Int, size: Int): IntArray =
        IntArray(size){ (x shr it) and 1 }

fun bitsToInt(bits: IntArray): Int =
        bits.foldIndexed(0) { index, acc, bit  ->
            acc + bit * (1 shl index)
        }

fun changeRandomBit(block: IntArray): IntArray {
    val randomPosition = Random.nextInt(0, 3)
    block[randomPosition] = block[randomPosition] xor 1
    return block
}

fun generateRandomString(length: Int): String {
    val allowedChars = ('a'..'z')  + ('0'..'9')
    return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
}
