package cn.com.chsys.util;

import java.nio.charset.Charset;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import cn.hutool.extra.ssh.Connector;
import cn.hutool.extra.ssh.JschUtil;

public class SSH2Test {
	public static void main(String[] args) {
       Connector connector = new Connector("10.50.7.173",22,"root", "dvs@admin");
       int port = JschUtil.openAndBindPortToLocal(connector, "10.50.7.172", 22);
	   System.out.println(port);
	   Session  session = JschUtil.getSession("10.50.7.172", 22,"root", "dvs@admin");
	   String result = JschUtil.exec(session, "ls  /",Charset.forName("utf-8"));
	   System.out.println(result);
	   result = JschUtil.exec(session, "touch /root/1.txt",Charset.forName("utf-8"));
	   System.out.println(result);
	}
}
