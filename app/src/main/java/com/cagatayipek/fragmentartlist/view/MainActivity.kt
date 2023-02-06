package com.cagatayipek.fragmentartlist.view

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.cagatayipek.fragmentartlist.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuinflater=menuInflater
        menuinflater.inflate(R.menu.post_menu,menu)
        menu?.findItem(R.id.addpost)?.setVisible(false)
        menu?.findItem(R.id.logout)?.setVisible(false)
        return super.onCreateOptionsMenu(menu)
    }


}