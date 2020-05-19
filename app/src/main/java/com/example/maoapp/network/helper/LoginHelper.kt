package com.example.maoapp.network.helper

import com.example.maoapp.model.apiBean.*
import com.example.maoapp.network.api.LoginService
import io.reactivex.Flowable

class LoginHelper(private val mLoginService: LoginService) {
    fun login(phoneNumber:String,password:String): Flowable<UserApiBean> = mLoginService.login(phoneNumber, password)

    fun register(userName:String, phoneNumber:String, password:String):Flowable<UserApiBean> = mLoginService.resterUser(userName, phoneNumber, password)

    fun phoneNumberIsOnly(phoneNumber:String):Flowable<UserApiBean> = mLoginService.phoneNumberIsOnly(phoneNumber)

    fun userNameIsOnly(userName:String):Flowable<UserApiBean> = mLoginService.userNameIsOnly(userName)

    fun getQiniuToken():Flowable<QiniuToken> = mLoginService.getQiniuUpdateToken()

    fun getAdressDetail(longitude:String,latitude:String):Flowable<LocationBean> = mLoginService.getAddressDetail(longitude, latitude)

    fun upLoadShare(id:Long,userName: String,message:String,address:String,imageOne:String,imageTwo:String,imageThree:String,classification:Int,userImage:String):Flowable<ResultNoDataBean> = mLoginService.saveShare(id,userName, message, address, imageOne, imageTwo, imageThree,classification, userImage)

    fun getShares():Flowable<SharesBean> =mLoginService.getShares()

    fun queryShares(query:String):Flowable<SharesBean> = mLoginService.queryShares(query)

    fun getMineShares(userId:Long):Flowable<SharesBean> = mLoginService.getMineShares(userId)


    fun getSells():Flowable<SellsBean> = mLoginService.getSells()

    fun getSellById(id: Long):Flowable<SellBean> = mLoginService.getSellById(id)

    fun getSellsByUserId(userId: Long):Flowable<SellsBean> = mLoginService.getSellsByUserId(userId)

    fun buildOrder(goodId:Long,storeId:Long,storeName:String,name:String,userId:Long,userName:String,userPhone:String,userAddress:String,imagePath:String,price:Float,number:Int,totalPrice:Float):Flowable<ResultNoDataBean> =mLoginService.buildOrder(goodId,storeId, storeName, name, userId, userName, userPhone, userAddress, imagePath, price, number, totalPrice)

    fun getMyOrders(userId: Long):Flowable<OrdersBean> = mLoginService.getOrdersByUserId(userId)

    fun getOrder(orderId:Long):Flowable<OrderBean> = mLoginService.getOrderByOrderId(orderId)

    fun updateOrder(orderId: Long):Flowable<ResultNoDataBean> = mLoginService.updateOrderStatus(orderId)

    fun querySells(query:String):Flowable<SellsBean> = mLoginService.querySells(query)

    fun getSharesByUserId(userId: Long):Flowable<SharesBean> =mLoginService.getSharesByUserId(userId)

    fun updateSharesStatus(id: Long):Flowable<ResultNoDataBean> = mLoginService.updateSharesStatus(id)
}