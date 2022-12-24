package xyz.fi5t.robustapp.internal

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.nio.ByteBuffer

class ByteBufferAdapter : TypeAdapter<ByteBuffer>() {
    override fun write(out: JsonWriter?, value: ByteBuffer?) {
        TODO("Not yet implemented")
    }

    override fun read(`in`: JsonReader): ByteBuffer {
        return `in`.nextString().let {
            ByteBuffer.allocateDirect(it.length).put(it.encodeToByteArray())
        }
    }
}
