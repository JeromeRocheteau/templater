package com.sloi.formatters;

import com.sloi.formatter.Formatter;

public class CapitalizedStringFormatter implements Formatter {

	@Override
	public StringBuffer doFormat(Object object) throws Exception {
		String string = object.toString();
		StringBuffer buffer = new StringBuffer(string);
		buffer.replace(0, 1, buffer.substring(0, 1).toUpperCase());
		return buffer;
	}

}
