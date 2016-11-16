package com.sloi.templater;

import java.util.Map;

public class Text extends Block {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public StringBuffer doProcess(Map<String, Object> scope) throws ParsingException {
		return new StringBuffer(value);
	}
	
}
