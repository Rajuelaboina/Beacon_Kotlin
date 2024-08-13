package com.phycare.residentbeacon_kotlin.ui.login

import android.util.Log
import junit.framework.TestCase.assertNotNull
import net.bytebuddy.matcher.ElementMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
//@RunWith(JUnit4::class)
class LoginActivityTest  {
    @Mock
    lateinit var loginActivity: LoginActivity
    @Mock
    lateinit var loginViewModel: LoginViewModel
    @Mock
    lateinit var  loginUser : UserLogin
    val FAKE_STRING: String = "Login was successful"

    @Before
    fun setUp(){
        loginActivity = LoginActivity()
        loginViewModel = LoginViewModel(loginActivity)
        loginUser = UserLogin("","")
    }
    @Test
    fun testValidUsername(){
   /*  //  val result = loginUser.isValidUser()
        //val result = loginUser.isValidUserName()
        val result2 = loginUser.isVaildEmail()

         // println("is not username>>> :  $result")
       // Log.e("Testing" ,"username: "+ result)
       // println("username: "+ result2)
     //   assertThat("result", result)
        assertThat("result2", result2)*/
      //  val loginCode: Int = loginUser.isValidUser()
      //  Log.e("Testing" ,"username: "+ loginCode)
        //assertThat("result2", loginUser.isVaildEmail())
      // val result =  loginUser.isValidUser()
      //  Log.e("Testing" ,"username: "+ result)
        loginActivity.isValidUser("","")
        assertThat("result2", loginActivity.isValidUser("",""))
    }

    /*@Test
    fun validPassword(){
        val result =  loginUser.isVaildEmail()
        Log.e("Testing" ,"username: "+ result)
        assertNotNull(LoginActivity::class)
    }*/
}