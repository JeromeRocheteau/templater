package com.sloi.tests;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sloi.templater.Templater;

public class SimpleTest {

	private Templater templater;
	
    @Before
    public void setUp() throws Exception {
    	InputStream stream = this.getClass().getResourceAsStream("/simple-test.template");
    	Reader reader = new InputStreamReader(stream);
        templater = new Templater(reader);
    }
 
    @After
    public void tearDown() throws Exception {
    }
	
	@Test
	public void doProcess() throws Exception {
		Map<String, Object> scope = new HashMap<String, Object>();
		scope.put("world", "World");
		StringBuffer buffer = templater.doProcess(scope);
		Assert.assertEquals(buffer.toString(), "Hello World!");
	}
	
}
