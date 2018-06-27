package netty.websocket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import io.netty.channel.Channel;
/**
 * ͨ��idCardNumber,����ÿһ��websocket�ͻ�������
 * @author ������
 * {@link}
 */
public class WebSocketCache {
    /**
     * ����websocket��Map
     */
	private static Map<String, Channel> map = new ConcurrentHashMap<String, Channel>();
   
	/**
	 *˽�л����캯��
	 */
	private WebSocketCache() {

	}
    /**
     * ���� Map
     * @return Map<String, Channel>
     */
	public static Map<String, Channel> getMap() {
		return map;
	}
}
