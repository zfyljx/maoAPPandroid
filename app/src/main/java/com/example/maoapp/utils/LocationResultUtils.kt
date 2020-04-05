package com.example.maoapp.utils

import android.app.AlertDialog
import android.content.Context
import android.location.LocationManager
import com.amap.api.location.AMapLocation
import com.example.maoapp.model.bean.LocationModel
import java.text.SimpleDateFormat
import java.util.*

object LocationResultUtils {
    /**
     * 根据定位结果返回定位信息的字符串
     */
    fun getLocationStr(location: AMapLocation): LocationModel? {
        val sb = StringBuffer()

        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
//        if (location.errorCode == 0) {
            val locationModel=LocationModel(location.locationType,location.longitude,location.latitude,location.errorCode,location.errorInfo)
//            sb.append("定位成功" + "\n")
//            sb.append("定位类型: " + location.locationType + "\n")
//            sb.append("经    度    : " + location.longitude + "\n")
//            sb.append("纬    度    : " + location.latitude + "\n")
//            sb.append("精    度    : " + location.accuracy + "米" + "\n")
//            sb.append("提供者    : " + location.provider + "\n")
//
//            sb.append("速    度    : " + location.speed + "米/秒" + "\n")
//            sb.append("角    度    : " + location.bearing + "\n")
//            // 获取当前提供定位服务的卫星个数
//            sb.append("星    数    : " + location.satellites + "\n")
//            sb.append("国    家    : " + location.country + "\n")
//            sb.append("省            : " + location.province + "\n")
//            sb.append("市            : " + location.city + "\n")
//            sb.append("城市编码 : " + location.cityCode + "\n")
//            sb.append("区            : " + location.district + "\n")
//            sb.append("区域 码   : " + location.adCode + "\n")
//            sb.append("地    址    : " + location.address + "\n")
//            sb.append("兴趣点    : " + location.poiName + "\n")
//            //定位完成的时间
//            sb.append("定位时间: " + formatUTC(location.time, "yyyy-MM-dd HH:mm:ss") + "\n")
//        } else {
//            //定位失败
//            sb.append("定位失败" + "\n")
//            sb.append("错误码:" + location.errorCode + "\n")
//            sb.append("错误信息:" + location.errorInfo + "\n")
//            sb.append("错误描述:" + location.locationDetail + "\n")
//            val locationModel=LocationModel(location.locationType,location.longitude,location.latitude,location.errorCode,location.errorInfo)
//        }
//        //定位之后的回调时间
//        sb.append("回调时间: " + formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n")
        return locationModel
    }

    private fun formatUTC(l: Long, strPattern: String): String {
        val sdf = SimpleDateFormat(strPattern, Locale.CHINA)
        return sdf.format(l)
    }

    /**
     * 判断是否开启了GPS或网络定位开关
     *
     * @return
     */
    fun isLocationProviderEnabled(context: Context): Boolean {
        var result = false
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            result = true
        }
        return result
    }

    fun showAlert(message: String, context: Context) {
        val alertDialog = AlertDialog.Builder(context).setMessage(message).setCancelable(false)
            .setPositiveButton(android.R.string.ok)
            { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .create()
        alertDialog.show()
    }
}