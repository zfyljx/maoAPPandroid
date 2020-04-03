package com.example.maoapp.ui.activity

import android.Manifest
import android.annotation.TargetApi
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.maoapp.R
import com.example.maoapp.base.BaseInjectActivity
import com.example.maoapp.contract.WriteContract
import com.example.maoapp.presenter.WritePresenter
import com.example.maoapp.widget.PermissionCheckerDelegate
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

    lateinit var uri: Uri
    private val permissionList = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    //存储用户拒绝授权的权限
    var permissionTemp: ArrayList<String> = ArrayList()


    override fun getLayoutId(): Int=R.layout.activity_write



    override fun initInject()=activityComponent.inject(this)
    override fun initPresenter()=mPresenter.attachView(this)


    override fun initWidget() { pic_one.setOnClickListener {

//            //检查版本是否大于M
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (ContextCompat.checkSelfPermission(
//                        this@WriteActivity,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    ActivityCompat.requestPermissions(
//                        this@WriteActivity,
//                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                        PERMISSIONS_REQUEST_ALBUM
//                    )
//                } else {
//                    //权限已经被授权，开启相册
//                    goAlbum()
//                }
//            }
//        applyOpenAlbumPermission()

        CoCo.with(this)
            .pick()
            .apply()
            .start(object : GetImageCallBack<PickResult> {

                override fun onSuccess(data: PickResult) {
//                    Toast.makeText(
//                        this@MainActivity,
//                        "选择操作最终成功 path: ${data.originUri.path}",
//                        Toast.LENGTH_SHORT
//                    ).show()
                    pic_one.setImageURI(data.originUri)
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

    //权限结果回调
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//
//        when (requestCode) {
//
//            //相册权限请求结果
//            PERMISSIONS_REQUEST_ALBUM -> {
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    goAlbum()
//                } else {
//                    ToastUtils.showToast("你拒绝了读取相册权限")
//                }
//            }
//
//            //拍照权限请求结果
//            PERMISSIONS_REQUEST_CAMERA -> {
//                //用于判断是否有未授权权限，没有则开启照相
//                var isAgree = true
//                for (i in grantResults.indices) {
//                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
//                        //检查到有未授予的权限
//                        isAgree = false
//                        //判断是否勾选禁止后不再询问
//                        val showRequestPermission =
//                            ActivityCompat.shouldShowRequestPermissionRationale(
//                                this,
//                                permissions[i]
//                            )
//                        if (showRequestPermission) {
//                            ToastUtils.showToast("你拒绝了拍照相关权限")
//                        }
//                    }
//                }
//                //isAgree没有被置为false则表示权限都已授予，开启拍照
//                if (isAgree) {
////                    goCamera()
//                }
//            }
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//    }

//    //相册功能
//    private fun goAlbum() {
//        val intent = Intent()
//        intent.action = Intent.ACTION_PICK
//        intent.type = "image/*"
//        startActivityForResult(intent, ACTIVITY_REQUEST_ALBUM)
//    }

    //活动请求的回调，用requestCode来匹配
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //图片路径
        var photoPath: String = ""

        //相册
//        if (requestCode == ACTIVITY_REQUEST_ALBUM && resultCode == Activity.RESULT_OK) {
//
//            photoPath = data!!.data?.let { GetPhotoFromAlbum.getRealPathFromUri(this, it) }!!
//            Log.d("QQQQQQQQQQQQQQ",photoPath)
////            var photoPathStr= Environment.getExternalStorageDirectory().absolutePath+photoPath
//            pic_one.setImageBitmap(getBitmapByAlbum(data!!))
////            pic_one.setBackgroundResource(0)
////            pic_one.setImageURI(Uri.parse(photoPath))
////            pic_one.setImageURI(Uri.fromFile(File(photoPath)))
//
//        }
//        when (requestCode) {
//            OPEN_CAMERA -> {
//                if (resultCode == Activity.RESULT_OK) {
////                    insertBitmapToList(getBitmapByCamera())
//                }
//            }
//            OPEN_ALBUM -> {
//                if (resultCode ==Activity.RESULT_OK) {
//
//                    pic_one.setImageBitmap(getBitmapByAlbum(data!!))
//                }
//            }
//        }

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

    }

    /**
     * android4.4之后，需要解析获取图片真实路径
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun handleImageAfterKitKat(data: Intent) {
        val uri = data.data
        Log.d("URL",uri.toString())
        //document类型的Uri
        when {
            DocumentsContract.isDocumentUri(mContext, uri) -> {
                //通过documentId处理
                val docId = DocumentsContract.getDocumentId(uri)
                Log.d("URL",docId.toString())
                when (uri?.authority) {
                    "com.android.externalstorage.documents" -> {
                        val type = docId.split(":")[0]
                        if ("primary".equals(type, ignoreCase = true)) {
                            imagePath = Environment.getExternalStorageDirectory()
                                .toString() + "/" + docId.split(":")[1]
                        }
                    }
                    //media类型解析
                    "com.android.providers.media.documents" -> {
                        val id = docId.split(":")[1]
                        val type = docId.split(":")[0]
                        val contentUri: Uri? = when (type) {
                            "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                            "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                            else -> null
                        }
                        val selection = "_id=?"
                        val selectionArgs: Array<String> = arrayOf(id)
                        imagePath = getImagePath(contentUri!!, selection, selectionArgs)!!
                    }
                    //downloads文件解析
                    "com.android.providers.downloads.documents" -> {
                        ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), docId.toLong()
                        ).apply {
                            imagePath = getImagePath(this, null, null)!!
                        }
                    }
                    else -> {
                    }
                }
            }
            "content".equals(uri?.scheme, ignoreCase = true) ->
                //content类型数据不需要解析，直接传入生成即可
                imagePath = getImagePath(uri!!, null, null)!!
            "file".equals(uri?.scheme, ignoreCase = true) ->
                //file类型的uri直接获取图片路径即可
                imagePath = uri!!.path!!
        }
    }

    /**
     * android4.4之前可直接获取图片真实uri
     */
    private fun handleImageBeforeKitKat(data: Intent) {
        val uri = data.data
        imagePath = getImagePath(uri!!, null, null)!!
    }

    /**
     * 解析uri及selection
     * 获取图片真实路径
     */
    private fun getImagePath(uri: Uri, selection: String?, selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        try {
            cursor = mContext!!.contentResolver.query(uri, null, selection, selectionArgs, null)
            if (cursor?.moveToFirst()!!) {
                return cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    /**
     * 获取相册的图片转为bitmap
     */
    fun getBitmapByAlbum(data: Intent): Bitmap {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            handleImageAfterKitKat(data)
        } else {
            handleImageBeforeKitKat(data)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //7.0适配
            Log.d("imagePath",imagePath)
            oriUri = FileProvider.getUriForFile(mContext!!, "com.example.maoapp.provider", File(imagePath))
        }
        Log.d("oriUri",oriUri.toString())
        pic_three.setImageURI(oriUri)
        Log.d("BitMaporiUri",MediaStore.Images.Media.getBitmap(mContext!!.contentResolver, oriUri).toString())
//        return MediaStore.Images.Media.getBitmap(mContext!!.contentResolver, oriUri)
        return BitmapFactory.decodeStream(contentResolver.openInputStream(oriUri))
    }

    /**
     * 相机读写权限申请
     */
    fun applyCameraPermission() {
        applyWritePermission(PermissionCheckerDelegate.OPEN_CAMERA) {
            openCamera()
        }
    }

    /**
     * 相册读写权限申请
     */
    fun applyOpenAlbumPermission() {
        applyWritePermission(PermissionCheckerDelegate.OPEN_ALBUM) {
            openAlbum()
        }
    }

    /**
     * 获取相机拍下的uri并转为bitmap
     */
    fun getBitmapByCamera() = BitmapFactory
        .decodeStream(this!!.contentResolver.openInputStream(photoUri))!!



    /**
     * 打开相机
     */
    private fun openCamera() {
        //创建file于sdcard/pocketPicture/ 以当前时间命名的jpg图像
        File(Environment.getExternalStorageDirectory().absolutePath,
            "/pocket/picture/" + System.currentTimeMillis() + ".jpg").apply {
            parentFile.mkdirs()
            photoUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //android7.0之后，不再允许app透露file://Uri给其他app
                //转而使用FileProvider来生成content://Uri取代file://Uri
                FileProvider
                    .getUriForFile(mContext!!, "com.dididi.pocket.provider", this)
            } else {
                //7.0之前 直接获取Uri
                Uri.fromFile(this)
            }
        }
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            //将uri存进intent，供相机回调使用 data.getData中获取
            putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            startActivityForResult(this, PermissionCheckerDelegate.OPEN_CAMERA)
        }
    }

    /**
     * 打开相册
     */
    private fun openAlbum() {
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            startActivityForResult(this, PermissionCheckerDelegate.OPEN_ALBUM)
        }
    }

    /**
     * 裁剪Uri
     * @param oriUri 原始Uri
     * @param desUri 目标Uri
     */
    fun cropImageUri(oriUri: Uri, desUri: Uri, aspectX: Int, aspectY: Int, width: Int, height: Int) {
        Intent("com.android.camera.action.CROP").apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            setDataAndType(oriUri, "image/*")
            putExtra("crop", "true")
            putExtra("aspectX", aspectX)
            putExtra("aspectY", aspectY)
            putExtra("outputX", width)
            putExtra("outputY", height)
            putExtra("scale", true)
            //将剪切的图片保存到目标Uri中
            putExtra(MediaStore.EXTRA_OUTPUT, desUri)
            putExtra("return-data", false)
            putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
            putExtra("noFaceDetection", true)
            this@WriteActivity.startActivityForResult(this,
                PermissionCheckerDelegate.CROP_IMAGE
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //权限请求结果
        when (requestCode) {
            PermissionCheckerDelegate.WRITE_EXTERNAL_STORAGE -> {
                permissionHint(grantResults, "没有读写权限") {}
            }
            PermissionCheckerDelegate.OPEN_CAMERA -> {
                permissionHint(grantResults, "没有读写权限") {
                    openCamera()
                }
            }
            PermissionCheckerDelegate.OPEN_ALBUM -> {
                permissionHint(grantResults, "没有读写权限") {
                    openAlbum()
                }
            }
            else -> {
                Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * 权限结果处理lambda函数
     * @param grantResults 请求结果
     * @param msg toast内容
     * @param target 权限拿到要做什么
     */
    private fun permissionHint(grantResults: IntArray, msg: String, target: () -> Unit) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            target()
        } else {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 请求读写权限
     * @param requestCode 请求码
     * @param target 要做什么
     */
    private fun applyWritePermission(requestCode: Int, target: () -> Unit) {
        val permissions = listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        //android6.0之后，需要动态申请读写权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //读写是否已经授权
            val check = ContextCompat.checkSelfPermission(this!!, permissions[0])
            if (check == PackageManager.PERMISSION_GRANTED) {
                target()
            } else {
                //如果未发现授权，则请求权限
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    requestCode)
            }
        } else {
            target()
        }
    }
}
