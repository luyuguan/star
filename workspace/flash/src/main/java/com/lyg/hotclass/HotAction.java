package com.lyg.hotclass;

import com.lyg.hotload.IAction;

public class HotAction implements IAction {

	@Override
	public void invoke(Object param) {
		System.out.println("hotaction223");
	}
}
