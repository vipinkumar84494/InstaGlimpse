package com.vk.instaglimpse.model

 class User {

     private var username: String? = ""
     private var fullname: String? = ""
     private var email: String? = ""
     private var bio: String? = ""
     private var uid: String? = ""
     private var image: String? = ""

     constructor()

     constructor(
         username: String?,
         fullname: String?,
         email: String?,
         bio: String?,
         uid: String?,
         image: String?
     ) {
         this.username = username
         this.fullname = fullname
         this.email = email
         this.bio = bio
         this.uid = uid
         this.image = image
     }

     fun getUserName(): String? {
         return username
     }

     fun setUserName(username: String?) {
         this.username = username
     }

     fun getFullName(): String? {
         return fullname
     }

     fun setFullName(fullname: String?) {
         this.fullname = fullname
     }

     fun getUserEmail(): String? {
         return email
     }

     fun setUserEmail(email: String?) {
         this.email = email
     }

     fun getUserBio(): String? {
         return bio
     }

     fun setUserBio(bio: String?) {
         this.bio = bio
     }

     fun getUserUid(): String? {
         return uid
     }

     fun setUserUid(uid: String?) {
         this.uid = uid
     }

     fun getProfileImage(): String?{
         return image
     }
     fun setProfileImage(image: String?){
        this.image = image
     }
 }


