package ru.rpuxa.englishtenses.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ru.rpuxa.englishtenses.inflate

abstract class BaseAdapter<T, Binding : ViewBinding> :
    RecyclerView.Adapter<BaseAdapter<T, Binding>.BaseViewHolder>() {

    private var collection: List<T> = emptyList()

    abstract inner class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun Context.bind(item: T)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        with(holder) {
            itemView.context.bind(collection[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = create(inflater, parent)
        val view = binding.root
        return view.getViewHolder(binding)
    }


    override fun getItemCount(): Int = collection.size

    fun submitList(list: List<T>) {
        collection = list
        notifyDataSetChanged()
    }

    protected abstract fun create(inflater: LayoutInflater, parent: ViewGroup): Binding

    protected abstract fun View.getViewHolder(binding: Binding): BaseViewHolder

    protected inline fun View.bind(crossinline block: Context.(T) -> Unit) =
        object : BaseViewHolder(this) {
            override fun Context.bind(item: T) {
                block(item)
            }
        }
}