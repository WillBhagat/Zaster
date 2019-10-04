package com.example.zaster

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.zaster.models.Response
import com.example.zaster.models.ResponseCode
import com.example.zaster.repositories.NetworkTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences
import android.support.v4.app.FragmentActivity


class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var button: Button
    private lateinit var editText: EditText
    private val networkTask = NetworkTask()
    private lateinit var response : Response
    private lateinit var bearer : String
    private lateinit var email : String
    private lateinit var name : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settings = getSharedPreferences("mSharedPrefs", Context.MODE_PRIVATE)

        // Writing data to SharedPreferences
        // I assume this is the requirement here as no database has been provided
        val editor = settings.edit()
        editor.putString("email", "willbhagat@hotmail.co.uk")
        editor.putString("name", "Will Bhagat")
        editor.commit()

        // Reading from SharedPreferences
        name = settings.getString("name", "")
        email = settings.getString("email", "")

        textView = findViewById(R.id.welcomeText)
        textView.text = getString(R.string.welcome_message, name)


        editText = findViewById(R.id.editText)

        button = findViewById(R.id.loginButton)
        button.setOnClickListener {
            runNetwork()
            when (response.responseCode) {
                ResponseCode.VALID -> {
                    bearer = response.responseMessage
                }
                ResponseCode.INVALID -> {
                    Toast.makeText(this, R.string.incorrect_password, Toast.LENGTH_LONG).show()
                }
                ResponseCode.INTERNAL -> {
                    Toast.makeText(this, R.string.internal_error, Toast.LENGTH_LONG).show()
                    Log.d("TAG", response.responseMessage)
                }
            }
        }
    }

    private fun runNetwork() = runBlocking {
        response = withContext(Dispatchers.Default) {networkTask.runTask(editText.text.toString(), email)}
    }

}
