package com.lyg.nettest;

import io.netty.buffer.ByteBuf;

public class ByteReader {

	private ByteBuf byteBuf;
	
	public ByteReader(ByteBuf byteBuf) {
		this.byteBuf = byteBuf;
	}
	
	public void wrap(ByteBuf byteBuf) {
		this.byteBuf = byteBuf;
	}
	
	public boolean readbool() {
		return (byteBuf.readByte() == 0?false:true);
	}
	
	public int readint() {
		return byteBuf.readInt();
	}
	
	public String readString() {
		int len = byteBuf.readInt();
		byte[] data = new byte[len];
		byteBuf.readBytes(data);
		String ret = new String(data);
		return ret;
	}
	
	public int[] readintArray() {
		int len = byteBuf.readInt();
		if(len == 0)
			return null;
		int[] ret = new int[len];
		for(int i = 0;i < len;i++) {
			ret[i] = byteBuf.readInt();
		}
		return ret;
	}
	
	public String[] readStringArray() {
		int len = byteBuf.readInt();
		if(len == 0)
			return null;
		String[] ret = new String[len];
		for(int i = 0;i < len;i++) {
			ret[i] = this.readString();
		}
		return ret;
	}
	
}
