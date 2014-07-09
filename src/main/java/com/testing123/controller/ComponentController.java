package com.testing123.controller;

import com.testing123.ui.MainComponentVolatile;
import com.vaadin.ui.GridLayout;

public class ComponentController {
	
	public static void drawComponent(GridLayout layout) {
		MainComponentVolatile comp = new MainComponentVolatile();
		layout.addComponent(comp, 1, 1);
	}
	
	public static void drawGraph() {
		
	}
}
