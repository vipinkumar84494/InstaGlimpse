package com.vk.instaglimpse.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.vk.instaglimpse.Adapter.UserAdapter
import com.vk.instaglimpse.R
import com.vk.instaglimpse.databinding.FragmentSearchBinding
import com.vk.instaglimpse.model.User


class SearchFragment : Fragment() {


    lateinit var binding: FragmentSearchBinding
    private var recyclerView: RecyclerView? = null
    private var userAdapter: UserAdapter? = null
    private var usrList: ArrayList<User>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvSearchUser
        recyclerView!!.layoutManager = LinearLayoutManager(requireContext())
        usrList = ArrayList()
        userAdapter = UserAdapter(requireContext(), usrList as ArrayList<User>,true)
        recyclerView!!.adapter = userAdapter
        recyclerView!!.visibility = View.GONE

        binding.etSearchUser.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.etSearchUser.text.toString() == ""){

                }
                else {
                    recyclerView!!.visibility = View.VISIBLE
                    retrieveUsers()
                    searchUser(p0.toString().lowercase())
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun searchUser(input: String) {

        val query = FirebaseDatabase.getInstance().getReference().child("Users")
            .orderByChild("fullname")
            .startAt(input)
            .endAt(input + "\uf8ff")

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                    usrList!!.clear()

                    for (snap in snapshot.children){
                        val user = snap.getValue(User::class.java)
                        if (user != null){
                            usrList!!.add(user)
                        }
                    }
                    userAdapter!!.notifyDataSetChanged()


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun retrieveUsers() {

        val userRef = FirebaseDatabase.getInstance().getReference().child("Users")

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                usrList!!.clear()
                if (binding.etSearchUser.text.toString() == ""){

                    for (snap in snapshot.children){
                        val user = snap.getValue(User::class.java)
                        if (user != null){
                            usrList!!.add(user)
                        }
                    }
                    userAdapter!!.notifyDataSetChanged()
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

}