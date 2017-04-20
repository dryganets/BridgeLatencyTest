package com.latency;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by sergeyd on 11/28/16.
 */

public class LatencyDelayTestPackage implements ReactPackage {

  final static int OBJECTS_COUNT = 100;
  @Override
  public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
    return Arrays.<NativeModule>asList(new LatencyModule(reactContext));
  }

  @Override
  public List<Class<? extends JavaScriptModule>> createJSModules() {
    return Collections.emptyList();

  }

  @Override
  public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
    return Collections.emptyList();
  }

  private static class LatencyModule extends ReactContextBaseJavaModule {

    public LatencyModule(ReactApplicationContext reactContext) {
      super(reactContext);
    }

    @Override
    public String getName() {
      return "LatencyTest";
    }


    @Override
    public void initialize() {

    }

    enum Type {
      BIN,
      JACKSON,
      JSON
    }

    public WritableMap newMap(Type type) {
      switch (type) {
        case BIN:
          return new WritableNativeMap();
        case JSON:
          return new WritableJSONMap();
        case JACKSON:
          return new WritableJacksonMap();
        default:
          throw new IllegalStateException();
      }
    }

    public WritableArray newArray(Type type, int size) {
      switch (type) {
        case BIN:
          return new WritableNativeArray();
        case JSON:
          return new WritableJSONArray();
        case JACKSON:
          return new WritableJacksonArray(size);
        default:
          throw new IllegalStateException();
      }
    }

    public void test(Type type, Promise promise) {
      double startSerialization = performanceNow();
      WritableMap map = newMap(type);
      WritableArray array = newArray(type, OBJECTS_COUNT);
      map.putDouble("start", startSerialization);
      for (int i = 0; i < OBJECTS_COUNT; i++) {
        WritableMap object = newMap(type);
        object.putInt("id", i);
        object.putString("name", "Sergey Dryganets");
        object.putString("address", "Some street some address");
        object.putDouble("salary", 1110.33);
        object.putBoolean("smoking", false);
        array.pushMap(object);
      }

      map.putArray("data", array);
      Object result = null;

      if (type == Type.BIN) {
        result = map;
      } else {
        result = map.toString();
      }

      Log.i(ReactConstants.TAG, "java serialization time" + (performanceNow() - startSerialization));

      promise.resolve(result);
    }



    @ReactMethod
    public void testLatency(Promise promise) {
      test(Type.BIN, promise);
    }

    @ReactMethod
    public void testLatencyJacksonString(Promise promise) {
     test(Type.JACKSON, promise);
    }

    @ReactMethod
    public void testLatencyJSONString(Promise promise) {
      test(Type.JSON, promise);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private double performanceNow() {
      return android.os.SystemClock.elapsedRealtimeNanos() / 1000000.;
    }

    @Override
    public boolean canOverrideExistingModule() {
      return false;
    }

    @Override
    public void onCatalystInstanceDestroy() {

    }

    @Override
    public boolean supportsWebWorkers() {
      return false;
    }
  }
}
