package com.example.post_request_practice

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var bAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bAdd = findViewById<View>(R.id.badd)
        bAdd.setOnClickListener { addnew() }

        val responseText = findViewById<TextView>(R.id.textView)

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        if (apiInterface != null) {

            apiInterface.doGetUsersList()?.enqueue(object :Callback<List<Users.UserDetails?>> {

                override fun onResponse(call: Call<List<Users.UserDetails?>>?, response: Response<List<Users.UserDetails?>>?) {
                    progressDialog.dismiss()
                    var displayResponse = ""
                  //  val resource: <list<Users.UserDetails?>> = response.body()

                    for (user in response?.body()!!) {
                         displayResponse = displayResponse +user?.name+ "\n"+user?.location + "\n"+"\n"
                    }
                    responseText.text = displayResponse
                }

                override fun onFailure(call: Call<List<Users.UserDetails?>>, t: Throwable) {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, ""+t.message, Toast.LENGTH_SHORT).show()
                }
            })

        }
    }
//view: android.view.View
    fun addnew() {
        intent = Intent(applicationContext, NewUser::class.java)
        startActivity(intent)
    }

}