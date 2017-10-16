package com.lyg.hotclass;

import com.lyg.hotload.IAction;

public class HotAction2 implements IAction {

	@Override
	public void invoke(Object param) {
		System.out.println("HotAction2");
	}
}
