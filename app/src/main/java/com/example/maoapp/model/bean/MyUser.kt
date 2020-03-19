package com.example.maoapp.model.bean

import cn.bmob.v3.BmobUser

class MyUser:BmobUser() {
    private lateinit var  portrait:String
    private lateinit var  profile:String
}