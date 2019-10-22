package com.example.zaster

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.content.Context
import android.content.SharedPreferences
import com.example.zaster.models.LoginInfo
import com.example.zaster.models.ResponseMessage
import com.example.zaster.repositories.PostLoginInfo
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "MyActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var loginButton: Button
    private lateinit var editText: EditText

    private lateinit var token : String
    private lateinit var email : String
    private lateinit var name : String

    @Inject lateinit var postLoginInfo : PostLoginInfo
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerNetworkComponent.create().inject(this)

        writeSharedPrefs()
        readSharedPrefs()
        retrieveLoginInfo()
        setupButtons()
    }

    private fun writeSharedPrefs()
    {
        sharedPreferences = getSharedPreferences("mSharedPrefs", Context.MODE_PRIVATE)
        // Writing data to SharedPreferences
        // I assume this is the requirement here as no database has been provided
        val editor = sharedPreferences.edit()
        editor.putString("email", "willbhagat@hotmail.co.uk")
        editor.putString("name", "Will Bhagat")
        editor.commit()
    }

    private fun readSharedPrefs()
    {
        // Reading from SharedPreferences
        name = sharedPreferences.getString("name", "")
        email = sharedPreferences.getString("email", "")
    }

    private fun retrieveLoginInfo()
    {
        textView = findViewById(R.id.welcomeText)
        textView.text = getString(R.string.welcome_message, name)
        editText = findViewById(R.id.editText)
        loginButton = findViewById(R.id.loginButton)
    }
    private fun setupButtons()
    {
        loginButton.setOnClickListener {
            loginButton.isEnabled = false
            handleMessage(postLoginInfo.postLoginDetails(LoginInfo(email, editText.text.toString())))
            loginButton.isEnabled = true
        }
    }


    fun handleMessage(responseMessage: ResponseMessage) = when(responseMessage.code)
    {
        200 -> {
            token = responseMessage.message
        }
        401 -> {
            Toast.makeText(this, R.string.incorrect_password, Toast.LENGTH_LONG).show()
        }
        500, 0 -> {
            Toast.makeText(this, R.string.internal_error, Toast.LENGTH_LONG).show()
            Log.d(TAG, responseMessage.message)
        }
        else -> {
            Log.d(TAG, "Unexpected error code")
        }
    }
}
