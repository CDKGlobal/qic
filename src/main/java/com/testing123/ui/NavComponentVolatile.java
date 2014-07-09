package com.testing123.ui;

import com.testing123.controller.AvailableResources;
import com.testing123.downloader.Uploader;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.ListSelect;

public class NavComponentVolatile extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private ListSelect listSelect_1;
	@AutoGenerated
	private Button button_1;
	@AutoGenerated
	private ComboBox comboBox_2;
	@AutoGenerated
	private ComboBox comboBox_1;

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public NavComponentVolatile() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("1000px");
		mainLayout.setHeight("100px");

		// top-level component properties
		setWidth("1000px");
		setHeight("100px");

		// comboBox_1
		comboBox_1 = new ComboBox("Start Date");
		comboBox_1.setImmediate(false);
		comboBox_1.setWidth("-1px");
		comboBox_1.setHeight("-1px");
		mainLayout.addComponent(comboBox_1, "top:20.0px;left:22.0px;");

		// comboBox_2
		comboBox_2 = new ComboBox("End Date");
		comboBox_2.setImmediate(false);
		comboBox_2.setWidth("156px");
		comboBox_2.setHeight("-1px");
		
		comboBox_2.addItem("Item 1");
		AvailableResources.getAvailableDates();
		
		mainLayout.addComponent(comboBox_2, "top:20.0px;left:200.0px;");

		// button_1
		button_1 = new Button();
		button_1.setCaption("Go");
		button_1.setImmediate(false);
		button_1.setWidth("-1px");
		button_1.setHeight("-1px");
		mainLayout.addComponent(button_1, "top:20.0px;left:380.0px;");

		// listSelect_1
		listSelect_1 = new ListSelect();
		listSelect_1.setImmediate(false);
		listSelect_1.setWidth("-1px");
		listSelect_1.setHeight("86px");
		mainLayout.addComponent(listSelect_1, "top:14.0px;left:480.0px;");

		return mainLayout;
	}
}
