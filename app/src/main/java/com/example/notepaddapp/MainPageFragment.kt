package com.example.notepaddapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notepaddapp.databinding.FragmentMainPageBinding
import java.lang.Exception

class MainPageFragment : Fragment() {
    private var _binding: FragmentMainPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var listeAdapter : ListNoteDetailsAdapter

    var noteIdList = ArrayList<Int>()
    var noteTitleList = ArrayList<String>()
    var noteDetailsList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainPageBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeAdapter = ListNoteDetailsAdapter(noteTitleList,noteDetailsList,noteIdList)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = listeAdapter


        sqldataget()
    }
    fun sqldataget(){

        try {
            activity?.let {
                val database = it.openOrCreateDatabase("NoteDetailsDatabase", Context.MODE_PRIVATE,null)

                val cursor = database.rawQuery("SELECT * FROM notedetailsdatabase",null)
                val notedetailsId = cursor.getColumnIndex("id")
                val notedetailsTitle = cursor.getColumnIndex("notetitle")
                val notedetail = cursor.getColumnIndex("notedetail")

                noteIdList.clear()
                noteTitleList.clear()
                noteDetailsList.clear()

                while(cursor.moveToNext()){

                    noteIdList.add(cursor.getInt(notedetailsId))
                    noteTitleList.add(cursor.getString(notedetailsTitle))
                    noteDetailsList.add(cursor.getString(notedetail))

                }

                listeAdapter.notifyDataSetChanged()

                cursor.close()
            }
        }catch (e: Exception){
        }
    }
}