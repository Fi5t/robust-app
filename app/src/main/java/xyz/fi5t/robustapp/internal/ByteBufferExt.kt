package xyz.fi5t.robustapp.internal

import java.nio.ByteBuffer
import java.util.*

fun ByteBuffer.wipeDirectBuffer(random: Random = Random()) {
    if (!this.isDirect) throw IllegalStateException("Only direct-allocated byte buffers can be meaningfully wiped")

    val arr = ByteArray(this.capacity())
    this.rewind()

    random.nextBytes(arr)
    this.put(arr)
}
