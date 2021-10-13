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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var rvMain: RecyclerView
    private lateinit var rvAdapter: RVAdapter
    lateinit var bAdd: Button
    lateinit var bUpdateDeleteUser: Button

    private lateinit var users: ArrayList<user>

    private val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        users = arrayListOf()

        rvMain = findViewById(R.id.rvMain)
        rvAdapter = RVAdapter(users)
        rvMain.adapter = rvAdapter
        rvMain.layoutManager = LinearLayoutManager(this)

        bAdd = findViewById(R.id.badd)
        bAdd.setOnClickListener { addnew() }

        bUpdateDeleteUser = findViewById(R.id.bUpdateDeleteUser)
        bUpdateDeleteUser.setOnClickListener { UpdateDeleteUser() }

        //val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        CoroutineScope(IO).launch {
            withContext(Main){
                getUsers()
            }
        }
    }
        private fun getUsers(){
        if (apiInterface != null) {
          //  val responseText = findViewById<TextView>(R.id.textView)
            apiInterface.doGetUsersList().enqueue(object :Callback<ArrayList<user>> {

                override fun onResponse(call: Call<ArrayList<user>>, response: Response<ArrayList<user>>) {
                    progressDialog.dismiss()
                   // var displayResponse = ""
                    users = response.body()!!

//                    displayResponse = users.a
//                    responseText.text = displayResponse
                    rvAdapter.update(users)
//                    for (user in response.body()!!) {
//                         displayResponse = displayResponse +user?.name+ "\n"+user?.location + "\n"+"\n"
//                    }

                }

                override fun onFailure(call: Call<ArrayList<user>>, t: Throwable) {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, ""+t.message, Toast.LENGTH_SHORT).show()
                }
            })

        }
    }

    fun addnew() {
        intent = Intent(applicationContext, NewUser::class.java)
        val userNames = arrayListOf<String>()
        for(user in users){
            userNames.add(user.name.lowercase())
        }
        intent.putExtra("userNames", userNames)
        startActivity(intent)
    }

    fun UpdateDeleteUser() {
        intent = Intent(applicationContext, UpdateDeleteUser::class.java)
        startActivity(intent)
    }

}