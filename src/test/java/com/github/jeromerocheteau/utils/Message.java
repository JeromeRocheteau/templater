package com.github.jeromerocheteau.utils;

public class Message {
	
	private String hello;
	
	private String world;

	public String getHello() {
		return hello;
	}

	public void setHello(String hello) {
		this.hello = hello;
	}

	public String getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = world;
	}
	
	public Message(String hello, String world) {
		this.setHello(hello);
		this.setWorld(world);
	}
	
}