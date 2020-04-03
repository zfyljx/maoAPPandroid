package com.example.maoapp.ui.activity

import android.Manifest
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.maoapp.R
import com.example.maoapp.base.BaseInjectActivity
import com.example.maoapp.contract.WriteContract
import com.example.maoapp.presenter.WritePresenter
import com.qiniu.android.storage.UploadManager
import com.qw.photo.CoCo
import com.qw.photo.callback.GetImageCallBack
import com.qw.photo.pojo.PickResult
import kotlinx.android.synthetic.main.activity_write.*
import java.io.File

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
        const val OPEN_CAMERA = 2
        const val OPEN_ALBUM = 3
        const val CROP_IMAGE = 4
    }

    private lateinit var qiniuTokenStr:String
    private  val  uploadManager= UploadManager()
    lateinit var uri: Uri
    private val permissionList = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    //存储用户拒绝授权的权限
    var permissionTemp: ArrayList<String> = ArrayList()


    override fun getLayoutId(): Int=R.layout.activity_write



    override fun initInject()=activityComponent.inject(this)
    override fun initPresenter()=mPresenter.attachView(this)


    override fun initWidget() {
        mPresenter.getQiniuToken()
        pic_one.setOnClickListener {
        CoCo.with(this)
            .pick()
            .apply()
            .start(object : GetImageCallBack<PickResult> {

                override fun onSuccess(data: PickResult) {
//
                    pic_one.setImageURI(data.originUri)
                    var imagePath=getImagePath(data.originUri,null,null)
                    Log.d("图片地址",imagePath)
                    Log.d("图片地址",data.localPath)
                    Log.d("图片地址",data.originUri.toString())
                    uploadManager.put(
                        File(data.localPath),"zfyljxq.jpg",qiniuTokenStr,
                        { key, info, response ->
                            /**
                             * 用户自定义的内容上传完成后处理动作必须实现的方法
                             *
                             * @param key      文件上传保存名称
                             * @param info     上传完成返回日志信息
                             * @param response 上传完成的回复内容
                             */
                            Log.d("七牛云文件名称",key)
                            Log.d("七牛云返回结果",info.toString())
                        },null)
                }

                override fun onFailed(exception: Exception) {
                    Toast.makeText(this@WriteActivity, "选择异常: $exception", Toast.LENGTH_SHORT).show()
                }
            })
        }


    }


    override fun loadData() {

    }

    override fun initVariables() {


    }

    override fun setToken(data: String) {
        qiniuTokenStr=data
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


}

