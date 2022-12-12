package com.app.ktorcrud.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.ktorcrud.BR
import com.app.ktorcrud.databinding.DashboardItemLayoutBinding
import com.app.ktorcrud.response.Data

class DashboardAdapter(
    var usersList: ArrayList<Data>,
    val context: Context
) :
    RecyclerView.Adapter<DashboardAdapter.BudgetViewHolder>() {

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
        holder.bind(usersList[position])
    }

    override fun getItemCount(): Int {
        return usersList.size
    }
}