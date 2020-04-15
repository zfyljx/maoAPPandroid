package com.example.maoapp.ui.activity

//import androidx.core.app.ActivityCompat
import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import com.afollestad.assent.Permission
import com.afollestad.assent.runWithPermissions
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.example.maoapp.MainActivity
import com.example.maoapp.R
import com.example.maoapp.base.BaseInjectActivity
import com.example.maoapp.contract.WriteContract
import com.example.maoapp.model.bean.LocationModel
import com.example.maoapp.presenter.WritePresenter
import com.example.maoapp.utils.LocationResultUtils
import com.example.maoapp.utils.LocationResultUtils.isLocationProviderEnabled
import com.example.maoapp.utils.ToastUtils
import com.qiniu.android.storage.UploadManager
import com.qw.photo.CoCo
import com.qw.photo.callback.GetImageCallBack
import com.qw.photo.pojo.PickResult
import kotlinx.android.synthetic.main.activity_write.*
import java.util.*
import kotlin.collections.ArrayList

class WriteActivity : BaseInjectActivity<WritePresenter>(), WriteContract.View{

    //给子类用于显示的相片地址
    private lateinit var photoUri: Uri
    private lateinit var imagePath: String
    //供裁剪使用
    private lateinit var oriUri: Uri
    private var selectImage:Int=1
    private var firstImageUrl=""
    private var secondImageUrl=""
    private var threeImageUrl=""
    companion object {
        const val WRITE_EXTERNAL_STORAGE = 1
        const val SELECT_IMAGE_ONE = 1
        const val SELECT_IMAGE_TWO = 2
        const val SELECT_IMAGE_THREE = 3
    }

    private lateinit var qiniuTokenStr:String
    private  val  uploadManager= UploadManager()
    lateinit var uri: Uri
    private val permissionList = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    //存储用户拒绝授权的权限
    var permissionTemp: ArrayList<String> = ArrayList()
    private lateinit var mLocationClient: AMapLocationClient
    private var mLocationOption = AMapLocationClientOption()


    override fun getLayoutId(): Int=R.layout.activity_write



    override fun initInject()=activityComponent.inject(this)
    override fun initPresenter()=mPresenter.attachView(this)


