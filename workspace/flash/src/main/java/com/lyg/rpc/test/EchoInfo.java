package com.lyg.rpc.test;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

public class EchoInfo {
	  @Protobuf(order=1)
	    public String message;
}
