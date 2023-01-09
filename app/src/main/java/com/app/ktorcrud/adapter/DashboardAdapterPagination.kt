package com.app.ktorcrud.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.ktorcrud.BR
import com.app.ktorcrud.databinding.DashboardItemLayoutBinding
import com.app.ktorcrud.response.Data

class DashboardAdapterPagination(
    val context: Context
) : PagingDataAdapter<Data, DashboardAdapterPagination.BudgetViewHolder>(
    DIFF_CALLBACK
) {

    class BudgetViewHolder(val itemLayoutBinding: DashboardItemLayoutBinding) :
        RecyclerView.ViewHolder(itemLayoutBinding.root) {
        fun bind(usersList: Data) {
            itemLayoutBinding.setVariable(BR.users, usersList)
            itemLayoutBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: DashboardItemLayoutBinding =
            DashboardItemLayoutBinding.inflate(layoutInflater, parent, false)
        return BudgetViewHolder(itemBinding)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Data> =
            object : DiffUtil.ItemCallback<Data>() {
                override fun areItemsTheSame(
                    oldItem: Data,
                    newItem: Data
                ): Boolean {
                    return oldItem.id === newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Data,
                    newItem: Data
                ): Boolean {
                    return oldItem.id == newItem.id
                }
            }
    }
}