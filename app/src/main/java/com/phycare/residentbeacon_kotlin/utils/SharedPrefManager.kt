package com.phycare.residentbeacon_kotlin.utils

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.phycare.residentbeacon_kotlin.ui.login.LoginActivity
import com.phycare.residentbeacon_kotlin.ui.login.UserData
import com.phycare.residentbeacon_kotlin.ui.login.UserLogin

class SharedPrefManager private constructor(private val mContext: Context) {
    //insert user data
    fun insertData(userProfileModal: UserData) {
        val sharedPreferences: SharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        //editor.putInt(USER_ID,userProfileModal.getId());
        editor.putString(USER_NAME, userProfileModal.userName)
        editor.putString(USER_FullName, userProfileModal.userFullName)
        //editor.putString(LASTLOGGEDINDATE, userProfileModal.getLastLoggedinDate());
        editor.commit()
    }

    val userData: UserData
        //get the user data
        get() {
            val sharedPreferences: SharedPreferences = mContext.getSharedPreferences( SHARED_PREF_NAME,Context.MODE_PRIVATE)
            //sharedPreferences.getString(LASTLOGGEDINDATE,null));
            return UserData( //sharedPreferences.getInt(USER_ID,0),
                sharedPreferences.getString(USER_NAME, null),
                sharedPreferences.getString(USER_FullName, null)
            )
        }
    val isLoggedIn: Boolean
        get() {
            val sharedPreferences: SharedPreferences =
                mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            /* if (sharedPreferences.getInt(USER_NAME, 0) != 0){   //USER_ID
            return true;
        }*/return if (sharedPreferences.getString(USER_NAME, null) != null) {   //USER_ID
                true
            } else false
        }
    val isLoggedOut: Unit
        get() {
            val sharedPreferences: SharedPreferences =
                mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            // editor.clear();
            editor.remove(USER_NAME) // USER_ID
            editor.apply()
            val notificationManager: NotificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll()
            val intent = Intent(mContext, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent)
        }

    fun rememberUserNamePass(rememberUsernamePass: UserLogin) {
        val sharedPreferences: SharedPreferences =
            mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("RE_USERNAME", rememberUsernamePass.email)
        editor.putString("RE_PASSWORD", rememberUsernamePass.password)
        //editor.apply();
        editor.commit()
    }

    val userNamePassword: UserLogin
        get() {
            val sharedPreferences: SharedPreferences = mContext.getSharedPreferences(
                SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
            return UserLogin(
                sharedPreferences.getString("RE_USERNAME", null),
                sharedPreferences.getString("RE_PASSWORD", null)
            )
        }

    fun removeUserNamePassword() {
        val sharedPreferences: SharedPreferences =
            mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        // editor.clear();
        editor.remove("RE_USERNAME")
        editor.remove("RE_PASSWORD")
        editor.apply()
    }

    companion object {
        var sharedPrefManager: SharedPrefManager? = null
        private const val SHARED_PREF_NAME = "userProfile"
        private const val USER_ID = "userId"
        private const val USER_NAME = "userName"
        private const val USER_Email = "userEmail"
        private const val USER_MOBILE = "userMobile"
        private const val USER_FullName = "userMobile"
        @Synchronized
        fun getInstance(context: Context): SharedPrefManager? {
            if (sharedPrefManager == null) {
                sharedPrefManager = SharedPrefManager(context)
            }
            return sharedPrefManager
        }
    }
}
