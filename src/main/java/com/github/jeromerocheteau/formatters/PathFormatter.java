package com.github.jeromerocheteau.formatters;

import java.util.LinkedList;
import java.util.List;

import com.github.jeromerocheteau.formatter.Formatter;

public class PathFormatter implements Formatter {

	@Override
	public StringBuffer doFormat(Object object) throws Exception {
		String path = object.toString();
		StringBuffer buffer = new StringBuffer(64);
		List<StringBuffer> buffers = new LinkedList<StringBuffer>();
		StringBuffer current = new StringBuffer(16);
		for (char c : path.toCharArray()) {
			if (Character.isUpperCase(c)) {
				buffers.add(current);
				current = new StringBuffer(16);
			} 
			current.append(Character.toLowerCase(c));
		}
		buffers.add(current);
		for (int i = buffers.size() - 1; i >= 0; i--) {
			buffer.append('/');
			buffer.append(buffers.get(i));
		}
		return buffer;
	}

}
