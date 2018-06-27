package netty.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.StringUtil;

/**
 * 
 * WebSocket������Ϣ������
 * 
 * @author ������
 * {@link }
 *
 */
public class WebSocketUtil {

	private WebSocketUtil() {
	}

	/**
	 * 
	 * ��ָ��websocket�����ַ���
	 * 
	 * @param idNumber
	 *            ���֤����
	 * @param message
	 *            ��Ϣ���ݣ�����ΪString
	 * 
	 */
	public static void send(String idNumber, String message) {
		if (StringUtil.isNullOrEmpty(idNumber)) {
			new IllegalArgumentException("idNumber is Null or Empty!");
		}
		if (StringUtil.isNullOrEmpty(message)) {
			new IllegalArgumentException("message is Null or Empty!");
		}
		Channel channel = WebSocketCache.getMap().get(idNumber);
		if (channel != null && channel.isActive()) {
			channel.writeAndFlush(new TextWebSocketFrame(message));
		}
	}
	/**
	 * 
	 * ��ָ��websocket���Ͷ���������
	 * 
	 * @param idNumber
	 *            ���֤����
	 * @param message
	 *            ��Ϣ���ݣ�����Ϊ byte[]
	 * 
	 */
	public static void send(String idNumber, byte[] message) {
		if (StringUtil.isNullOrEmpty(idNumber)) {
			new IllegalArgumentException("idNumber is null or empty!");
		}
		if (message == null || message.length == 0) {
			new IllegalArgumentException("message is null or length is zero !");
		}
		Channel channel = WebSocketCache.getMap().get(idNumber);
		if (channel != null && channel.isActive()) {
			ByteBuf byteBuf = Unpooled.wrappedBuffer(message);
			channel.writeAndFlush(new BinaryWebSocketFrame(byteBuf));
		}
	}
}