    override fun initWidget() {
        mPresenter.getQiniuToken()
           initMap()

        initLinserter()


    }



    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

when(requestCode){
    WriteActivity.WRITE_EXTERNAL_STORAGE ->{
        var isAgree = true
        for (i in grantResults.indices){
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                isAgree = false
                val showRequestPermission= shouldShowRequestPermissionRationale(permissions[i])
                if (showRequestPermission){
                    ToastUtils.showToast("你拒绝了读写存储权限")
                    Log.d("FFFFFFFFF","拒绝了读写存储权限")
                }
            }
        }
        if (isAgree){
            Log.d("TTTTTTTT","允许了读写存储权限")
            updateImageToQinu()
        }
    }
}


        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    private fun initLinserter(){
        pic_one.setOnClickListener {
            selectImage= SELECT_IMAGE_ONE
            //检查版本是否大于M
            verfyPermissionsToGallery()
        }
        pic_second.setOnClickListener {
            selectImage= SELECT_IMAGE_TWO
            //检查版本是否大于M
            verfyPermissionsToGallery()
        }

        pic_three.setOnClickListener {
            selectImage= SELECT_IMAGE_THREE
            //检查版本是否大于M
            verfyPermissionsToGallery()
        }




    get_address.setOnClickListener {
        locate()
    }

        submit_write.setOnClickListener{
            submitShare()
        }

        write_back.setOnClickListener{
            isBack()
        }

    }

    private fun isBack(){
        val dialog=AlertDialog.Builder(this)
            .setTitle("放弃编辑")
            .setIcon(R.drawable.is_back)
            .setMessage("确定要放弃编辑吗？")
            .setPositiveButton("确定",DialogInterface.OnClickListener { _, _ ->
                val intent= Intent(this@WriteActivity, MainActivity::class.java)
                startActivity(intent)
            })
            .setNegativeButton("取消",DialogInterface.OnClickListener { _, _ ->  })
            .create().show()
    }
    private fun submitShare(){
        if (checkALLDataBlank()){
            ToastUtils.showToast("请编辑发布内容")
        }else{
            val userProfile=getSharedPreferences("userProfile", Context.MODE_PRIVATE)
            val edit=userProfile.edit()
            val userId=userProfile.getLong("userId",0)
            val userName=userProfile.getString("userName","窝里横")
            mPresenter.uploadShare(userId,write_text.text.toString().trim(),write_address.text.toString().trim(),firstImageUrl,secondImageUrl,threeImageUrl)
        }
    }

    private fun checkALLDataBlank():Boolean{
        val dadalist= arrayListOf<String>()
        dadalist.add(write_text.text.toString())
        return dadalist.contains("")
    }
    private fun updateImageToQinu(){

        CoCo.with(this)
            .pick()
            .apply()
            .start(object : GetImageCallBack<PickResult> {

                override fun onSuccess(data: PickResult) {
//
                    when(selectImage){
                        SELECT_IMAGE_ONE ->{
                            pic_one.setImageURI(data.originUri)
                                         }
                        SELECT_IMAGE_TWO ->{
                            pic_second.setImageURI(data.originUri)
                                           }
                        SELECT_IMAGE_THREE -> {
                            pic_three.setImageURI(data.originUri)
                        }
                    }

                    verfyPerrsion()
                    var key = UUID.randomUUID().toString()
                    uploadManager.put(
                        data.localPath,key,qiniuTokenStr,
                        { key, info, response ->
                            /**
                             * 用户自定义的内容上传完成后处理动作必须实现的方法
                             *
                             * @param key      文件上传保存名称
                             * @param info     上传完成返回日志信息
                             * @param response 上传完成的回复内容
                             */
                            Log.d("TTTTTTTT",info.toString())
                            if (info.statusCode == 200){
                                when(selectImage){
                                    SELECT_IMAGE_ONE ->{
                                       firstImageUrl=key
                                    }
                                    SELECT_IMAGE_TWO ->{
                                        secondImageUrl=key
                                    }
                                    SELECT_IMAGE_THREE -> {
                                        threeImageUrl=key
                                    }
                                }
                            }
//                            PicassoLoader.createLoader(pic_three, ApiConstants.QINIU_URL+key)
//                                .attach()
                        },null)


                }

                override fun onFailed(exception: Exception) {
                    Toast.makeText(this@WriteActivity, "选择异常: $exception", Toast.LENGTH_SHORT).show()
                }
            })
    }
    override fun loadData() {

    }

    override fun initVariables() {


    }

    override fun setToken(data: String) {
        qiniuTokenStr=data
    }


    private fun verfyPermissionsToGallery(){
        //检查版本是否大于M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    this,
                    permissionList,
                    WRITE_EXTERNAL_STORAGE
                )
            } else {
                //权限已经被授权，开启相册
                updateImageToQinu()
            }
        }
    }

    private fun verfyPerrsion(){
        //检查版本是否大于M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    this,
                    permissionList,
                    WRITE_EXTERNAL_STORAGE
                )
            }
        }
    }

    /**
     * 解析uri及selection
     * 获取图片真实路径
     */
    private fun getImagePath(uri: Uri, selection: String?, selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        try {
            cursor = this!!.contentResolver.query(uri, null, selection, selectionArgs, null)
            if (cursor?.moveToFirst()!!) {
                return cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
        } finally {
            cursor?.close()
        }
        return null
    }


    private fun initMap() {
        mLocationClient = AMapLocationClient(applicationContext)
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        mLocationOption.isOnceLocation = true

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.isOnceLocationLatest = true

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.isNeedAddress = true
        //关闭缓存机制
        mLocationOption.isLocationCacheEnable = false

        //声明定位回调监听器
        val locationListener = AMapLocationListener { loc ->
            if (null != loc) {
                //解析定位结果
                val result = LocationResultUtils.getLocationStr(loc)
//                locationTv.text = result
                Log.d("TTTTTTTTTTT",result.toString())
                setLocationResult(result)
            } else {
                write_address.text="获取定位信息失败"
            }
        }
        //设置定位回调监听
        mLocationClient.setLocationListener(locationListener)
    }

    private fun locate() {
        runWithPermissions(Permission.ACCESS_FINE_LOCATION) {
            if (isLocationProviderEnabled(this@WriteActivity)) {
                //给定位客户端对象设置定位参数
                mLocationClient.setLocationOption(mLocationOption);
                //启动定位
                mLocationClient.startLocation()
            } else {
                LocationResultUtils.showAlert("本应用需要获取地理位置，请打开获取位置的开关", this)
            }
        }
    }

    private fun setLocationResult(result:LocationModel?){
        if (result?.errorCode == 0 ){
            when(result?.locationType){
                1 -> {
                   mPresenter.getAdressDetail(result?.longitude.toString(),result?.latitude.toString())
                      }
                5 -> {
                    ToastUtils.showToast("定位精度不够，可再重试一次")
                    mPresenter.getAdressDetail(result?.longitude.toString(),result?.latitude.toString())
                     }
                else ->{
                    ToastUtils.showToast("网络定位出了点问题，请稍后再试")
                }
            }
        }
    }

    override fun setAddressDetail(address: String) {
       write_address.text=address
    }

    override fun showUploadShareResult(result: Boolean) {
        if (result){
            ToastUtils.showToast("发布成功")
            val intent= Intent(this@WriteActivity, MainActivity::class.java)
            startActivity(intent)
        }else{
            ToastUtils.showToast("网络出了点问题，请稍后再试")
        }
    }

    override fun showToast(message: String) {
        ToastUtils.showToast(message)
    }
}

