package com.example.scenespotnersion2.auth

import android.app.Application
import android.net.Uri
import android.widget.Toast
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.scenespotnersion2.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseRef = FirebaseDatabase.getInstance().getReference("users")





    private val _authState = MutableLiveData<FirebaseUser?>()
    val authState: LiveData<FirebaseUser?> get() = _authState

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _resetPasswordStatus = MutableLiveData<Boolean>()
    val resetPasswordStatus: LiveData<Boolean> get() = _resetPasswordStatus

    init {
        checkIfLoggedIn()

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(application.getString(com.firebase.ui.auth.R.string.default_web_client_id))
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

    }


    fun setupSignInWithGoogle(){}


    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user?.isEmailVerified == true) {
                        _authState.value = user
                    } else {
                        _errorMessage.value = "Please verify your email before logging in."
                        auth.signOut()
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
                    user?.sendEmailVerification()?.addOnCompleteListener { verificationTask ->
                        if (verificationTask.isSuccessful) {
                            _errorMessage.value = "Registered Successfully, check your email for verification."
                        } else {
                            _errorMessage.value = "Registration successful, but failed to send verification email."
                        }
                    }
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

    fun uploadProfilePictureToFirebase(imageUri: Uri) {
        val user = auth.currentUser ?: return
        val storageRef = FirebaseStorage.getInstance().getReference("images").child(user.uid + "_" + System.currentTimeMillis())

        storageRef.putFile(imageUri)
            .addOnSuccessListener { task ->
                task.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        val photoUrl = uri.toString()

                        // ✅ استدعاء updateProfilePicture لحفظ الصورة في FirebaseAuth + Realtime Database
                        updateProfilePicture(photoUrl)

                        Toast.makeText(getApplication(), "✅ تم حفظ الصورة بنجاح!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(getApplication(), "❌ فشل الحصول على الرابط: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener { e ->
                Toast.makeText(getApplication(), "❌ فشل رفع الصورة: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }










    private fun updateProfilePicture(photoUrl: String) {
        val user = auth.currentUser ?: return
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setPhotoUri(Uri.parse(photoUrl))
            .build()

        user.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseRef.child(user.uid).child("photoUrl").setValue(photoUrl)
                } else {
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
