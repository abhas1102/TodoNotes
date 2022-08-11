package com.example.todonotesapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotesapp.R
import com.example.todonotesapp.adapter.BlogAdapter
import com.example.todonotesapp.model.Data
import com.example.todonotesapp.network.BlogsInterface
import com.example.todonotesapp.network.BlogsService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BlogActivity : AppCompatActivity() {
    lateinit var recyclerViewBlogs:RecyclerView
    var blogList: List<Data> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)
        bindViews()


        lifecycleScope.launch {
            val result = BlogsService.blogInstance.getBlogs().body()
           Log.d("BlogActivity",""+result!!.data)
           blogList += result.data
            Log.d("BlogActivity",""+result.data.size)

                setUpRecyclerView()


        }


    }



    private fun bindViews() {
        recyclerViewBlogs = findViewById(R.id.recyclerViewBlogs)
    }

    private fun setUpRecyclerView(){
        val blogAdapter = BlogAdapter(blogList)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewBlogs.layoutManager = linearLayoutManager
        recyclerViewBlogs.adapter = blogAdapter
    }


}