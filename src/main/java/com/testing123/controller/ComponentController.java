package com.testing123.controller;

import com.testing123.ui.MainComponent;
import com.vaadin.ui.GridLayout;

public class ComponentController {
	
	public static void drawMainComponent(GridLayout layout, UIState state) {
		state.verifyState();
		if (layout.getComponent(1, 1) != null) {
			layout.removeComponent(1, 1);
		}
		MainComponent comp = new MainComponent(state.getStart(), state.getEnd(), state.getGrain());
		layout.addComponent(comp, 1, 1);
	}
}