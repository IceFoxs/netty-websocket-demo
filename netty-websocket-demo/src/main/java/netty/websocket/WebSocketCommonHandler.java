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
 * @author ������
 * {@link}
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
			System.out.println("���ͷ�:"+idnumber+",���������ݽ�������Ϊ��"+new String(buffer.array(), "utf-8"));
			String content = "��Ϣ�����ݲ�����";
			ByteBuf byteBuf = Unpooled.wrappedBuffer(content.getBytes("utf-8"));
			ctx.channel().writeAndFlush(new BinaryWebSocketFrame(byteBuf));
			showUsers();
		}
		if (msg instanceof TextWebSocketFrame) {
			Channel channel = ctx.channel();
			System.out.println("���ͷ�:"+idnumber+",�ı����ݽ�������Ϊ: " + ((TextWebSocketFrame) msg).text());
			channel.writeAndFlush(new TextWebSocketFrame("��Ϣ�����ݲ�����"));
			showUsers();
		}
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// System.out.println("ChannelId" + ctx.channel().id().asLongText());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		// System.out.println("�û�����: " + ctx.channel().id().asLongText());
		System.out.println("�û�����: " + idnumber);
		showUsers();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.channel().close();
	}

	private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
		Channel channel = ctx.channel();
		for (Entry<String, String> header : req.headers()) {
			System.out.println(header.getKey() + "��" + header.getValue());
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
		System.out.println("��ǰurl:" + uriPath);
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
		System.out.println("��ǰ����������" + map.size());
		for (Entry<String, Channel> u : map.entrySet()) {
			System.out.println("�û���" + u.getKey() + ",״̬��" + (u.getValue().isActive() ? "����" : "����"));
		}
	}

	private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
		// ����Ӧ����ͻ���
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
		// ����Ƿ�Keep-Alive���ر�����
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if (!isKeepAlive(req) || res.status().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}

	private static boolean isKeepAlive(FullHttpRequest req) {
		return false;
	}
}