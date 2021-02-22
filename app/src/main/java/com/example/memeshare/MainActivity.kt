package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var currenturl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
    }
    fun loadmeme(){
        val pb=findViewById<ProgressBar>(R.id.pb_load)
        pb.visibility=View.VISIBLE

        val queue = Volley.newRequestQueue(this)
        var url = " https://meme-api.herokuapp.com/gimme"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->
                    currenturl=response.getString("url")
                    val iv=findViewById<ImageView>(R.id.iv_meme)
                    Glide.with(this).load(currenturl).listener(object :RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            pb.visibility=View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {

                            pb.visibility=View.GONE
                            return false
                        }
                    }).into(iv)
                },
                { error ->
                     Toast.makeText(this,"error in fetching",Toast.LENGTH_LONG).show()
                })

        queue.add(jsonObjectRequest)


    }
    fun share(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey Check this cool meme out $currenturl")
        val chooser=Intent.createChooser(intent,"Share this meme Using")
        startActivity(chooser)
    }
    fun next(view: View) {
        loadmeme()
    }
}