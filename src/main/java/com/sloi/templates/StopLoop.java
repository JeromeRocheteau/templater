package com.sloi.templates;

import java.util.Map;

import com.sloi.templater.TemplaterException;

public class StopLoop extends Block {

	@Override
	public StringBuffer doProcess(Map<String, Object> scope) throws TemplaterException {
		throw new TemplaterException(this, "");
	}

}
