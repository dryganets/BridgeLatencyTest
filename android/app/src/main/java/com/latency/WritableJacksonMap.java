package com.latency;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.util.HashMap;


/**
 * Created by sergeyd on 4/14/17.
 */

public class WritableJacksonMap extends HashMap<String, Object> implements WritableMap {
	private static JsonFactory factory = new JsonFactory();

	@Override
	public void putNull(String key) {
		this.put(key, null);
	}

	@Override
	public void putBoolean(String key, boolean value) {
		this.put(key, value);
	}

	@Override
	public void putDouble(String key, double value) {
		this.put(key, value);
	}

	@Override
	public void putInt(String key, int value) {
		this.put(key, value);
	}

	@Override
	public void putString(String key, String value) {
		this.put(key, value);
	}

	@Override
	public void putArray(String key, WritableArray value) {
		this.put(key, value);
	}

	@Override
	public void putMap(String key, WritableMap value) {
		this.put(key, value);
	}

	@Override
	public void merge(ReadableMap source) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean hasKey(String name) {
		return containsKey(name);
	}

	@Override
	public boolean isNull(String name) {
		if (containsKey(name)) {
			return get(name) == null;
		}
		return false;
	}

	@Override
	public boolean getBoolean(String name) {
		return (boolean) get(name);
	}

	@Override
	public double getDouble(String name) {
		return (double) get(name);
	}

	@Override
	public int getInt(String name) {
		return (int) get(name);
	}

	@Override
	public String getString(String name) {
		return (String) get(name);
	}

	@Override
	public ReadableArray getArray(String name) {
		return (ReadableArray) get(name);
	}

	@Override
	public ReadableMap getMap(String name) {
		return (ReadableMap) get(name);
	}

	@Override
	public ReadableType getType(String name) {
		throw new RuntimeException(("getType is not implemented yet"));
	}

	@Override
	public ReadableMapKeySetIterator keySetIterator() {
		return new Iterator();
	}

	private class Iterator implements ReadableMapKeySetIterator {

		public java.util.Iterator<String> iter;

		public Iterator() {
			this. iter =WritableJacksonMap.this.keySet().iterator();
		}

		@Override
		public boolean hasNextKey() {
			return iter.hasNext();
		}

		@Override
		public String nextKey() {
			return iter.next();
		}
	}

	protected void serialize(String name, JsonGenerator generator) {
		try {
			if (name == null) {
				generator.writeStartObject();
			} else {
				generator.writeObjectFieldStart(name);
			}

		for (Entry<String, Object> entry : entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value == null) {
				generator.writeNullField(key);
			} else if (value instanceof String) {
				generator.writeStringField(key, (String) value);
			} else if (value instanceof WritableJacksonMap) {
				((WritableJacksonMap) value).serialize(key, generator);
			} else if (value instanceof  WritableJacksonArray) {
				((WritableJacksonArray) value).serialize(key, generator);
			} else if (value instanceof Double) {
				generator.writeNumberField(key, ((Double) value).doubleValue());
			} else if (value instanceof Integer) {
				generator.writeNumberField(key, ((Integer) value).intValue());
			} else if (value instanceof Boolean) {
				generator.writeBooleanField(key, ((Boolean) value).booleanValue());
			}
		}
		generator.writeEndObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String toString() {

		StringBuilderWriter writer = new StringBuilderWriter(256);
		//SegmentedStringWriter writer =  new SegmentedStringWriter(factory._getBufferRecycler());
		JsonGenerator generator = null;
		try {
			generator = factory.createGenerator(writer);
			serialize(null, generator);
			generator.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//return writer.getAndClear();
		return writer.toString();
	}
}
