package com.sloi.templater;

import java.util.Map;

public class StartLoop extends Block {

	private String variable;

	private Expr iterator;
	
	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public Expr getIterator() {
		return iterator;
	}

	public void setIterator(Expr iterator) {
		this.iterator = iterator;
	}

	@Override
	public StringBuffer doProcess(Map<String, Object> scope) throws ParsingException {
		throw new ParsingException(this, "");
	}

}
