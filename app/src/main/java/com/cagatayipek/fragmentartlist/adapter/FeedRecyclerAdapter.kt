package com.cagatayipek.fragmentartlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cagatayipek.fragmentartlist.databinding.RecyclerRowBinding
import com.cagatayipek.fragmentartlist.model.Post
import com.squareup.picasso.Picasso

class FeedRecyclerAdapter(val postlist:ArrayList<Post>):RecyclerView.Adapter<FeedRecyclerAdapter.Postholder>() {
    class Postholder(val binding:RecyclerRowBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Postholder {
        val binding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Postholder(binding)

    }

    override fun getItemCount(): Int {
        return postlist.size
    }

    override fun onBindViewHolder(holder: Postholder, position: Int) {
        holder.binding.recyclerArtNameText.text=postlist.get(position).ArtName
        holder.binding.artistNameText.text=postlist.get(position).artistName
        holder.binding.recyclerEmailText.text=postlist.get(position).email
        holder.binding.recyclerviewArtYearText.text=postlist.get(position).Artyear
        Picasso.get().load(postlist.get(position).downloadUrl).into(holder.binding.recyclerviewImageView)

    }
}