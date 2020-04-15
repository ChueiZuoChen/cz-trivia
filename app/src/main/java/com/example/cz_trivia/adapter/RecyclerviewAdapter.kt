package com.example.cz_trivia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cz_trivia.R
import com.example.cz_trivia.databinding.ItemViewBinding
import com.example.cz_trivia.db.Student
import com.example.cz_trivia.generated.callback.OnClickListener

class RecyclerviewAdapter(private val student:List<Student>,private val clickListener: (Student)->Unit): RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding:ItemViewBinding =
            DataBindingUtil.inflate(layoutInflater,R.layout.item_view,parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return student.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding(student[position],clickListener)
    }

}

class MyViewHolder(val binding:ItemViewBinding):RecyclerView.ViewHolder(binding.root){
    fun binding(student: Student,clickListener: (Student)->Unit){
        binding.nameItemview.text = student.name
        binding.emailItemview.text = student.email
        binding.listItemLayout.setOnClickListener {
            clickListener(student)
        }
    }
}