package netty.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

public class MyWebSocketHandler3 extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
//		/Channel channel = ctx.channel();
		System.out.println(new String(msg.content().copy().array(),"utf-8"));
		String str = "{\"jpc\":{\"commandType\":\"jpclogin\",\"idCardNumber\":\"140429199502234410\",\"cellNumber\":\"15735105261\",\"verificationCode\":\"151615\",\"loginType\":\"1\",\"appId\":\"15165\",\"courtCode\":\"0\"}}";
		ByteBuf byteBuf =Unpooled.wrappedBuffer(str.getBytes("utf-8"));
		ctx.channel().writeAndFlush(new BinaryWebSocketFrame(byteBuf));
		
		//ctx.channel().writeAndFlush(msg);
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ChannelId" + ctx.channel().id().asLongText());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("用户下线: " + ctx.channel().id().asLongText());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.channel().close();
	}
}