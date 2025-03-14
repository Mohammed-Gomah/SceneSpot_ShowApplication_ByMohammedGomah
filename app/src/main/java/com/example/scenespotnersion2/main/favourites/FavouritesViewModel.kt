package com.example.scenespotnersion2.main.favourites

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.scenespotnersion2.remote.data.SeriesDBItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FavouritesViewModel(application: Application) : AndroidViewModel(application) {
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private val firebaseRef =
        userId?.let { FirebaseDatabase.getInstance().getReference("favourites").child(it) }

    private val _favouritesList = MutableLiveData<List<SeriesDBItem>>()
    val favouritesList: LiveData<List<SeriesDBItem>> get() = _favouritesList

    fun toggleFavourite(show: SeriesDBItem, updateUI: (Boolean) -> Unit) {
        if (firebaseRef == null) return

        checkFavouriteState(show.id.toString()) { isFavourite ->
            if (isFavourite) {
                firebaseRef.child(show.id.toString()).removeValue().addOnSuccessListener {
                    updateUI(false)
                }
            } else {
                firebaseRef.child(show.id.toString()).setValue(show).addOnSuccessListener {
                    updateUI(true)
                }
            }
        }
    }


    fun checkFavouriteState(showId: String, callback: (Boolean) -> Unit) {
        if (firebaseRef==null) return
        firebaseRef.child(showId).get().addOnSuccessListener {
            callback(it.exists())
        }
    }

    fun getFavourites() {
        if (firebaseRef==null) return
        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val favList = mutableListOf<SeriesDBItem>()
                for (child in snapshot.children) {
                    val show = child.getValue(SeriesDBItem::class.java)
                    show?.let { favList.add(it) }
                }
                _favouritesList.value = favList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FavouritesViewModel", "onCancelled: ${error.message}")
            }
        })
    }

}
