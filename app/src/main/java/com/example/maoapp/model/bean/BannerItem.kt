package com.example.maoapp.model.bean

import com.geek.banner.loader.BannerEntry

class BannerItem : BannerEntry<Any?> {
    var path: Any? = null
    private var indicatorText: String? = null

    constructor() {}
    constructor(path: Any?, indicatorText: String?) {
        this.path = path
        this.indicatorText = indicatorText
    }

    override fun getBannerPath(): Any? {
        return path
    }

    override fun getIndicatorText(): String {
        return indicatorText!!
    }

    fun setIndicatorText(indicatorText: String?) {
        this.indicatorText = indicatorText
    }
}