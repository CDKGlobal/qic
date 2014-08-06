package com.testing123.ui;

import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;

import com.testing123.controller.AvailableResources;
import com.testing123.controller.UIState;
import com.testing123.controller.UIState.XAxis;
import com.testing123.dataObjects.ConvertDate;
import com.testing123.dataObjects.ConvertProject;
import com.testing123.vaadin.GetData;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

public class NavigationComponent extends CustomComponent {
	private FilterComponent  filter;
	private AbsoluteLayout navLayout;
	private ComboBox startComboBox;
	private ComboBox endComboBox;
	private Button button_1;
	private GridLayout layout;
	private Label errorLabel;
	private GetData data;
	private UIState state;
	
	private static final String DATE_GRANULARITY_OFFSET = "70px";
	private static final String AXIS_BOX_OFFSET = "5px";
	private static final String COMBOBOX_WIDTH = "200px";
	private static final String DEFAULT_VALUE = "-1px";
	
	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 */
	public NavigationComponent(final GridLayout layout, final UIState state) {
		this.data = new GetData();
		this.state = state;
		this.layout = layout;
		createNavComponentLayout();
		buildNavigationLayout();
		setCompositionRoot(navLayout);
		
		ComponentController.drawMainComponent(layout, state, data);
	
		JavaScript.getCurrent().addFunction("notify", new JavaScriptFunction() {
			@Override
			public void call(JSONArray arguments) throws JSONException {
				Notification.show(arguments.getString(0));
				fireChangeAction();
			}
		});
		
		filter = new FilterComponent();
		layout.addComponent(filter, 2, 1);
	}
	
	/**
	 * Fires the event that will repaint the graph
	 * 
	 */
	public void fireChangeAction() {
		ComponentController.drawMainComponent(layout, state, data);
	}
	
	public Button buildNavigationLayout() {
		createNavComponentLayout();
		
		// XAxis combo box
		List<XAxis> xAxisOptions = XAxis.possibleValues();
		
		final ComboBox xAxisComboBox = createAxisComboBox(xAxisOptions, "");
		navLayout.addComponent(xAxisComboBox, "top:" + AXIS_BOX_OFFSET + "; left: 340px;");
		
		xAxisComboBox.addValueChangeListener(new Property.ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				state.setX((XAxis) xAxisComboBox.getValue());
				if (state.getX() == XAxis.LINESOFCODE) {
					startComboBox.setEnabled(false);
				} else {
					startComboBox.setEnabled(true);
				}
				fireChangeAction();
			}
		});
		
		// gets all the available dates that can be queried
		List<ConvertDate> dateOptions = AvailableResources.getAvailableDates();
		
		// start date combo box
	    startComboBox = createDateComboBox(dateOptions, "Start Date");
	    startComboBox.select(state.getStart());
		navLayout.addComponent(startComboBox, "top:" + DATE_GRANULARITY_OFFSET +";");
		
		// end date combo box
		endComboBox = createDateComboBox(dateOptions, "End Date");
		endComboBox.select(state.getEnd());
		navLayout.addComponent(endComboBox, "top:" + DATE_GRANULARITY_OFFSET + ";left:220.0px;");
		
		// go button
		button_1 = new Button();
		button_1.setCaption("Go / Reset");
		button_1.setImmediate(false);
		button_1.setWidth(DEFAULT_VALUE);
		button_1.setHeight(DEFAULT_VALUE);
		errorLabel = new Label("");
		button_1.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				navLayout.removeComponent(errorLabel);
				if (startComboBox.getValue() == null || endComboBox.getValue() == null) {
					errorLabel = new Label("No date range entered");
					navLayout.addComponent(errorLabel, "top:" + DATE_GRANULARITY_OFFSET + "; left:570.0px;");
					return;
				} 
				ConvertDate startDate = (ConvertDate) startComboBox.getValue();
				ConvertDate endDate = (ConvertDate) endComboBox.getValue();
				if (checkIfStartDateIsNotLessThanEndDate(startDate, endDate)) {
					errorLabel = new Label("Date range invalid");
					navLayout.addComponent(errorLabel, "top:" + DATE_GRANULARITY_OFFSET + "; left:570.0px;");
					return;
				}
				state.setProjects((Set<ConvertProject>) filter.projectFilter.getValue());
				state.setAuthorsFilter((Set<String>) filter.authorsFilter.getValue()); 
				state.setStart(startDate);
				state.setEnd(endDate);
				fireChangeAction();
			}

			private boolean checkIfStartDateIsNotLessThanEndDate(
					ConvertDate startDate, ConvertDate endDate) {
				return startDate.toString().equals(endDate.toString()) || 
						startDate.toString().compareTo(endDate.toString()) > 0;
			}
		});
		navLayout.addComponent(button_1, "top:" + DATE_GRANULARITY_OFFSET + "; left:440.0px;");
		return button_1;
	}

	private void createNavComponentLayout() {
		// common part: create layout
		navLayout = new AbsoluteLayout();
		navLayout.setImmediate(false);
		navLayout.setWidth("800px");
		navLayout.setHeight("100px");

		// top-level component properties
		setWidth("800px");
		setHeight("100px");
	}

	private ComboBox createAxisComboBox(List<XAxis> options, String tag) {
		ComboBox box = createComboBoxWithLabel(tag, true);
		for (int i = 0; i < options.size(); i++) {
			box.addItem(options.get(i));
		}
		box.select(options.get(0));
		return box;
	}
	
	private ComboBox createDateComboBox(List<ConvertDate> options, String tag) {
		ComboBox box = createComboBoxWithLabel(tag, true);
		for (int i = 0; i < options.size(); i++) {
			box.addItem(options.get(i));
		}
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