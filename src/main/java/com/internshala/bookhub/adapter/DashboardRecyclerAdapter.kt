package com.internshala.bookhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.internshala.bookhub.R
import com.squareup.picasso.Picasso
import model.Book

class DashboardRecyclerAdapter(val context:Context,val itemlist:ArrayList<Book>):RecyclerView.Adapter<DashboardRecyclerAdapter.DashBoardViewHolder>() {
    class DashBoardViewHolder(view: View):RecyclerView.ViewHolder(view){
        val txtBookName:TextView=view.findViewById(R.id.txtBookName)
        val txtBookAuthor:TextView=view.findViewById(R.id.txtBookAuthor)
        val txtBookPrice:TextView=view.findViewById(R.id.txtBookPrize)
        val txtBookRating:TextView=view.findViewById(R.id.txtBookRating)
        val imgBookImage:ImageView=view.findViewById(R.id.imgBookName)
        val content:LinearLayout=view.findViewById(R.id.Content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashBoardViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row,parent,false)
        return DashBoardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }

    override fun onBindViewHolder(holder: DashBoardViewHolder, position: Int) {
        val book=itemlist[position]
        holder.txtBookName.text=book.bookName
        holder.txtBookAuthor.text=book.bookAuthor
        holder.txtBookPrice.text=book.bookPrice
        holder.txtBookRating.text=book.bookRating
       // holder.imgBookImage.setImageResource(book.bookImage)
        Picasso.get().load(book.bookImage).into(holder.imgBookImage);
        holder.content.setOnClickListener{
            Toast.makeText(context,"clicked on ${holder.txtBookName.text}",Toast.LENGTH_SHORT).show()
        }

    }
}