package netty.websocket;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Map.Entry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
/**
 * 
 * @author 赵云涛
 * {@link https://github.com/IceFoxs/}
 *
 */

public class WebSocketCommonHandler extends SimpleChannelInboundHandler<Object> {
	private WebSocketServerHandshaker handshaker;
	private final Map<String, Channel> map;
	private String idnumber;

	public WebSocketCommonHandler(Map<String, Channel> map) {
		this.map = map;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof FullHttpRequest) {
			handleHttpRequest(ctx, (FullHttpRequest) msg);
		}
		if (msg instanceof BinaryWebSocketFrame) {
			ByteBuf buf = ((BinaryWebSocketFrame) msg).content();
			int capacity = buf.capacity();
			ByteBuffer buffer = ByteBuffer.allocate(buf.capacity());
			for (int i = 0; i < capacity; i++) {
				byte b = buf.getByte(i);
				buffer.put(b);
			}
			System.out.println("发送方:"+idnumber+",二进制数据接受内容为："+new String(buffer.array(), "utf-8"));
			String content = "消息内容暂不处理";
			ByteBuf byteBuf = Unpooled.wrappedBuffer(content.getBytes("utf-8"));
			ctx.channel().writeAndFlush(new BinaryWebSocketFrame(byteBuf));
			showUsers();
		}
		if (msg instanceof TextWebSocketFrame) {
			Channel channel = ctx.channel();
			System.out.println("发送方:"+idnumber+",文本数据接受内容为: " + ((TextWebSocketFrame) msg).text());
			channel.writeAndFlush(new TextWebSocketFrame("消息内容暂不处理"));
			showUsers();
		}
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// System.out.println("ChannelId" + ctx.channel().id().asLongText());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		// System.out.println("用户下线: " + ctx.channel().id().asLongText());
		System.out.println("用户下线: " + idnumber);
		showUsers();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.channel().close();
	}

	private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
		Channel channel = ctx.channel();
		for (Entry<String, String> header : req.headers()) {
			System.out.println(header.getKey() + "：" + header.getValue());
		}
		if (!req.decoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
			sendHttpResponse(ctx, req,
					new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
		}
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://127.0.0.1", null,
				false);
		handshaker = wsFactory.newHandshaker(req);
		if (!req.uri().startsWith("/websocket/")) {
			WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
			return;
		}
		String uriPath = req.uri().substring(11);
		idnumber = uriPath;
		System.out.println("当前url:" + uriPath);
		if (handshaker == null) {
			WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
			// WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
		} else {
			Channel c = map.get(idnumber);
			if (c != null) {
				c.close();
			}
			handshaker.handshake(ctx.channel(), req);
			map.put(idnumber, channel);
			showUsers();
		}
	}

	private void showUsers() {
		System.out.println("当前在线人数：" + map.size());
		for (Entry<String, Channel> u : map.entrySet()) {
			System.out.println("用户：" + u.getKey() + ",状态：" + (u.getValue().isActive() ? "在线" : "离线"));
		}
	}

	private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
		// 返回应答给客户端
		if (res.status().code() != 200) {
			String str = "hello";
			ByteBuf buf = null;
			try {
				buf = Unpooled.wrappedBuffer(str.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			res.content().writeBytes(buf);
			buf.release();
		}
		// 如果是非Keep-Alive，关闭连接
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if (!isKeepAlive(req) || res.status().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}

	private static boolean isKeepAlive(FullHttpRequest req) {
		return false;
	}
}
