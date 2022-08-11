package com.example.todonotesapp.model

data class Blog(val status_code:String, val message:String,val data:List<Data>)

data class Data(val title: String, val description:String, val img_url:String, val author: String, val blog_url:String, val published_at:String)
