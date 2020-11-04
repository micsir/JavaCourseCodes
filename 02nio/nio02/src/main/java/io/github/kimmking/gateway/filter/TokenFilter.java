package io.github.kimmking.gateway.filter;

import cn.hutool.core.convert.Convert;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class TokenFilter implements HttpRequestFilter{
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        HttpHeaders headers = fullRequest.trailingHeaders();

        if (!headers.contains("token")) {
            FullHttpResponse response = null;
            byte[] bytes = "deny access".getBytes();
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(bytes));
            response.headers().set("Content-Type", "application/json");
            response.setStatus(HttpResponseStatus.FORBIDDEN);
            response.headers().setInt("Content-Length", Convert.toInt(bytes.length));
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
        }
    }
}
