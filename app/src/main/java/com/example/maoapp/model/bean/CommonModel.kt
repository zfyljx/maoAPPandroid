package com.example.maoapp.model.bean

data class LocationModel(val locationType:Int,val longitude:Double,val latitude:Double,val errorCode:Int,val errorMessage:String)

data class ShareModelList(val shares:List<ShareModel>){
    data class ShareModel(val id:Long,val userId:Long,val userName:String,val createTime:String,val message:String,val address:String,val imageOne:String,val imageTwo:String,val imageThree:String)
}


data class SellModel(val id:Long,val storeId:Long,val storeName:String,val userPhone:String,val userId: Long,val userName: String,val name:String,val price:Float,val monthSell:Int,val introduction:String,val description:String,val imageOne: String,val imageTwo: String,val imageThree: String)

