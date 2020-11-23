package com.umarapps.citrudbitsinterviewtest.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.umarapps.citrudbitsinterviewtest.R
import com.umarapps.citrudbitsinterviewtest.databinding.PhotoListItemBinding
import com.umarapps.citrudbitsinterviewtest.models.AlbumsItem
import com.umarapps.citrudbitsinterviewtest.models.PhotosItem


import kotlinx.android.extensions.LayoutContainer

class PhotosListAdapter : RecyclerView.Adapter<PhotosListAdapter.ViewHolder>() {
    var onItemClick: ((PhotosItem) -> Unit)? = null
    private var mList: List<PhotosItem>? = listOf()

    fun setData(list: List<PhotosItem>) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: PhotoListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.photo_list_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBinding.photoItem = mList!![position]
        holder.containerView?.setOnClickListener {
            onItemClick?.invoke(mList!![position])
        }
    }

    class ViewHolder(var itemBinding: PhotoListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root), LayoutContainer {
        override val containerView: View?
            get() = itemBinding.root
    }
}