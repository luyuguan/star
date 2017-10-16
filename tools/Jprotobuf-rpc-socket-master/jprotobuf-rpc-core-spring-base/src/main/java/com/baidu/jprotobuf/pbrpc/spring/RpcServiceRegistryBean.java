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
package com.baidu.jprotobuf.pbrpc.spring;

import java.lang.reflect.Method;

import com.baidu.jprotobuf.pbrpc.ProtobufRPCService;
import com.baidu.jprotobuf.pbrpc.RpcHandler;
import com.baidu.jprotobuf.pbrpc.server.RpcServiceRegistry;

/**
 * Supports {@link RemoteInvocationExecutor} parse.
 *
 * @author xiemalin
 * @since 2.17
 */
public class RpcServiceRegistryBean extends RpcServiceRegistry {

    /**
     * Instantiates a new rpc service registry bean.
     */
    public RpcServiceRegistryBean() {
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.baidu.jprotobuf.pbrpc.server.RpcServiceRegistry#doCreateRpcHandler
     * (java.lang.reflect.Method, java.lang.Object,
     * com.baidu.jprotobuf.pbrpc.ProtobufPRCService)
     */
    @Override
    protected RpcHandler doCreateRpcHandler(Method method, 
            Object service, ProtobufRPCService protobufPRCService) {
        
        RpcHandler handler = super.doCreateRpcHandler(method, service, protobufPRCService);
        return handler;
    }
}
