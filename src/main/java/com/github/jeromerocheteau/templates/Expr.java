package com.github.jeromerocheteau.templates;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import com.github.jeromerocheteau.templater.TemplaterException;

public class Expr extends Block {

	private String[] path;

	public String[] getPath() {
		return path;
	}

	public void setPath(String[] path) {
		this.path = path;
	}
	
	@Override
	public StringBuffer doProcess(Map<String, Object> scope) throws TemplaterException {
		Object value = this.getValue(scope);
		StringBuffer builder = new StringBuffer();
		if (value != null) builder.append(value);
		return builder;
	}
	
	public Object getValue(Map<String, Object> scope) throws TemplaterException {
		if (path.length > 1) {
			Object object = scope.get(path[0]);
			String[] path = Arrays.copyOfRange(this.path, 1, this.path.length);
			try {
				return this.doGet(object, path);
			} catch (Exception e) {
				e.printStackTrace();
				throw new TemplaterException(this, e.getMessage());
			}
		} else {
			return scope.get(this.path[0]);
		}
	}
	
	private Object doGet(Object object, String[] path) throws Exception {		
		if (path.length > 0) {
			return doGet(object, path, 0, path.length - 1);
		} else {
			return object;
		}
	}
	
	private Object doGet(Object object, String[] path, int i, int j) throws Exception {
		String name = path[i];
		if (i < j) {
			Method getter = this.getGetter(object, name);
			try {
				getter.setAccessible(true);
				Object obj = getter.invoke(object);
				return this.doGet(obj, path, i+1, j);
			} catch (Exception e) {
				throw e;
			}
		} else {
			return this.doEject(object, name);
		}
	}
	
	private Object doEject(Object object, String name) throws TemplaterException {
		Method getter = this.getGetter(object, name);
		try {
			getter.setAccessible(true);
			Object value = getter.invoke(object);
			return value;
		} catch (Exception e) {
			throw new TemplaterException(this, e.getMessage());
		}
	}

	private Method getGetter(Object object, String name) throws TemplaterException {
		try {
			if (object == null) {
				throw new NullPointerException(name);
			} else {
				String getName = "get" + name.substring(0,1).toUpperCase() + name.substring(1);
				Class<?> type = object.getClass();
				Method getMethod = this.getMethod(type, getName);
				return getMethod;
			}
		} catch (NullPointerException e) {
			throw new TemplaterException(this, e.getMessage());
		} catch (Exception e) {
			throw new TemplaterException(this, "field '" + name + "' not found in class '" + object.getClass() + "'");
		}
	}

	private Method getMethod(Class<?> type, String name) throws Exception {
		if (type == null) {
			throw new Exception();
		} else {
			Method method = type.getMethod(name);
			if (method == null) {
				return this.getMethod(type.getSuperclass(), name);
			} else {
				return method;
			}
		}
	}
	
}
