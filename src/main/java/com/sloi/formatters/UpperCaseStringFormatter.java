package com.sloi.formatters;

import com.sloi.formatter.Formatter;

public class UpperCaseStringFormatter implements Formatter {
	
	@Override
	public StringBuffer doFormat(Object object) throws Exception {
		String string = object.toString();
		return new StringBuffer(string.toUpperCase());
	}

}
