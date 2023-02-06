package com.cagatayipek.fragmentartlist.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.cagatayipek.fragmentartlist.R
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.fragment_art_post.*
import java.util.UUID

class artPost : Fragment() {
    var db = Firebase.firestore
    var storage = Firebase.storage
    var imageURI: Uri? = null
    val pickImage = 100
    val auth=Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {


        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_art_post, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       imageView2.setOnClickListener {
           Dexter.withContext(requireContext())
           .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
           .withListener(object : PermissionListener {
               override fun onPermissionGranted(response: PermissionGrantedResponse) {
                   val gallery=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                   startActivityForResult(gallery,pickImage)


               }


               override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
               }

               override fun onPermissionRationaleShouldBeShown(
                   permission: PermissionRequest?,
                   token: PermissionToken?
               ) { /* ... */
               }
           }).check() }
        post.setOnClickListener {
            val uuid=UUID.randomUUID()
            val reference=storage.reference
            val imageName="$uuid.jpg"
            val imageReference=reference.child("Images").child(imageName)
            imageReference.putFile(imageURI!!).addOnSuccessListener {
                val uploadedImage=storage.reference.child("Images").child(imageName)
                uploadedImage.downloadUrl.addOnSuccessListener {
                    val uploadedImageURL = it.toString()
                    val postmap = hashMapOf<String, Any>()
                    postmap.put("downloadURL", uploadedImageURL)
                    postmap.put("Art Name", artName.text.toString())
                    postmap.put("Artist Name", artistName.text.toString())
                    postmap.put("Art Year", artYear.text.toString())
                    postmap.put("Date", Timestamp.now())
                    postmap.put("Email", auth.currentUser?.email!!)
                    db.collection("Posts").add(postmap).addOnSuccessListener {
                        val action = artPostDirections.actionArtPostToArtbookfeed()
                        Navigation.findNavController(view).navigate(action)
                    }
                }.addOnFailureListener { Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_LONG).show() }

            }
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == pickImage) {
            imageURI = data?.data
            imageView2.setImageURI(imageURI)
        }
    }






}

