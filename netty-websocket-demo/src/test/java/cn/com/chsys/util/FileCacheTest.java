package cn.com.chsys.util;

import cn.hutool.cache.file.AbstractFileCache;
import cn.hutool.cache.file.LRUFileCache;

public class FileCacheTest {
	public static void main(String[] args) {
		LRUFileCache lruFileCache = new LRUFileCache(10, 4096, 10000);
		getInfo(lruFileCache);
		byte[] buffer = lruFileCache.getFileBytes("D:/1.txt");
		System.out.println(new String(buffer));
		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getInfo(lruFileCache);
		buffer = lruFileCache.getFileBytes("D:/1.txt");
		System.out.println(new String(buffer));
		getInfo(lruFileCache);
	}

	public static void getInfo(AbstractFileCache lruFileCache) {
		System.out.println("�����ļ�����" + lruFileCache.getCachedFilesCount());
		System.out.println("��ʹ�ÿռ��С��" + lruFileCache.getUsedSize());
		System.out.println("���������ļ������byte����" + lruFileCache.maxFileSize());
		System.out.println("����������" + lruFileCache.capacity());
	}
}
