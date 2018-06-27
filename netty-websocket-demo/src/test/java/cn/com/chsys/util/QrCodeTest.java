package cn.com.chsys.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import cn.hutool.extra.qrcode.QrCodeUtil;

public class QrCodeTest {
	public static void main(String[] args) throws FileNotFoundException {
		String content ="http://10.1.2.111/netcourt/#zh-CN/phoneSign?idNumber=140429199502234410&signTokenId=0212c6c0-0bb5-4598-9f56-eba1aa195b39&timestamp=1529047344525&trialPlanId=45E1D949_9663_ABE3_581C_B6655D593F07&lastSigId=45E1D949_9663_ABE3_581C_B6655D593F07";
		generateQrCodeImage(content, 500, 500, "png", new FileOutputStream("D:/1.png"),true);
		String resultContent =  QrCodeUtil.decode(new File("D:/1.png"));
		System.out.println(resultContent);
		System.out.println(resultContent.equals(content));
	}
	public  static void  generateQrCodeImage(String content,int width,int height,String imageType,OutputStream out,boolean isNeed) {
		if(isNeed==true) {
			QrCodeUtil.generate(content, width, height, imageType,out);
		} 
	}
}
