package com.umarapps.citrudbitsinterviewtest.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.umarapps.citrudbitsinterviewtest.R
import com.umarapps.citrudbitsinterviewtest.databinding.AlbumListItemBinding

import com.umarapps.citrudbitsinterviewtest.models.AlbumsItem
import com.umarapps.citrudbitsinterviewtest.models.UsersItem
import kotlinx.android.extensions.LayoutContainer

class AlbumsListAdapter : RecyclerView.Adapter<AlbumsListAdapter.ViewHolder>() {

    var onItemClick: ((AlbumsItem) -> Unit)? = null
    private var mList: List<AlbumsItem>? = listOf()

    fun setData(list: List<AlbumsItem>) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: AlbumListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.album_list_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBinding.albumItem = mList!![position]
        holder.containerView?.setOnClickListener {
            onItemClick?.invoke(mList!![position])
        }
    }

    class ViewHolder(var itemBinding: AlbumListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root), LayoutContainer {
        override val containerView: View?
            get() = itemBinding.root
    }
}