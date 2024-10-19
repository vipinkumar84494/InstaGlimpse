package com.vk.instaglimpse.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vk.instaglimpse.R
import com.vk.instaglimpse.model.User
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(private var context: Context,
                  private var userList: List<User>,
                  private var fragment: Boolean = false
): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
         var view = LayoutInflater.from(context).inflate(R.layout.user_item_layout, parent, false)

        return UserAdapter.UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {

        holder.usernameTextView.text = userList[position].getUserName()
        holder.fullNameTextView.text = userList[position].getFullName()

        Glide.with(holder.itemView.context)
            .load(userList[position].getProfileImage())
//            .placeholder(R.drawable.placeholder_image) // Set your placeholder
            .error(R.drawable.profile) // Set your error image
            .into(holder.userProfileImageView)

        Log.e("userName",userList[position].getUserName().toString())
        Log.e("userImage",userList[position].getProfileImage().toString())


    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var usernameTextView = itemView.findViewById<TextView>(R.id.tv_username_search)
        var fullNameTextView = itemView.findViewById<TextView>(R.id.tv_user_profile_name_search)
        var userProfileImageView = itemView.findViewById<CircleImageView>(R.id.user_profile_search)
        var userFollowButton = itemView.findViewById<Button>(R.id.btn_follow_profile)
    }
}