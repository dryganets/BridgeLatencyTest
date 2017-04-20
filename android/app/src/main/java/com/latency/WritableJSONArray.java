package com.latency;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Author: Sergei Dryganets
 * Copyright: Microsoft 2017
 * <p>
 * JSON based WritableArray implementation
 */
public class WritableJSONArray implements WritableArray {

	JSONArray mArray;

	protected JSONArray getBackingArray() {
		return mArray;
	}

	public WritableJSONArray() {
		mArray = new JSONArray();
	}

	public WritableJSONArray(JSONArray array) {
		mArray = array;
	}

	@Override
	public void pushNull() {
		mArray.put(null);
	}

	@Override
	public void pushBoolean(boolean value) {
		mArray.put(value);
	}

	@Override
	public void pushDouble(double value) {
		try {
			mArray.put(value);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void pushInt(int value) {
		mArray.put(value);
	}

	@Override
	public void pushString(String value) {
		mArray.put(value);
	}

	@Override
	public void pushArray(WritableArray value) {
		if (value instanceof WritableJSONArray) {
			WritableJSONArray array = (WritableJSONArray) value;
			mArray.put(array.getBackingArray());
		} else {
			throw new IllegalArgumentException("Expected WritableJSONArray got " + value.getClass());
		}
	}

	@Override
	public void pushMap(WritableMap value) {
		if (value instanceof WritableJSONMap) {
			WritableJSONMap map = (WritableJSONMap) value;
			mArray.put(map.getBackingObject());
		} else {
			throw new IllegalArgumentException("Expected WritebleJSONMap got " + value.getClass());
		}
	}

	@Override
	public int size() {
		return mArray.length();
	}

	@Override
	public boolean isNull(int index) {
		return mArray.isNull(index);
	}

	@Override
	public boolean getBoolean(int index) {
		try {
			return mArray.getBoolean(index);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public double getDouble(int index) {
		try {
			return mArray.getDouble(index);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int getInt(int index) {
		try {
			return mArray.getInt(index);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getString(int index) {
		try {
			return mArray.getString(index);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ReadableArray getArray(int index) {
		try {
			return isNull(index) ? null : new WritableJSONArray(mArray.getJSONArray(index));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ReadableMap getMap(int index) {
		try {
			return isNull(index) ? null : new WritableJSONMap(mArray.getJSONObject(index));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ReadableType getType(int index) {
		try {
			return JSONTypeHelper.getType(mArray.get(index));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
