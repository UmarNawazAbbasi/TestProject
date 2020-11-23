package com.umarapps.citrudbitsinterviewtest.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.umarapps.citrudbitsinterviewtest.R
import com.umarapps.citrudbitsinterviewtest.databinding.UserListItemBinding
import com.umarapps.citrudbitsinterviewtest.models.UsersItem
import kotlinx.android.extensions.LayoutContainer

class UsersListAdapter : RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {

    var onItemClick: ((UsersItem) -> Unit)? = null
    private var mList: List<UsersItem>? = listOf()

    fun setData(list: List<UsersItem>) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: UserListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.user_list_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBinding.usersItem = mList!![position]
        holder.containerView?.setOnClickListener {
            onItemClick?.invoke(mList!![position])
        }
    }

    class ViewHolder(var itemBinding: UserListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root), LayoutContainer {
        override val containerView: View?
            get() = itemBinding.root

    }

}