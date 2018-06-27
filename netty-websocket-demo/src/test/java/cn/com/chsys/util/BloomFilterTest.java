package cn.com.chsys.util;

import java.io.IOException;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.bloomfilter.BitSetBloomFilter;
import cn.hutool.bloomfilter.bitMap.IntMap;

public class BloomFilterTest {
	public static void bitMapBloomFilter(String[] args) {
		BitMapBloomFilter bitMapBloomFilter = new BitMapBloomFilter(100);
		bitMapBloomFilter.add("������");
		Boolean b = bitMapBloomFilter.contains("������");
		System.out.println(b);
        //System.out.println();
	}
	public static void bitSetBloomFilter(String[] args) {
		BitSetBloomFilter bitSetBloomFilter =new BitSetBloomFilter(100000,100000,10000);
		double db = bitSetBloomFilter.getFalsePositiveProbability();
		System.out.println("��ǰ�������Ĵ�����"+db);
		try {
			bitSetBloomFilter.init("1.txt","utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bitSetBloomFilter.add("������");
		boolean a = bitSetBloomFilter.contains("������");
		boolean b = bitSetBloomFilter.contains("����");
		boolean c = bitSetBloomFilter.contains("�����");
		System.out.println("������������"+(a==true?"����":"������"));
		System.out.println("�����������"+(c==true?"����":"������"));
		System.out.println("����������"+(b==true?"����":"������"));
	}
	public static void main(String[] args) {
		IntMap  intmap = new IntMap();
		for(int i=0;i<100;i++) {
			intmap.add(i);
		}
		for(int i=0;i<100;i++) {
			System.out.println(i+":"+(intmap.contains(i)==true?"����":"������"));
		}
		System.out.println(100+":"+(intmap.contains(100)==true?"����":"������"));
	    intmap.remove(99);
	    System.out.println(99+":"+(intmap.contains(99)==true?"����":"������"));
	}
}
