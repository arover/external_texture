import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:external_texture/external_texture.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final externalTexture = ExternalTexture();

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    
    try { 
      
      await externalTexture.initialize(200,200);
      setState(() {
      });
    //   // await ExternalTexture.platformVersion ??
    //   //  'Unknown platform version';
    } on PlatformException {
    //   // platformVersion = 'Failed to get platform version.';
      print("platform exception");
    }

   
  }

  @override
  void dispose(){
    super.dispose();
    externalTexture.dispose();
  }
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Container(
          width: 200,
          height: 200,
          child: externalTexture.isInitialized
            ? Texture(textureId: externalTexture.textureId)
            : Container(color: Colors.black)
        ),
      ),
    );
  }
}
