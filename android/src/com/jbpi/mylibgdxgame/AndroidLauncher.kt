package com.jbpi.mylibgdxgame

import android.os.Bundle

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.jbpi.mylibgdxgame.MyGdxGame

class AndroidLauncher : AndroidApplication() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val config = AndroidApplicationConfiguration()
        config.useAccelerometer = false
        config.useCompass = false

        initialize(MyGdxGame(), config)
    }
}
