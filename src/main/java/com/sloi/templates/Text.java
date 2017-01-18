package com.sloi.templates;

import java.util.Map;

import com.sloi.templater.TemplaterException;

public class Text extends Block {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public StringBuffer doProcess(Map<String, Object> scope) throws TemplaterException {
		return new StringBuffer(value);
	}
	
}
