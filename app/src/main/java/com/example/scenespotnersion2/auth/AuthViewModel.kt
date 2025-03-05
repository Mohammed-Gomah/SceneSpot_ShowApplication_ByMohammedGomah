package com.example.scenespotnersion2.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val auth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<FirebaseUser?>()
    val authState: LiveData<FirebaseUser?> get() = _authState

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _resetPasswordStatus = MutableLiveData<Boolean>()
    val resetPasswordStatus: LiveData<Boolean> get() = _resetPasswordStatus

    init {
        checkIfLoggedIn()
    }

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user?.isEmailVerified == true) {
                        _authState.value = user
                    } else {
                        _errorMessage.value = "يرجى تأكيد بريدك الإلكتروني أولًا."
                        user?.sendEmailVerification() // إعادة إرسال التحقق
                        auth.signOut() // تسجيل خروج حتى لا يتم الدخول بدون تحقق
                    }
                } else {
                    _errorMessage.value = task.exception?.message
                }
            }
    }

    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                    _authState.value = user
                    _errorMessage.value = "Registered Successfully , check your email"
                } else {
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        _errorMessage.value = "Already Registered"
                    } else {
                        _errorMessage.value = task.exception?.message
                    }
                }
            }
    }

    fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                _resetPasswordStatus.value = task.isSuccessful
                if (!task.isSuccessful) {
                    _errorMessage.value = task.exception?.message
                }
            }
    }

    fun setUsername(username: String) {
        val user = auth.currentUser ?: return
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .build()

        user.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    _errorMessage.value = task.exception?.message
                }
            }
    }

    fun logout() {
        auth.signOut()
        _authState.value = null
    }

    private fun checkIfLoggedIn() {
        _authState.value = auth.currentUser?.takeIf { it.isEmailVerified }
    }
}
