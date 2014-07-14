package com.testing123.controller;

import com.testing123.ui.MainComponent;
import com.vaadin.ui.GridLayout;

public class ComponentController {
	
	public static void drawMainComponent(GridLayout layout, UIState state) {
		MainComponent comp = new MainComponent(state.getStart(), state.getEnd(), state.getFileGrain());
		layout.addComponent(comp, 1, 1);
	}
	
	public static void drawLabels() {
		
	}
}
