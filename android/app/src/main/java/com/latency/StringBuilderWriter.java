package com.latency;

/**
 * Author: Sergei Dryganets
 * Copyright: Microsoft 2017
 * <p>
 * Helper class for Jackson Serializer
 * StringWriter is non optimal as it uses StringBuffer
 * As we don't need thread safety no reason to pay for it
 */
import java.io.IOException;
import java.io.Writer;

public class StringBuilderWriter extends Writer {

	private StringBuilder builder;

	public StringBuilderWriter() {
		builder = new StringBuilder(16);
	}

	public StringBuilderWriter(int initialSize) {
		if (initialSize < 0) {
			throw new IllegalArgumentException("initialSize < 0: " + initialSize);
		}
		builder = new StringBuilder(initialSize);
	}

	@Override
	public StringBuilderWriter append(char c) {
		write(c);
		return this;
	}

	@Override
	public StringBuilderWriter append(CharSequence csq) {
		if (csq == null) {
			csq = "null";
		}
		write(csq.toString());
		return this;
	}

	@Override
	public StringBuilderWriter append(CharSequence csq, int start, int end) {
		if (csq == null) {
			csq = "null";
		}
		String output = csq.subSequence(start, end).toString();
		write(output, 0, output.length());
		return this;
	}

	@Override
	public void close() throws IOException {
	}

	@Override
	public void flush() {
	}

	@Override
	public String toString() {
		return builder.toString();
	}

	@Override
	public void write(char[] chars, int offset, int count) {
		if (count == 0) {
			return;
		}
		builder.append(chars, offset, count);
	}

	@Override
	public void write(int oneChar) {
		builder.append((char) oneChar);
	}

	@Override
	public void write(String str) {
		builder.append(str);
	}

	@Override
	public void write(String str, int offset, int count) {
		String sub = str.substring(offset, offset + count);
		builder.append(sub);
	}
}

