package com.testing123.ui;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.testing123.controller.AvailableResources;
import com.testing123.controller.ComponentController;
import com.testing123.controller.UIState;
import com.testing123.controller.UIState.Axis;
import com.testing123.vaadin.ConvertDate;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;

public class NavigationComponent extends CustomComponent {

	private AbsoluteLayout mainLayout;
	//private ListSelect listSelect_1;
	private Button button_1;
	private GridLayout layout;
	private Label errorLabel;
	private UIState state;
	
	private static final String DATE_GRANULARITY_OFFSET = "70px";
	private static final String AXIS_BOX_OFFSET = "20px";
	private static final String COMBOBOX_WIDTH = "200px";
	private static final String DEFAULT_VALUE = "-1px";

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 */
	public NavigationComponent(final GridLayout layout, final UIState state) {
		this.state = state;
		this.layout = layout;
		buildMainLayout();
		setCompositionRoot(mainLayout);
		ComponentController.drawMainComponent(layout, state);
	
		JavaScript.getCurrent().addFunction("notify", new JavaScriptFunction() {
			@Override
			public void call(JSONArray arguments) throws JSONException {
				Notification.show(arguments.getString(0));
				state.setFocus(arguments.getString(0));
				ComponentController.drawMainComponent(layout, state);
			}
		});
	}
	
	private AbsoluteLayout buildMainLayout() {
		createNavComponentLayout();
		
		// XAxis combobox
		
		List<Axis> xAxisOptions = Axis.possibleValues();
		
		final ComboBox xAxisComboBox = createAxisComboBox(xAxisOptions, "X-Axis");
		mainLayout.addComponent(xAxisComboBox, "top:" + AXIS_BOX_OFFSET + "; left: 325.0px");
		
		xAxisComboBox.addValueChangeListener(new Property.ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				state.setX((Axis) xAxisComboBox.getValue());
				ComponentController.drawMainComponent(layout, state);
			}
		});
		
		
		// File Granularity Menu
		
		// gets all the available dates that can be queried
		List<String> dateOptions = AvailableResources.getAvailableDates();
		
		// comboBox_1
	    final ComboBox startComboBox = createDateComboBox(dateOptions, "Start Date");
		mainLayout.addComponent(startComboBox, "top:" + DATE_GRANULARITY_OFFSET +";");
		
		// comboBox_2
		final ComboBox endComboBox = createDateComboBox(dateOptions, "End Date");
		mainLayout.addComponent(endComboBox, "top:" + DATE_GRANULARITY_OFFSET + ";left:220.0px;");
		
		// button_1
		button_1 = new Button();
		button_1.setCaption("Go");
		button_1.setImmediate(false);
		button_1.setWidth(DEFAULT_VALUE);
		button_1.setHeight(DEFAULT_VALUE);
		errorLabel = new Label("");
		button_1.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				mainLayout.removeComponent(errorLabel);
				if (startComboBox.getValue() == null || endComboBox.getValue() == null) {
					errorLabel = new Label("No date range entered");
					mainLayout.addComponent(errorLabel, "top:" + DATE_GRANULARITY_OFFSET + "; left:510.0px;");
					return;
				} 
				ConvertDate startDate = (ConvertDate) startComboBox.getValue();
				ConvertDate endDate = (ConvertDate) endComboBox.getValue();
				if (checkIfStartDateIsNotLessThanEndDate(startDate, endDate)) {
					errorLabel = new Label("Date range invalid");
					mainLayout.addComponent(errorLabel, "top:" + DATE_GRANULARITY_OFFSET + "; left:510.0px;");
					return;
				}
				state.setStart(startDate);
				state.setEnd(endDate);
				ComponentController.drawMainComponent(layout, state);
			}

			private boolean checkIfStartDateIsNotLessThanEndDate(
					ConvertDate startDate, ConvertDate endDate) {
				return startDate.getSonarFormat().equals(endDate.getSonarFormat()) || 
						startDate.getSonarFormat().compareTo(endDate.getSonarFormat()) > 0;
			}
		});
		mainLayout.addComponent(button_1, "top:" + DATE_GRANULARITY_OFFSET + ";left:440.0px;");

		return mainLayout;
	}

	private void createNavComponentLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("1000px");
		mainLayout.setHeight("100px");

		// top-level component properties
		setWidth("1000px");
		setHeight("100px");
	}

	private ComboBox createAxisComboBox(List<Axis> options, String tag) {
		ComboBox box = createComboBoxWithLabel(tag, true);
		for (int i = 0; i < 3; i++) {
			box.addItem(options.get(i));
		}
		box.select(options.get(0));
		return box;
	}
	
	private ComboBox createDateComboBox(List<String> options, String tag) {
		ComboBox box = createComboBoxWithLabel(tag, false);
		for (int i = options.size() - 3; i >= 0; i--) {
			box.addItem(new ConvertDate(options.get(i)));
		}
		//box.select(new ConvertDate(options.get(0)));
		return box;
	}

	private ComboBox createComboBoxWithLabel(String label, boolean immediate) {
		ComboBox comboBox = new ComboBox(label);
		comboBox.setImmediate(immediate);
		comboBox.setNullSelectionAllowed(false);
		comboBox.setTextInputAllowed(false);
		comboBox.setWidth(COMBOBOX_WIDTH);
		comboBox.setHeight(DEFAULT_VALUE);
		return comboBox;
	}
}