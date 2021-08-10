
import 'dart:async';

import 'package:flutter/services.dart';

class ExternalTexture {
  static const MethodChannel _channel = const MethodChannel('external_texture');

  int textureId = -1;

  bool get isInitialized => textureId != -1;

  Future<int> initialize(int width, int height) async {
      textureId = await _channel.invokeMethod("create", {
        'width': width,
        'height': height,
      });
      return textureId;
  }

  Future<void> dispose() => _channel.invokeMethod('dispose', {'textureId': textureId});
  // static Future<String?> get platformVersion async {
  //   final String? version = await _channel.invokeMethod('getPlatformVersion');
  //   return version;
  // }
}
