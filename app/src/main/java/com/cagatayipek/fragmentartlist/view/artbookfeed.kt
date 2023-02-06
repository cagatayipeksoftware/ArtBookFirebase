package com.cagatayipek.fragmentartlist.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import android.view.MenuInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cagatayipek.fragmentartlist.R
import com.cagatayipek.fragmentartlist.adapter.FeedRecyclerAdapter
import com.cagatayipek.fragmentartlist.databinding.FragmentArtbookfeedBinding
import com.cagatayipek.fragmentartlist.model.Post
import com.google.api.Distribution.BucketOptions.Linear
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_art_post.*
import kotlinx.android.synthetic.main.fragment_artbookfeed.*
import kotlinx.android.synthetic.main.recycler_row.view.*

class artbookfeed : Fragment() {
    private lateinit var feedadapter:FeedRecyclerAdapter
    var db = Firebase.firestore
    var storage = Firebase.storage
    var postArrayList=ArrayList<Post>()
    var auth=Firebase.auth
    private lateinit var binding:FragmentArtbookfeedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        binding= FragmentArtbookfeedBinding.inflate(layoutInflater)




    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.post_menu,menu)


        super.onCreateOptionsMenu(menu, inflater)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        // Inflate the layout for this fragment

        val view=binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        binding.recyclerview.layoutManager=LinearLayoutManager(requireContext())
        binding.recyclerview.setHasFixedSize(true)
        feedadapter= FeedRecyclerAdapter(postArrayList)
        binding.recyclerview.adapter=feedadapter



    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemid=item.itemId
        val view: View? =view
        if (itemid== R.id.addpost){
            var action= artbookfeedDirections.actionArtbookfeedToArtPost()
            if (view != null) {
                Navigation.findNavController(view).navigate(action)
            }
        }

        if(itemid==R.id.logout){
                auth.signOut()
                val action=artbookfeedDirections.actionArtbookfeedToLogin()
            if(view!=null){
                Navigation.findNavController(view).navigate(action)

            }}
        return super.onOptionsItemSelected(item)
    }
    fun getData(){
        db.collection("Posts").orderBy("Date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if(value!=null){
                if(!value.isEmpty){
                    val documents=value.documents
                    postArrayList.clear()
                    for(document in documents){
                        val email=document.get("Email") as String
                        val artname=document.get("Art Name") as String
                        val artistname=document.get("Artist Name") as String
                        val artyear=document.get("Art Year") as String
                        //val artdate=document.get("Date") as String
                        val downloadURL=document.get("downloadURL") as String
                        val post= Post(artname, artistname,artyear,downloadURL,email)
                        postArrayList.add(post)
                    }
                    feedadapter.notifyDataSetChanged()
                }
            }
        }
    }







}