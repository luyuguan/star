/*
 * Copyright 2002-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baidu.jprotobuf.pbrpc.management;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.HashMap;
import java.util.Map;

import com.baidu.jprotobuf.pbrpc.transport.RpcServer;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpRequest;

/**
 * HTTP server handler for all request process.
 * 
 *
 * @author xiemalin
 * @since 3.1.0
 */
@Sharable
public class HttpServerInboundHandler extends ChannelInboundHandlerAdapter {

    /** The Constant DEFAULT_URI. */
    private static final String DEFAULT_URI = "/";

    /** The Constant STATUS_URI. */
    private static final String STATUS_URI = "/status";

    /** The request. */
    private HttpRequest request;

    /** The response mapping. */
    private Map<String, Object> responseMapping;

    /** The server status. */
    private ServerStatus serverStatus;

    /**
     * Instantiates a new http server inbound handler.
     *
     * @param rpcServer the rpc server
     */
    public HttpServerInboundHandler(Server rpcServer) {
        super();
        responseMapping = new HashMap<String, Object>();
        serverStatus = new ServerStatus(rpcServer);
        ServerStatus.Enabled();
        responseMapping.put(STATUS_URI, serverStatus);
    }

    /*
     * (non-Javadoc)
     * 
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelRead(io.netty.channel.ChannelHandlerContext,
     * java.lang.Object)
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            request = (HttpRequest) msg;
            
            String uri = request.getUri();
            if (DEFAULT_URI.equals(uri)) {
                uri = STATUS_URI;
            }
            
            Object responser = responseMapping.get(uri);
            if (responser != null) {
                writeResponse(ctx, responser.toString());
            } else  {
                writeResponse(ctx, "No such path '" + uri + "'");
            }

        }
    }
    
    /**
     * Write response.
     *
     * @param ctx the ctx
     * @param content the content
     * @throws Exception the exception
     */
    private void writeResponse(ChannelHandlerContext ctx, String content) throws Exception {
        FullHttpResponse response =
                new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(content.getBytes("UTF-8")));
        response.headers().set(CONTENT_TYPE, "text/html");
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        if (HttpHeaders.isKeepAlive(request)) {
            response.headers().set(CONNECTION, Values.KEEP_ALIVE);
        }
        ctx.write(response);
        ctx.flush();
        
    }

    /* (non-Javadoc)
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelReadComplete(io.netty.channel.ChannelHandlerContext)
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /* (non-Javadoc)
     * @see io.netty.channel.ChannelInboundHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    /**
     * Close.
     */
    public void close() {
        if (serverStatus != null) {
            serverStatus.close();
        }
        
    }
}
