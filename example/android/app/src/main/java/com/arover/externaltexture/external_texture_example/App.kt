package com.arover.externaltexture.external_texture_example

import android.app.Application
import android.util.Log
import android.view.Surface
import com.arover.arch.AppComponents

/**
 *
 * @author arover
 * created at 8/11/21 2:32 AM
 */
class App : Application(), AppComponents{
    companion object {
        private const val TAG = "App"
    }
    override fun playVideo(surface: Surface?, width: Int, height: Int) {
        Log.d(TAG, "playVideo() called with: surface = $surface, width = $width, height = $height")
    }

    override fun stopVideo() {
        Log.d(TAG, "stopVideo() called")
    }

}