package com.szh.concurrent;
public class User implements Cloneable{
	private String name;
	private int age;
	
	public User(String name, int age) {
		this.name = name;
		this.age = age;
	}
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof User)) {
			return false;	
		}
		User user = (User)obj;
		//if(this.name==user.name && this.age==user.age)
		if(this.name.equals(user.name) 
			&& this.age==user.age) {
			return true;
		}
		else {
			return false;
		}
	}
	public int hashCode() {
		return name.hashCode() + age;
	}
	
	public String toString() {
		return "{name:'" + name + "',age:" + age + "}";
	}
	public Object clone()  {
		Object object = null;
		try {
			object = super.clone();
		} catch (CloneNotSupportedException e) {}
		return object;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	static int i;	static String i2;

	public static void main(String[] args) {
		//String str;
		// String [] str={"www","sss"};
		String a="1"+"";
		String b="1"+"";

		System.out.println(a.hashCode());		System.out.println(b.hashCode());

		//System.out.println(i);
	}
} 
