package com.example.latihan_multipart.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.latihan_multipart.databinding.ActivityRegisterBinding
import com.example.latihan_multipart.model.ResponseRegisterUser

class AdapterRegister(var dataRegister : List<ResponseRegisterUser>) : RecyclerView.Adapter<AdapterRegister.ViewHolder>() {
    class ViewHolder(val binding : ActivityRegisterBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ActivityRegisterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvName.text = dataRegister[position].fullName
//        holder.binding.tvEmail.text = dataRegister[position].email
//        holder.binding.tvPhone.text = dataRegister[position].phoneNumber.toString()
//        holder.binding.tvAddress.text = dataRegister[position].address
//        holder.binding.tvCity.text = dataRegister[position].city
//        Glide.with(holder.itemView).load(dataRegister[position].imageUrl).into(holder.binding.ivImage)
    }

    override fun getItemCount(): Int {
        return dataRegister.size
    }

}
}