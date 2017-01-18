package com.sloi.formatters;

import com.sloi.formatter.Formatter;

public class IdentifierFormatter implements Formatter {

	@Override
	public StringBuffer doFormat(Object object) throws Exception {
		String id = object.toString();
		StringBuffer buffer = new StringBuffer(64);
		for (char c : id.toCharArray()) {
			if (Character.isUpperCase(c)) {
				if (buffer.length() > 0) {
					buffer.append('-');		
				}
			} 
			buffer.append(Character.toLowerCase(c));
		}
		return buffer;
	}

}
