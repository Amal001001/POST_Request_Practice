package com.example.post_request_practice

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateDeleteUser : AppCompatActivity() {

    private lateinit var etId: EditText
    private lateinit var etNameUpdate: EditText
    private lateinit var etLocationUpdate: EditText
    lateinit var bDelete: Button
    lateinit var bUpdate: Button
    private lateinit var bBack: Button
    private val apiInterface by lazy { APIClient().getClient()?.create(APIInterface::class.java) }

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete_user)

        etId = findViewById(R.id.etId)
        etNameUpdate = findViewById(R.id.etNameUpdate)
        etLocationUpdate = findViewById(R.id.etLocationUpdate)


        bDelete = findViewById(R.id.bDelete)
        bUpdate = findViewById(R.id.bUpdate)
        bDelete.setOnClickListener { deleteCelebrity() }
        bUpdate.setOnClickListener { updateUser() }

        bBack = findViewById(R.id.bUpdateBack)
        bBack.setOnClickListener {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")


    }

    private fun updateUser(){
        progressDialog.show()

        apiInterface?.updateUser(
          etId.text.toString().toInt(),
            user(
                etNameUpdate.text.toString(),
                etLocationUpdate.text.toString(),
                etId.text.toString().toInt(),

            ))?.enqueue(object: Callback<user> {
            override fun onResponse(call: Call<user>, response: Response<user>) {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "User Updated", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<user>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun deleteCelebrity(){
        progressDialog.show()
        apiInterface?.deleteUser(etId.text.toString().toInt())?.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "User Deleted", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_LONG).show()
            }

        })
    }
}