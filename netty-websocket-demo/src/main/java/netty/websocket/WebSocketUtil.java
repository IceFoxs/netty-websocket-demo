package netty.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.StringUtil;

/**
 * 
 * WebSocket发送消息工具类
 * 
 * @author 赵云涛
 * {@link https://github.com/IceFoxs/ }
 *
 */
public class WebSocketUtil {

	private WebSocketUtil() {
	}

	/**
	 * 
	 * 向指定websocket发送字符串
	 * 
	 * @param idNumber
	 *            身份证号码
	 * @param message
	 *            消息内容，类型为String
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
	 * 向指定websocket发送二进制数据
	 * 
	 * @param idNumber
	 *            身份证号码
	 * @param message
	 *            消息内容，类型为 byte[]
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
