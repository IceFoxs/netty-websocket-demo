package cn.com.chsys.util;

import cn.hutool.aop.aspects.TimeIntervalAspect;

public class AspectTest {
	private String message;
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, Throwable {
		AspectTest  dt =new AspectTest("哈kimmy!.....");
		TimeIntervalAspect tia = new TimeIntervalAspect(dt);
		tia.invoke(dt,dt.getClass().getMethod("sendMessage",null) , null);
		tia.before(dt,dt.getClass().getMethod("sendMessage",null) , null);
		tia.after(dt,dt.getClass().getMethod("sendMessage",String.class) ,new Object[] {"Job"});
		tia.invoke(dt,dt.getClass().getMethod("sendMessage",String.class,String.class) ,new Object[] {"kitty","22��"});
	}
	
	public AspectTest() {
	}

	public AspectTest(String message) {
		this.message = message;
	}

	public void sendMessage() {
		System.out.println("������Ϣ��"+message);
	}
	public void sendMessage(String user) {
		System.out.println(user+"������Ϣ��"+message);
	}
	public void sendMessage(String user,String age) {
		System.out.println(user+" "+age+"  "+"������Ϣ��"+message);
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
}
