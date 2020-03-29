package english.tenses.practice.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

abstract class BaseMultiItemAdapter2 :
    RecyclerView.Adapter<BaseMultiItemAdapter2.BaseViewHolder>() {

    abstract fun Builder.build()

    private var collection = emptyList<Any>()
    private val items = Builder().apply { build() }.items as List<Item<Any, ViewBinding>>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val item = items[viewType]
        return BaseViewHolder(item.binding(LayoutInflater.from(parent.context), parent))
    }

    override fun getItemCount() = collection.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = getItem(position)
        item.bind.invoke(holder.binding.root.context, collection[position], holder.binding)
    }

    override fun getItemViewType(position: Int): Int {
        val item = collection[position]
        val index = items.indexOfFirst {
            it.clazz.isInstance(item)
        }
        if (index == -1) error("Unknown type ${item.javaClass}")
        return index
    }

    fun getItem(pos: Int): Item<Any, ViewBinding> {
        val value = collection[pos]
        return items.first {
            it.clazz.isInstance(value)
        }
    }


    fun submitList(list: List<Any>) {
        collection = list
        notifyDataSetChanged()
    }

    class BaseViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

    class Builder {
        val items = ArrayList<Item<*, *>>()

        inline fun <reified T : Any, Binding : ViewBinding> item(
            block: Item<T, Binding>.() -> Unit
        ) {
            val item = Item<T, Binding>(T::class)
            item.block()
            items += item
        }

    }

    class Item<T : Any, Binding : ViewBinding>(val clazz: KClass<T>) {
        lateinit var binding: (LayoutInflater, ViewGroup) -> Binding
        lateinit var bind: Context.(T, Binding) -> Unit
    }
}

