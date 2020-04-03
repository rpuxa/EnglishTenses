package english.tenses.practice.model

import android.content.Context
import java.io.ObjectInputStream

class AssetsLoader(private val context: Context) {

    val newIds: Array<IntArray> by lazy {
       ObjectInputStream(context.assets.open("NewIds")).use {
           it.readObject() as Array<IntArray>
       }
    }
}