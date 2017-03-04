package com.github.jeromerocheteau.templates;

import java.util.Map;

import com.github.jeromerocheteau.templater.TemplaterException;

public class StopTest extends Block {
	
	@Override
	public StringBuffer doProcess(Map<String, Object> scope) throws TemplaterException {
		throw new TemplaterException(this, "");
	}

}
