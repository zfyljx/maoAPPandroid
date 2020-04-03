package com.example.maoapp.model.apiBean

data class UserApiBean(val status:Int,val message:String,val data: User){
    data class User(val id:Long,val userName:String,val phoneNumber:String,val password:String,val gender:Int,val description:String)
}


data class QiniuToken(val status: Int,var message: String,val data:QiUToken){
    data class QiUToken(val token:String)
}