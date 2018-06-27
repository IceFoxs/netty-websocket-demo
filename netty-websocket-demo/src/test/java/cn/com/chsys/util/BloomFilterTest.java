package cn.com.chsys.util;

import java.io.IOException;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.bloomfilter.BitSetBloomFilter;
import cn.hutool.bloomfilter.bitMap.IntMap;

public class BloomFilterTest {
	public static void bitMapBloomFilter(String[] args) {
		BitMapBloomFilter bitMapBloomFilter = new BitMapBloomFilter(100);
		bitMapBloomFilter.add("张三");
		Boolean b = bitMapBloomFilter.contains("张三");
		System.out.println(b);
        //System.out.println();
	}
	public static void bitSetBloomFilter(String[] args) {
		BitSetBloomFilter bitSetBloomFilter =new BitSetBloomFilter(100000,100000,10000);
		double db = bitSetBloomFilter.getFalsePositiveProbability();
		System.out.println("当前过滤器的错误率"+db);
		try {
			bitSetBloomFilter.init("1.txt","utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bitSetBloomFilter.add("张三");
		boolean a = bitSetBloomFilter.contains("张三");
		boolean b = bitSetBloomFilter.contains("李四");
		boolean c = bitSetBloomFilter.contains("王五");
		System.out.println("存在张三吗？"+(a==true?"存在":"不存在"));
		System.out.println("存在李四吗？"+(c==true?"存在":"不存在"));
		System.out.println("存在王五吗？"+(b==true?"存在":"不存在"));
	}
	public static void main(String[] args) {
		IntMap  intmap = new IntMap();
		for(int i=0;i<100;i++) {
			intmap.add(i);
		}
		for(int i=0;i<100;i++) {
			System.out.println(i+":"+(intmap.contains(i)==true?"存在":"不存在"));
		}
		System.out.println(100+":"+(intmap.contains(100)==true?"存在":"不存在"));
	    intmap.remove(99);
	    System.out.println(99+":"+(intmap.contains(99)==true?"存在":"不存在"));
	}
}
