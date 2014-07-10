package com.testing123.controller;

import java.util.Calendar;

import com.testing123.ui.MainComponent;
import com.vaadin.ui.GridLayout;

public class ComponentController {
	
	public static void drawMainComponent(GridLayout layout, Calendar start, Calendar end) {
		MainComponent comp = new MainComponent();
		layout.addComponent(comp, 1, 1);
	}
	
	public static void drawLabels() {
		
	}
}
