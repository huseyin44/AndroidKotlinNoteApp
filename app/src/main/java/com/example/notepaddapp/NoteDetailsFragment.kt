package com.example.notepaddapp

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.notepaddapp.databinding.FragmentNoteDetailsBinding


class NoteDetailsFragment : Fragment() {
    private var _binding: FragmentNoteDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.savebutton.setOnClickListener {
            sqlsave(it)
        }
        binding.updatebutton.setOnClickListener {
            sqlUpdate(it)
        }
        binding.deletebutton.setOnClickListener {
            sqlDelete(it)
        }
        //argumenden gelen bilgileri kontrol
        arguments?.let {
            var gelenbilgi=NoteDetailsFragmentArgs.fromBundle(it).argBilgi
            if(gelenbilgi.equals("menudengeldim")){
                binding.notetitleText.setText("")
                binding.notedetailText.setText("")
                binding.savebutton.visibility=View.VISIBLE
                binding.updatebutton.visibility=View.INVISIBLE
                binding.deletebutton.visibility=View.INVISIBLE
            }
            else{
                binding.savebutton.visibility=View.INVISIBLE
                binding.updatebutton.visibility=View.VISIBLE
                binding.deletebutton.visibility=View.VISIBLE
                val argid=NoteDetailsFragmentArgs.fromBundle(it).argumanId
                context?.let {
                    try {
                        val db=it.openOrCreateDatabase("NoteDetailsDatabase",
                            Context.MODE_PRIVATE,null)
                        val cursor = db.rawQuery("SELECT * FROM notedetailsdatabase WHERE id=? ", arrayOf(argid.toString()))
                        val notedetailsTitle = cursor.getColumnIndex("notetitle")
                        val notedetail = cursor.getColumnIndex("notedetail")

                        while(cursor.moveToNext()){
                            binding.notetitleText.setText(cursor.getString(notedetailsTitle))
                            binding.notedetailText.setText(cursor.getString(notedetail))
                        }
                        cursor.close()
                    }
                    catch (e:Exception){
                        e.printStackTrace()
                    }
                }
            }
        }

    }
    private fun sqlsave(view: View){
        val noteTitle=binding.notetitleText.text.toString()
        val noteDetails=binding.notedetailText.text.toString()
        try {
            context?.let {
                val database=it.openOrCreateDatabase("NoteDetailsDatabase",
                    Context.MODE_PRIVATE,null)
                database.execSQL("CREATE TABLE IF NOT EXISTS notedetailsdatabase (id INTEGER PRIMARY KEY,notetitle VARCHAR,notedetail VARCHAR)")
                val sqlString="INSERT INTO notedetailsdatabase (notetitle,notedetail) VALUES (?,?)"
                val statement = database.compileStatement(sqlString)
                statement.bindString(1,noteTitle)
                statement.bindString(2,noteDetails)
                statement.execute()
            }

        }
        catch (e:Exception){
            e.printStackTrace()
        }
        val action=NoteDetailsFragmentDirections.actionNoteDetailsFragmentToMainPageFragment()
        Navigation.findNavController(view).navigate(action)
    }

    private fun sqlUpdate(view: View){
        val noteTitle=binding.notetitleText.text.toString()
        val noteDetails=binding.notedetailText.text.toString()
        val cv = ContentValues()
        arguments?.let {
            val argid=NoteDetailsFragmentArgs.fromBundle(it).argumanId
            try {
                context?.let { it ->
                    val db=it.openOrCreateDatabase("NoteDetailsDatabase", Context.MODE_PRIVATE,null)

                    val cursor = db.rawQuery("SELECT * FROM notedetailsdatabase WHERE id=? ", arrayOf(argid.toString()))
                    cv.put("notetitle",noteTitle);
                    cv.put("notedetail",noteDetails);
                    db.update("notedetailsdatabase", cv, "id = ?", arrayOf(argid.toString()));
                    cursor.close()
                }
            }
            catch (e:Exception){
                e.printStackTrace()
            }

            val action=NoteDetailsFragmentDirections.actionNoteDetailsFragmentToMainPageFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }
    private fun sqlDelete(view: View){
        arguments?.let {
            val argid=NoteDetailsFragmentArgs.fromBundle(it).argumanId
            try {
                context?.let { it ->
                    val db=it.openOrCreateDatabase("NoteDetailsDatabase", Context.MODE_PRIVATE,null)
                    val cursor = db.rawQuery("SELECT * FROM notedetailsdatabase WHERE id=? ", arrayOf(argid.toString()))
                    db.delete("notedetailsdatabase","id = ?",arrayOf(argid.toString()))
                    cursor.close()
                }
            }
            catch (e:Exception){
                e.printStackTrace()
            }

            val action=NoteDetailsFragmentDirections.actionNoteDetailsFragmentToMainPageFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }
}