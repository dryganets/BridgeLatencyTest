package com.latency;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sergeyd on 4/14/17.
 */

public class WritableJacksonArray extends ArrayList<Object> implements WritableArray {

	public WritableJacksonArray() {
		super();
	}

	public WritableJacksonArray(int size) {
		super(size);
	}

	@Override
	public void pushNull() {
		add(null);
	}

	@Override
	public void pushBoolean(boolean value) {
		add(value);
	}

	@Override
	public void pushDouble(double value) {
		add(value);
	}

	@Override
	public void pushInt(int value) {
		add(value);
	}

	@Override
	public void pushString(String value) {
		add(value);
	}

	@Override
	public void pushArray(WritableArray array) {
		add(array);
	}

	@Override
	public void pushMap(WritableMap map) {
		add(map);
	}

	@Override
	public boolean isNull(int index) {
		return get(index) == null;
	}

	@Override
	public boolean getBoolean(int index) {
		return (boolean) get(index);
	}

	@Override
	public double getDouble(int index) {
		return (double) get(index);
	}

	@Override
	public int getInt(int index) {
		return (int) get(index);
	}

	@Override
	public String getString(int index) {
		return (String) get(index);
	}

	@Override
	public ReadableArray getArray(int index) {
		return (ReadableArray) get(index);
	}

	@Override
	public ReadableMap getMap(int index) {
		return (ReadableMap) get(index);
	}

	@Override
	public ReadableType getType(int index) {
		throw  new RuntimeException("getType is not implemented yet");
	}

	protected void serialize(String name, JsonGenerator generator) {
		int length = size();
		try {
			if (name == null) {
				generator.writeStartArray();
			} else {
				generator.writeArrayFieldStart(name);
			}

			for (int i = 0; i < length; i++) {
				Object value = get(i);
				if (value == null) {
					generator.writeNull();
				} else if (value instanceof WritableJacksonMap) {
					((WritableJacksonMap) value).serialize(null, generator);
				} else if (value instanceof WritableJacksonArray) {
					((WritableJacksonArray) value).serialize(null, generator);
				} else if (value instanceof Double) {
					generator.writeNumber(((Double) value).doubleValue());
				} else if (value instanceof Integer) {
					generator.writeNumber(((Integer) value).intValue());
				} else if (value instanceof Boolean) {
					generator.writeBoolean(((Boolean) value).booleanValue());
				}
			}
			generator.writeEndArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
