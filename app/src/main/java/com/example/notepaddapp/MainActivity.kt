package com.example.notepaddapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainmenu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.note_add_menu){
            val action=MainPageFragmentDirections.actionMainPageFragmentToNoteDetailsFragment(0,"menudengeldim")
            //fragmentContainerView => activity_main inside navhost id
            Navigation.findNavController(this,R.id.fragmentContainerView).navigate(action)
        }
        return super.onOptionsItemSelected(item)
    }
}