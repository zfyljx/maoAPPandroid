package com.example.maoapp.model.bean

data class LocationModel(val locationType:Int,val longitude:Double,val latitude:Double,val errorCode:Int,val errorMessage:String)

data class ShareModelList(val shares:List<ShareModel>){
    data class ShareModel(val id:Long,val userId:Long,val userName:String,val message:String,val address:String,val imageOne:String,val imageTwo:String,val imageThree:String)
}
