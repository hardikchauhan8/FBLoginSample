package com.sa.hardik.fbloginsample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.facebook.*
import com.facebook.internal.ImageRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var callbackManager = CallbackManager.Factory.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FacebookSdk.sdkInitialize(applicationContext)

        LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        Log.e("state", "success")
                        val request = GraphRequest.newMeRequest(
                                loginResult.accessToken
                        ) { `object`, response ->

                            Log.e("loginResult.accessToken", loginResult.accessToken.toString())
                            Log.e("response", response.rawResponse)
                            Log.e("response", `object`.toString())
                            ImageRequest.getProfilePictureUri("1824363800970345", 500, 500)
                        }
                        val parameters = Bundle()
                        parameters.putString("fields", "id,name,link,birthday,first_name,last_name,location")
                        request.parameters = parameters
                        request.executeAsync()
                    }

                    override fun onCancel() {
                        Log.e("state", "cancel")
                    }

                    override fun onError(exception: FacebookException) {
                        Log.e("state", exception.message)
                    }
                })

        loginButton.setOnClickListener {
            val permissions = mutableListOf("user_photos", "email",
                    "user_birthday", "public_profile")
            LoginManager.getInstance().logInWithReadPermissions(this, permissions)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
