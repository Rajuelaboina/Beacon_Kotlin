package com.phycare.residentbeacon_kotlin.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.phycare.residentbeacon_kotlin.MainActivity
import com.phycare.residentbeacon_kotlin.databinding.ActivityLoginBinding
import com.phycare.residentbeacon_kotlin.utils.SharedPrefManager

class LoginActivity : AppCompatActivity(), LoginResponseListener {
    lateinit var binding: ActivityLoginBinding
    lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel = LoginViewModel(this)
        binding.lifecycleOwner = this
        binding.setViewModel(loginViewModel)
        binding.executePendingBindings()
        binding.progressBarLogin.visibility = View.GONE
        binding.checkBox2.isChecked = false

        // remember password
        val userNamePassword: UserLogin = SharedPrefManager.getInstance(applicationContext)!!.userNamePassword
        if (userNamePassword.email != null) {
            binding.emailEditTextText.setText(userNamePassword.email)
            binding.passwordEditTextText.setText(userNamePassword.password)
            binding.checkBox2.isChecked = true
        }

        binding.loginBtn.setOnClickListener {
            isValidUser(binding.emailEditTextText.text.toString(),binding.passwordEditTextText.text.toString())
        }


    }

    fun isValidUser(username: String, password: String): Boolean {
        var bb : Boolean
        if (username.isNotEmpty() && password.isNotEmpty()){
            bb = true
        }else{
            bb = false
        }
        return bb
    }

    override fun onError(loginCode: Int) {
        if (loginCode == 0) {
            binding.textInputLayout.error = "Email is required !"
            binding.textInputLayout.requestFocus()
        } else if (loginCode == 1) {
            binding.textInputLayout.error = "Invalid Email !"
            binding.textInputLayout.requestFocus()
        } else if (loginCode == 2) {
            binding.textInputLayout2.isErrorEnabled = true
            binding.textInputLayout2.error = "Password is required !"
            binding.textInputLayout2.requestFocus()
        }
    }

    override fun onSuccess(user: UserLogin?) {
        binding.progressBarLogin.visibility = View.VISIBLE
       loginViewModel.getLoginDetails(binding.emailEditTextText.text.toString().trim(),binding.passwordEditTextText.getText().toString().trim())
        loginViewModel.data.observe(this) { responseModel -> // if (responseModel.getCode() >= 400 ){
          /*  Snackbar.make(
                binding.loginBtn,
                responseModel.message *//*+ "\n" + responseModel.code*//*,
                Snackbar.LENGTH_LONG
            ).show()*/
            // }
            binding.progressBarLogin.visibility = View.GONE
        }
        loginViewModel.getUserLogin()?.observe(this){ userLoginList ->

            //Log.e("LoginMain>>>","List"+userLogins.size());
            binding.progressBarLogin.visibility = View.GONE
            var name = ""
            var fullName = ""
            try {
               if (userLoginList!!.size > 0) {
                    name = userLoginList!![0].userName.toString().trim()
                    fullName = userLoginList!![0].userFullName.toString().trim()
                    SharedPrefManager.getInstance(applicationContext)?.insertData(UserData(name, fullName))
                    if (binding.checkBox2.isChecked) {
                        // Log.e("checkkkk",""+checkBoxRemember.isChecked());
                        SharedPrefManager.getInstance(applicationContext)?.rememberUserNamePass(
                            UserLogin(
                                binding.emailEditTextText.getText().toString(),
                                binding.passwordEditTextText.getText().toString()
                            )
                        )
                    } else {
                        SharedPrefManager.getInstance(applicationContext)?.removeUserNamePassword()
                    }
                // Toast.makeText(getApplicationContext(),"success !",Toast.LENGTH_LONG).show();
                // Toast.makeText(getApplicationContext(),"success !",Toast.LENGTH_LONG).show();
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
               } else  {
                      Toast.makeText(applicationContext, "Invalid values!", Toast.LENGTH_LONG).show()
               }
            }catch (e:Exception){
                Toast.makeText(applicationContext,e.message , Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onRefreshError(id: Int) {
        if (id == -1) {
            binding.textInputLayout.isErrorEnabled = false
        } else {
            binding.textInputLayout2.error = ""
        }
    }
}