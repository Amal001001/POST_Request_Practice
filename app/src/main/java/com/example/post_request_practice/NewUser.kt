package com.example.post_request_practice

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewUser : AppCompatActivity() {

    lateinit var etName: EditText
    lateinit var etLocation: EditText
    lateinit var bSave: Button
    lateinit var bView: Button

    private lateinit var progressDialog: ProgressDialog

    private lateinit var existingUsers: ArrayList<String>

    private val apiInterface by lazy { APIClient().getClient()?.create(APIInterface::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        existingUsers = intent.extras!!.getStringArrayList("userNames")!!

        etName = findViewById<View>(R.id.etName) as EditText
        etLocation = findViewById<View>(R.id.etLocation) as EditText
        bSave = findViewById<View>(R.id.bSave) as Button
        bView = findViewById<View>(R.id.bView) as Button

        bSave.setOnClickListener {
            if(etName.text.isNotEmpty() && etLocation.text.isNotEmpty()){
                addSingleuser()
            }else{
                Toast.makeText(this, "One or more fields is empty", Toast.LENGTH_LONG).show()
            }
//            var adding = Users.UserDetails(etName.text.toString(), etLocation.text.toString(),0)
//
//            addSingleuser(adding, onResult = {
//                etName.setText("")
//                etLocation.setText("")
//                Toast.makeText(applicationContext, "Save Success!", Toast.LENGTH_SHORT).show()
//            })
        }
        bView.setOnClickListener { viewusers() }
    }

    private fun addSingleuser() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.show()

        if (apiInterface != null) {
           apiInterface!!.addUser(
               user
               (etName.text.toString().uppercase(),
               etLocation.text.toString(),
               0)
            ).enqueue(object: Callback<user> {

                override fun onResponse(call: Call<user>, response: Response<user>) {
                    progressDialog.dismiss()
                    if(!existingUsers.contains(etName.text.toString().lowercase())){
                        intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@NewUser, "Celebrity Already Exists", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<user>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    fun viewusers() {
        intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}