package cn.com.chsys.util;

import java.lang.reflect.Field;

import cn.hutool.core.bean.BeanDesc;

public class BeanTest {
	private String address;
	private int age;
    
	public BeanTest(String address, int age) {
		super();
		this.address = address;
		this.age = age;
	}

	public static void main(String[] args) {
          BeanDesc beanDesc = new BeanDesc(BeanTest.class); 
          System.out.println(beanDesc.getSimpleName());
          Field  field = beanDesc.getField("age");
          System.out.println(field.getType());
          beanDesc.getProp("age");
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

}
