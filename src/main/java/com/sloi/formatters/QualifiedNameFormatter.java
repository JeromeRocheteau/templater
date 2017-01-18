package com.sloi.formatters;

import com.sloi.formatter.Formatter;

public class QualifiedNameFormatter implements Formatter {

	@Override
	public StringBuffer doFormat(Object object) throws Exception {
		String name = object.toString();
		StringBuffer buffer = new StringBuffer(64);
		boolean flag = true;
		for (char c : name.toCharArray()) {
			if (flag) {
				flag = false;
				buffer.append(Character.toUpperCase(c));				
			} else if (c == '-' || c == '_' || c == ' ') {
				flag = true;
			} else {
				buffer.append(c);
			}
		}
		return buffer;
	}

}
