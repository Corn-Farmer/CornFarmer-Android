package com.example.corn_farmer

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "e355d004b5a2901c09d0626d9f643ad9")
    }

}