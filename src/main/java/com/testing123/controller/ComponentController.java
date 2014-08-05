package com.testing123.controller;

import com.testing123.ui.MainComponent;
import com.testing123.vaadin.GetData;
import com.vaadin.ui.GridLayout;

public class ComponentController {
	
	public static void drawMainComponent(GridLayout layout, UIState state, GetData data) {
		state.verifyState();
		if (layout.getComponent(1, 1) != null) {
			layout.removeComponent(1, 1);
		}
		MainComponent comp = new MainComponent(state, data);
		layout.addComponent(comp, 1, 1);
	}
}