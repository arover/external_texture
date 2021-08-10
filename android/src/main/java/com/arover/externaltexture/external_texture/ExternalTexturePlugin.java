package com.arover.externaltexture.external_texture;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.Surface;

import com.arover.arch.AppComponents;

import java.util.Map;

import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.view.TextureRegistry;

/**
 * ExternalTexturePlugin
 */
public class ExternalTexturePlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
    private static final String TAG = "ExternalTexturePlugin";
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;
    private TextureRegistry textureRegistry;
    TextureRegistry.SurfaceTextureEntry surfaceEntry;
    private Activity theActivity;
    Surface surface;
    private Context app;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "external_texture");
        channel.setMethodCallHandler(this);
        textureRegistry = flutterPluginBinding.getTextureRegistry();
        app = flutterPluginBinding.getApplicationContext();
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        Log.d(TAG, "onMethodCall()  method =" + call.method);
        if ("create".equals(call.method)) {
            TextureRegistry.SurfaceTextureEntry surfaceEntry = textureRegistry
                    .createSurfaceTexture();
            SurfaceTexture surfaceTexture = surfaceEntry.surfaceTexture();

            surface = new Surface(surfaceTexture);
            Map<String, Integer> params = call.arguments();
            int width = params.get("width");
            int height = params.get("height");
            if (app instanceof AppComponents) {
                ((AppComponents) app).playVideo(surface, width, height);
                Log.d(TAG, "onMethodCall success=" + surfaceEntry.id());
                result.success(surfaceEntry.id());
            } else {
                Log.d(TAG, "onMethodCall: app="+app);
                Log.d(TAG, "onMethodCall: error");
                result.error("100", "applicationContext is not AppComponents", null);
            }
            if (theActivity != null) {
                if (theActivity.getApplication() instanceof AppComponents) {
                    Log.d(TAG,
                            "onMethodCall: theActivity.getApplication() instanceof AppComponents");
                } else {
                    Log.d(TAG, "onMethodCall: theActivity.getApplication() is not AppComponents");
                }
            } else {
                Log.e(TAG, "onMethodCall: error activity is null");
            }
        } else if ("dispose".equals(call.method)) {
            if (app instanceof AppComponents) {
                ((AppComponents) app).stopVideo();
            }
            if (surfaceEntry != null) {
                surfaceEntry.release();
            }
            if (surface != null) {
                surface.release();
            }
            result.success(null);
        } else {
            Log.w(TAG, "onMethodCall()  notImplemented");
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }

    @Override public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        theActivity = binding.getActivity();
    }

    @Override public void onDetachedFromActivityForConfigChanges() {
        theActivity = null;
    }

    @Override public void onReattachedToActivityForConfigChanges(
            @NonNull ActivityPluginBinding binding) {
        theActivity = binding.getActivity();
    }

    @Override public void onDetachedFromActivity() {
        theActivity = null;
    }
}
