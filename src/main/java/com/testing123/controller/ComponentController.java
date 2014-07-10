package com.testing123.controller;

import com.testing123.ui.MainComponent;
import com.vaadin.ui.GridLayout;

public class ComponentController {
	
	public static void drawComponent(GridLayout layout) {
		MainComponent comp = new MainComponent();
		layout.addComponent(comp, 1, 1);
	}
	
	public static void drawGraph() {
		
	}
}
