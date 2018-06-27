package netty.websocket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import io.netty.channel.Channel;
/**
 * 通过idCardNumber,缓存每一个websocket客户端连接
 * @author 赵云涛
 * {@link https://github.com/IceFoxs/}
 */
public class WebSocketCache {
    /**
     * 缓存websocket的Map
     */
	private static Map<String, Channel> map = new ConcurrentHashMap<String, Channel>();
   
	/**
	 *私有化构造函数
	 */
	private WebSocketCache() {

	}
    /**
     * 返回 Map
     * @return Map<String, Channel>
     */
	public static Map<String, Channel> getMap() {
		return map;
	}
}
