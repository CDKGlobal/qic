package com.testing123.ui;

import com.testing123.controller.UIState;
import com.testing123.vaadin.GetData;
import com.vaadin.ui.AbsoluteLayout;

public class ComponentController {
	
	public static void drawMainComponent(AbsoluteLayout layout, UIState state, GetData data) {
		state.verifyState();
		MainComponent comp = new MainComponent(state, data);
		layout.removeAllComponents();
		layout.addComponent(comp);
	}
}