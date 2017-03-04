package com.github.jeromerocheteau.templates;

import java.util.Map;

import com.github.jeromerocheteau.templater.TemplaterException;

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
	public StringBuffer doProcess(Map<String, Object> scope) throws TemplaterException {
		throw new TemplaterException(this, "");
	}

}
