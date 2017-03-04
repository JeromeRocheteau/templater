package com.github.jeromerocheteau.templates;

import java.util.Map;

import com.github.jeromerocheteau.templater.TemplaterException;

public class StartTest extends Block {

	private Boolean not;

	public Boolean getNot() {
		return not;
	}

	public void setNot(Boolean not) {
		this.not = not;
	}
	
	private Expr expr;
	
	public Expr getExpr() {
		return expr;
	}

	public void setExpr(Expr expr) {
		this.expr = expr;
	}

	@Override
	public StringBuffer doProcess(Map<String, Object> scope) throws TemplaterException {
		throw new TemplaterException(this, "");
	}

}
