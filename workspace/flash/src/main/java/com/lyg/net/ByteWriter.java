package com.lyg.net;

import io.netty.buffer.ByteBuf;

public class ByteWriter {

	private ByteBuf byteBuf;
	
	public ByteWriter(ByteBuf byteBuf) {
		this.byteBuf = byteBuf;
	}
	
	public void wrap(ByteBuf byteBuf) {
		this.byteBuf = byteBuf;
	}
	
	public void writebool(boolean bool) {
		byteBuf.writeByte(bool == false?0:1);
	}
	
	public void writeint(int value) {
		byteBuf.writeInt(value);
	}
	
	public void writeString(String str) {
		if(str == null || str.length() == 0) {
			byteBuf.writeInt(0);
			return;
		}
		byte[] strArray = str.getBytes();
		byteBuf.writeInt(strArray.length);
		byteBuf.writeBytes(strArray);
		
	}
	
	public void writeintArray(int[] value) {
		if(value == null || value.length == 0) {
			byteBuf.writeInt(0);
			return;
		}
		byteBuf.writeInt(value.length);
		for(int i = 0;i < value.length;i++) {
			byteBuf.writeInt(value[i]);
		}
	}
	
	public void writeStringArray(String[] value) {
		if(value == null || value.length == 0) {
			byteBuf.writeInt(0);
			return;
		}
		byteBuf.writeInt(value.length);
		for(int i = 0;i < value.length;i++) {
			this.writeString(value[i]);
		}
	}
}
