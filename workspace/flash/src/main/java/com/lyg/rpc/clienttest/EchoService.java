package com.lyg.rpc.clienttest;

import com.baidu.jprotobuf.pbrpc.ProtobufRPC;
import com.lyg.rpc.test.EchoInfo;

public interface EchoService {

    /**
     * To define a RPC client method. <br>
     * serviceName is "echoService"
     * methodName is use default method name "echo"
     * onceTalkTimeout is 200 milliseconds
     * 
     * @param info
     * @return
     */
    @ProtobufRPC(serviceName = "echoService", onceTalkTimeout = 20000)
    EchoInfo echo(EchoInfo info);
}