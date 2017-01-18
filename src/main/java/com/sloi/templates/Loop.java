package com.sloi.templates;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.sloi.templater.TemplaterException;

public class Loop extends Block implements Templatable {

	private String variable;

	private Expr iterator;
	
	private Template template;
	
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

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	@Override
	public StringBuffer doProcess(Map<String, Object> scope) throws TemplaterException {
		StringBuffer builder = new StringBuffer(2048);		
		Object value = iterator.getValue(scope);
		if (value instanceof Iterable<?>) {
			Iterable<?> iter = (Iterable<?>) value;
			Iterator<?> it = iter.iterator();
			int size = this.getSize(iter);
			this.doIterate(scope, builder, it, size);
		} else if (value instanceof Map<?,?>) {
			Map<?, ?> map = (Map<?,?>) value;
			Set<?> set = map.entrySet();
			Iterator<?> it = set.iterator();
			int size = set.size();
			this.doIterate(scope, builder, it, size);
		}
		return builder;
	}

	private void doIterate(Map<String, Object> scope, StringBuffer builder, Iterator<?> it, int size) throws TemplaterException {
		scope.put(variable + "-size", size);
		scope.put(variable + "-first", true);
		scope.put(variable + "-last", false);
		int index = 1;
		while (it.hasNext()) {
			scope.put(variable + "-index", index++);
			Object val = it.next();
			scope.put(variable, val);
			builder.append(template.doProcess(scope));
			scope.put(variable + "-first", false);
			scope.put(variable + "-last", index == size);
		}
		scope.remove(variable);
		scope.remove(variable + "-first");
		scope.remove(variable + "-index");
		scope.remove(variable + "-last");
		scope.remove(variable + "-size");
	}

	private int getSize(Iterable<?> iter) {
		int size = 0;
		Iterator<?> it = iter.iterator();
		while (it.hasNext()) {
			it.next();
			size++;
		}
		return size;
	}
	
}
