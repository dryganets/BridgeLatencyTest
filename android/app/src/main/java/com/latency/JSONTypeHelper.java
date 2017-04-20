package com.latency;

/**
 * Created by sergeyd on 4/11/17.
 */

import com.facebook.react.bridge.ReadableType;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Type mapping utility to map types inside of JSONObject/JSONArray to Bridge types
 */
public class JSONTypeHelper {

	public static ReadableType getType(Object object) {
		ReadableType type;
		if (object == null) {
			type = ReadableType.Null;
		} else if (object instanceof JSONArray) {
			type = ReadableType.Array;
		} else if (object instanceof JSONObject) {
			type = ReadableType.Map;
		} else if (object instanceof Number) {
			type = ReadableType.Number;
		} else if (object instanceof  String) {
			type = ReadableType.String;
		} else if (object instanceof Boolean) {
			type = ReadableType.Boolean;
		} else {
			throw new IllegalStateException("Unsupported type: " + object.getClass());
		}
		return type;
	}
}