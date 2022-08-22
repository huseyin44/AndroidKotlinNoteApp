package com.example.notepaddapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class ListNoteDetailsAdapter(val noteTitleList: ArrayList<String>,val noteDetailsList: ArrayList<String>, val noteIdList : ArrayList<Int>) : RecyclerView.Adapter<ListNoteDetailsAdapter.NoteDetailsHolder>() {

    class NoteDetailsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteDetailsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row,parent,false)
        return NoteDetailsHolder(view)
    }

    override fun getItemCount(): Int {
        return noteTitleList.size
    }

    override fun onBindViewHolder(holder: NoteDetailsHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.recyclerview_title).text=noteTitleList[position]
        holder.itemView.setOnClickListener {
            val action = MainPageFragmentDirections.actionMainPageFragmentToNoteDetailsFragment(noteIdList[position],"recyclerdangeldim")
            Navigation.findNavController(it).navigate(action)
        }
    }
}