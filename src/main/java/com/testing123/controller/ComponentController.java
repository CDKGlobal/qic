package com.testing123.controller;

import com.testing123.ui.MainComponent;
import com.testing123.vaadin.ConvertDate;
import com.vaadin.ui.GridLayout;

public class ComponentController {
	
	public static void drawMainComponent(GridLayout layout, ConvertDate start, ConvertDate end) {
		MainComponent comp = new MainComponent(start, end);
		layout.addComponent(comp, 1, 1);
	}
	
	public static void drawLabels() {
		
	}
}
