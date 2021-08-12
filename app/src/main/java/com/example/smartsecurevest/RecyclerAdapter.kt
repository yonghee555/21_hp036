package com.example.smartsecurevest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val items: ArrayList<WorkerItem>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener { it ->
            Toast.makeText(it.context, "Clicked: ${item.WorkerName}", Toast.LENGTH_SHORT).show()
        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerAdapter.ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return RecyclerAdapter.ViewHolder(inflatedView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private var view: View = v
        private val workerName = view.findViewById<TextView>(R.id.workerName)
        private val workerTel = view.findViewById<TextView>(R.id.workerTel)
        fun bind(listener: View.OnClickListener, item: WorkerItem) {
            //view.thumbnail.setImageDrawable(item.image)
            workerName.text = item.WorkerName
            workerTel.text = item.WorkerTel
            view.setOnClickListener(listener)
        }
    }
}