package com.sloi.templater;

import java.util.Map;

public class StopTest extends Block {
	
	@Override
	public StringBuffer doProcess(Map<String, Object> scope) throws ParsingException {
		throw new ParsingException(this, "");
	}

}
