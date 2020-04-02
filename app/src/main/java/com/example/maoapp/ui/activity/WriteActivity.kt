package com.example.maoapp.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.maoapp.R
import com.example.maoapp.base.BaseInjectActivity
import com.example.maoapp.contract.WriteContract
import com.example.maoapp.presenter.WritePresenter
import com.example.maoapp.utils.GetPhotoFromAlbum
import com.example.maoapp.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_write.*

class WriteActivity : BaseInjectActivity<WritePresenter>(), WriteContract.View{

    private var selectImage:Int=1
    private var firstImageUrl=""
    private var secondImageUrl=""
    private var threeImageUrl=""
    companion object {
        private const val PERMISSIONS_REQUEST_ALBUM = 1
        private const val PERMISSIONS_REQUEST_CAMERA = 2

        private const val ACTIVITY_REQUEST_ALBUM = 3
        private const val ACTIVITY_REQUEST_CAMERA = 4
    }

    lateinit var uri: Uri
    private val permissionList = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    //存储用户拒绝授权的权限
    var permissionTemp: ArrayList<String> = ArrayList()


    override fun getLayoutId(): Int=R.layout.activity_write



    override fun initInject()=activityComponent.inject(this)
    override fun initPresenter()=mPresenter.attachView(this)


    override fun initWidget() {
        pic_one.setOnClickListener {
            //检查版本是否大于M
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        PERMISSIONS_REQUEST_ALBUM
                    )
                } else {
                    //权限已经被授权，开启相册
                    goAlbum()
                }
            }


        }


    }


    override fun loadData() {

    }

    override fun initVariables() {

    }

    //权限结果回调
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        when (requestCode) {

            //相册权限请求结果
            PERMISSIONS_REQUEST_ALBUM -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goAlbum()
                } else {
                    ToastUtils.showToast("你拒绝了读取相册权限")
                }
            }

            //拍照权限请求结果
            PERMISSIONS_REQUEST_CAMERA -> {
                //用于判断是否有未授权权限，没有则开启照相
                var isAgree = true
                for (i in grantResults.indices) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        //检查到有未授予的权限
                        isAgree = false
                        //判断是否勾选禁止后不再询问
                        val showRequestPermission =
                            ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                permissions[i]
                            )
                        if (showRequestPermission) {
                            ToastUtils.showToast("你拒绝了拍照相关权限")
                        }
                    }
                }
                //isAgree没有被置为false则表示权限都已授予，开启拍照
                if (isAgree) {
//                    goCamera()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    //相册功能
    private fun goAlbum() {
        val intent = Intent()
        intent.action = Intent.ACTION_PICK
        intent.type = "image/*"
        startActivityForResult(intent, ACTIVITY_REQUEST_ALBUM)
    }

    //活动请求的回调，用requestCode来匹配
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        //图片路径
        var photoPath: String = ""

        //相册
        if (requestCode == ACTIVITY_REQUEST_ALBUM && resultCode == Activity.RESULT_OK) {

            photoPath = data!!.data?.let { GetPhotoFromAlbum.getRealPathFromUri(this, it) }!!
            pic_one.setBackgroundResource(0)
            pic_one.setImageURI(Uri.parse(photoPath))
        }
            //拍照
//        } else if (requestCode == ACTIVITY_REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
//
//            photoPath = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
////                cameraSavePath.toString()
//            } else {
//                uri.encodedPath
//            }
//            imageView.setImageURI(Uri.parse(photoPath))
//        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}
