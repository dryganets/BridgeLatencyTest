package com.latency;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Author: Sergei Dryganets
 * Copyright: Microsoft 2017
 * <p>
 * JSON based WritableMap implementation
 */
public class WritableJSONMap implements WritableMap {

	private final JSONObject mObject;

	protected JSONObject getBackingObject() {
		return mObject;
	}

	public WritableJSONMap() {
		mObject = new JSONObject();
	}

	public WritableJSONMap(JSONObject object) {
		mObject = object;
	}

	@Override
	public void putNull(String key) {
		try {
			mObject.put(key, null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void putBoolean(String key, boolean value) {
		try {
			mObject.put(key, value);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void putDouble(String key, double value) {
		try {
			mObject.put(key, value);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void putInt(String key, int value) {
		try {
			mObject.put(key, value);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void putString(String key, String value) {
		try {
			mObject.put(key, value);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void putArray(String key, WritableArray value) {
		if (value instanceof WritableJSONArray) {
			try {
				WritableJSONArray array = (WritableJSONArray) value;
				mObject.put(key, array.getBackingArray());
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new IllegalArgumentException("value have to be instance of WritableJSONMap");
		}
	}

	@Override
	public void putMap(String key, WritableMap value) {

		if (value instanceof WritableJSONMap) {
			WritableJSONMap map = (WritableJSONMap) value;
			try {
				mObject.put(key, map.getBackingObject());
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new IllegalArgumentException("value have to be instance of WritableJSONMap");
		}

	}

	@Override
	public void merge(ReadableMap source) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean hasKey(String name) {
		return mObject.has(name);
	}

	@Override
	public boolean isNull(String name) {
		return mObject.isNull(name);
	}

	@Override
	public boolean getBoolean(String name) {
		try {
			return mObject.getBoolean(name);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public double getDouble(String name) {
		try {
			return mObject.getDouble(name);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int getInt(String name) {
		try {
			return mObject.getInt(name);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getString(String name) {
		try {
			return mObject.getString(name);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ReadableArray getArray(String name) {
		try {
			return isNull(name) ? null : new WritableJSONArray(mObject.getJSONArray(name));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ReadableMap getMap(String name) {
		try {
			return isNull(name) ? null : new WritableJSONMap(mObject.getJSONObject(name));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ReadableType getType(String name) {
		try {
			return JSONTypeHelper.getType(mObject.get(name));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ReadableMapKeySetIterator keySetIterator() {
		return new ReadableJsonMapKeysIterator(mObject);
	}

	private static class ReadableJsonMapKeysIterator implements ReadableMapKeySetIterator {
		Iterator<String> iterator;

		ReadableJsonMapKeysIterator(JSONObject object) {
			iterator = object.keys();
		}

		@Override
		public boolean hasNextKey() {
			return iterator.hasNext();
		}

		@Override
		public String nextKey() {
			return iterator.next();
		}
	}

	@Override
	public String toString() {
		return mObject.toString();
	}
}
