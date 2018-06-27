package netty.websocket;

import java.util.Map;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
/**
 * 
 * @author 赵云涛
 * {@link }
 *
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
	/**
	 * WebSocket客户端缓存Map
	 */
	private final Map<String, Channel> map;

	public WebSocketChannelInitializer(Map<String, Channel> map) {
		this.map = map;
	}

	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		// HttpServerCodec: 针对http协议进行编解码
		pipeline.addLast("httpServerCodec", new HttpServerCodec());
		// ChunkedWriteHandler分块写处理，文件过大会将内存撑爆
		pipeline.addLast("chunkedWriteHandler", new ChunkedWriteHandler());
		/**
		 * 作用是将一个Http的消息组装成一个完成的HttpRequest或者HttpResponse，那么具体的是什么 取决于是请求还是响应,
		 * 该Handler必须放在HttpServerCodec后的后面
		 */
		pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(8192));
		// 用于处理websocket, /ws为访问websocket时的uri
		// pipeline.addLast("webSocketServerProtocolHandler", new
		// WebSocketServerProtocolHandler("/"));
		pipeline.addLast("myWebSocketHandler", new WebSocketCommonHandler(map));
		// pipeline.addLast("myTextSocketHandler", new MyWebSocketHandler());
	}

}
