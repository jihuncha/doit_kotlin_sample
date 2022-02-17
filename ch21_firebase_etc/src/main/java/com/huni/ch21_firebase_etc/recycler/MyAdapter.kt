package com.huni.ch21_firebase_etc.recycler

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.huni.ch21_firebase_etc.MyApplication
import com.huni.ch21_firebase_etc.databinding.ItemMainBinding
import com.huni.ch21_firebase_etc.model.ItemData


class MyViewHolder(val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapter(val context: Context, val itemList: MutableList<ItemData>) :
    RecyclerView.Adapter<MyViewHolder>() {

    companion object {
        val TAG: String = MyAdapter::class.java.simpleName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyViewHolder(ItemMainBinding.inflate(layoutInflater))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = itemList.get(position)

        Log.d(TAG, "data - $data")

        holder.binding.run {
            itemEmailView.text = data.email
            itemDateView.text = data.date
            itemContentView.text = data.content
        }

        //스토리지 이미지 다운로드........................
        val imgRef = MyApplication.storage
            .reference
            .child("images/${data.docId}.jpg")

        Glide.with(context)
            .load(imgRef)
            .into(holder.binding.itemImageView)

    }
}

