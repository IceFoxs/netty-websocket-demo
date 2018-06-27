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
		System.out.println("缓存文件数：" + lruFileCache.getCachedFilesCount());
		System.out.println("已使用空间大小：" + lruFileCache.getUsedSize());
		System.out.println("允许被缓存文件的最大byte数：" + lruFileCache.maxFileSize());
		System.out.println("缓存容量：" + lruFileCache.capacity());
	}
}
