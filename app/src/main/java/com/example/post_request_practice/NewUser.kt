package com.example.post_request_practice

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        val etName = findViewById<View>(R.id.etName) as EditText
        val etLocation = findViewById<View>(R.id.etLocation) as EditText
        val bSave = findViewById<View>(R.id.bSave) as Button
        val bView = findViewById<View>(R.id.bView) as Button

        bSave.setOnClickListener {
            var adding = Users.UserDetails(etName.text.toString(), etLocation.text.toString())

            addSingleuser(adding, onResult = {
                etName.setText("")
                etLocation.setText("")
                Toast.makeText(applicationContext, "Save Success!", Toast.LENGTH_SHORT).show()
            })
        }
        bView.setOnClickListener { viewusers() }
    }

    private fun addSingleuser(adding: Users.UserDetails, onResult: () -> Unit) {

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        if (apiInterface != null) {
            apiInterface.addUser(adding)?.enqueue(object : Callback<Users.UserDetails?> {

                override fun onResponse(call: Call<Users.UserDetails?>, response: Response<Users.UserDetails?>) {
                    onResult()
                }

                override fun onFailure(call: Call<Users.UserDetails?>, t: Throwable) {
                    onResult()
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