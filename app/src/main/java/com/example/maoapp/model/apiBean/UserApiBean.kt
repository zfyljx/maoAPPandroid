package com.example.maoapp.model.apiBean

import com.example.maoapp.model.bean.ShareModelList

data class UserApiBean(val status:Int,val message:String,val data: User){
    data class User(val id:Long,val userName:String,val phoneNumber:String,val password:String,val gender:Int,val description:String)
}


data class QiniuToken(val status: Int,val message: String,val data:QiUToken){
    data class QiUToken(val token:String)
}

data class LocationBean(val status: Int,val message: String,val data:AddressDetail){
    data class AddressDetail(val address:String)
}

data class ResultNoDataBean(val status: Int,val message: String)

data class SharesBean(val status: Int,val message: String,val data:List<ShareModelList.ShareModel>)
