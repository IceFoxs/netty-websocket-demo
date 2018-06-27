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
 * @author ������
 * {@link }
 *
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
	/**
	 * WebSocket�ͻ��˻���Map
	 */
	private final Map<String, Channel> map;

	public WebSocketChannelInitializer(Map<String, Channel> map) {
		this.map = map;
	}

	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		// HttpServerCodec: ���httpЭ����б����
		pipeline.addLast("httpServerCodec", new HttpServerCodec());
		// ChunkedWriteHandler�ֿ�д�����ļ�����Ὣ�ڴ�ű�
		pipeline.addLast("chunkedWriteHandler", new ChunkedWriteHandler());
		/**
		 * �����ǽ�һ��Http����Ϣ��װ��һ����ɵ�HttpRequest����HttpResponse����ô�������ʲô ȡ��������������Ӧ,
		 * ��Handler�������HttpServerCodec��ĺ���
		 */
		pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(8192));
		// ���ڴ���websocket, /wsΪ����websocketʱ��uri
		// pipeline.addLast("webSocketServerProtocolHandler", new
		// WebSocketServerProtocolHandler("/"));
		pipeline.addLast("myWebSocketHandler", new WebSocketCommonHandler(map));
		// pipeline.addLast("myTextSocketHandler", new MyWebSocketHandler());
	}

}
