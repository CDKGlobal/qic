package com.testing123.ui;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;

import com.testing123.controller.AvailableResources;
import com.testing123.controller.SQLConnector;
import com.testing123.controller.UIState;
import com.testing123.controller.UIState.XAxis;
import com.testing123.dataObjects.ConvertDate;
import com.testing123.dataObjects.ConvertProject;
import com.testing123.vaadin.DisplayChanges;
import com.testing123.vaadin.GetData;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;

public class NavigationComponent extends CustomComponent {
	private FilterComponent filter;
	private AbsoluteLayout navLayout;
	private ComboBox startComboBox;
	private ComboBox endComboBox;
	private PopupDateField startDateField;
	private PopupDateField endDateField;
	private Button goButton;
	private Button shareButton;
	private TextField linkBox;
	private HorizontalLayout layout;
	private AbsoluteLayout graph;
	private GetData data;
	private UIState state;
	
	private static final String VERTICAL_OFFSET = "70px";
	private static final String VERTICAL_OFFSET_2 = "100px";
	private static final String AXIS_BOX_OFFSET = "5px";
	private static final String COMBOBOX_WIDTH = "200px";
	private static final String DEFAULT_VALUE = "-1px";
	
	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 */
	public NavigationComponent(final HorizontalLayout layout, final UIState state) {
		this.data = new GetData();
		this.state = state;
		this.layout = layout;
		this.graph = new AbsoluteLayout();
		
		final AbsoluteLayout left = new AbsoluteLayout();
		left.setWidth(null);
		layout.addComponent(left);
		
		graph.setWidth(QicUI.GRAPH_WIDTH);
		graph.setHeight(QicUI.GRAPH_HEIGHT);
		layout.addComponent(graph);
		
		final AbsoluteLayout right = new AbsoluteLayout();
		right.setWidth(null);
		layout.addComponent(right);
		
		layout.setExpandRatio(left, 1);
		layout.setExpandRatio(right, 1);
		
		createNavComponentLayout();
		buildNavigationLayout();
		setCompositionRoot(navLayout);
		
		fireChangeAction();
	
		/**
		 * Manages click events on the flot chart
		 */
		JavaScript.getCurrent().addFunction("notify", new JavaScriptFunction() {
			
			@Override
			public void call(JSONArray arguments) throws JSONException {
				String location = arguments.toString().replace("[\"", "").replace("\"]", "").replace("\\", "");
				System.out.println("PRINTED : " + location);
				if (state.getX() != XAxis.LINESOFCODE) {
					try {
						String link = new DisplayChanges().popUp(state, location).toString();
						if (link == null) {
							displayMessage("Pop Up Error", "Diff cannot be found", Notification.Type.WARNING_MESSAGE);
							return;
						}
						JavaScript.getCurrent().execute("window.open('" + link + "', 'Fisheye', 'height=800, width=1200');");
					} catch (Exception e) {
						System.out.println("Pop up failed");
					}
				}
			}
		});
		
		filter = new FilterComponent(state);
		layout.addComponent(filter);
	}
	
	/**
	 * Fires the event that will repaint the graph
	 * 
	 */
	public void fireChangeAction() {
		linkBox.setReadOnly(false);
		linkBox.setValue("");
		linkBox.setReadOnly(true);
		ComponentController.drawMainComponent(graph, state, data);
	}
	
	public Button buildNavigationLayout() {
		createNavComponentLayout();
		
		// XAxis combo box
		List<XAxis> xAxisOptions = XAxis.possibleValues();
		
		final ComboBox xAxisComboBox = createAxisComboBox(xAxisOptions, "");
		navLayout.addComponent(xAxisComboBox, "top:" + AXIS_BOX_OFFSET + "; left: 340.0px;");
		
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
		List<ConvertDate> dateOptions = AvailableResources.getAvailableDates(new SQLConnector());
		
		// start date combo box
//	    startComboBox = createDateComboBox(dateOptions, "Start Date");
//	    startComboBox.select(state.getStart());
//		navLayout.addComponent(startComboBox, "top:" + VERTICAL_OFFSET + ";");
		
		startDateField = new PopupDateField("Start Date");
        startDateField.setImmediate(true);
        startDateField.setDateFormat("MM/dd/yyyy");
        startDateField.setResolution(Resolution.DAY);
        startDateField.setRangeEnd(new DateTime().toDate()); // sets current day as max date
        
        startDateField.setValue(new DateTime().minusDays(7).toDate());
		navLayout.addComponent(startDateField, "top: " + VERTICAL_OFFSET + ";");
		
        endDateField = new PopupDateField("End Date");
        endDateField.setImmediate(true);
        endDateField.setDateFormat("MM/dd/yyyy");
        endDateField.setResolution(Resolution.DAY);
        endDateField.setRangeEnd(new DateTime().toDate()); // sets current day as max date
        
        endDateField.setValue(new Date());
		navLayout.addComponent(endDateField, "left: 200px; top: " + VERTICAL_OFFSET + ";");
		
		// end date combo box
//		endComboBox = createDateComboBox(dateOptions, "End Date");
//		endComboBox.select(state.getEnd());
//		navLayout.addComponent(endComboBox, "top:" + VERTICAL_OFFSET + ";left:220.0px;");
		
		// go button
		goButton = new Button();
		goButton.setCaption("Go / Reset");
		goButton.setImmediate(true);
		goButton.setWidth(DEFAULT_VALUE);
		goButton.setHeight(DEFAULT_VALUE);
		goButton.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if (startDateField.getValue() == null || endDateField.getValue() == null) {
					displayMessage("Invalid Date Input", "No date range entered", Notification.Type.ERROR_MESSAGE);
					return;
				} 
				
				ConvertDate startDate = new ConvertDate(startDateField.getValue());
				ConvertDate endDate = new ConvertDate(endDateField.getValue());
				
				if (checkIfStartDateIsNotLessThanEndDate(startDate, endDate)) {
					displayMessage("Invalid Date Input", "Date range invalid", Notification.Type.ERROR_MESSAGE);
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
		
		// link text field
		linkBox = new TextField();
        linkBox.setImmediate(false);
        linkBox.setReadOnly(true);
        linkBox.setWidth("620px");
		navLayout.addComponent(linkBox, "top:" + VERTICAL_OFFSET_2 + ";");
		
		// share button
		shareButton = new Button();
		shareButton.setCaption("Share");
		shareButton.setImmediate(false);
		shareButton.setWidth(DEFAULT_VALUE);
		shareButton.setHeight(DEFAULT_VALUE);
		shareButton.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				linkBox.setReadOnly(false);
				linkBox.setValue(state.getStateURI());
				linkBox.setReadOnly(true);
			}	
		});
		
		navLayout.addComponent(shareButton, "top:" + VERTICAL_OFFSET_2 + "; left:640.0px;");
		navLayout.addComponent(goButton, "top:" + VERTICAL_OFFSET + "; left:440.0px;");
		return goButton;
	}

	private void createNavComponentLayout() {
		// common part: create layout
		navLayout = new AbsoluteLayout();
		navLayout.setImmediate(false);
//		navLayout.setWidth("800px");
		navLayout.setHeight("200px");

		// top-level component properties
//		setWidth("800px");
		setHeight("200px");
	}

	private void displayMessage(String message, String desc, Notification.Type type) {
		Notification popUp = new Notification(message, desc, type);
		popUp.show(Page.getCurrent());
		popUp.setPosition(Position.TOP_CENTER);
	}
	
	private ComboBox createAxisComboBox(List<XAxis> options, String tag) {
		ComboBox box = createComboBoxWithLabel(tag, true);
		for (int i = 0; i < options.size(); i++) {
			box.addItem(options.get(i));
		}
		box.select(state.getX());
		return box;
	}
//	
//	private ComboBox createDateComboBox(List<ConvertDate> options, String tag) {
//		ComboBox box = createComboBoxWithLabel(tag, true);
//		for (int i = 0; i < options.size(); i++) {
//			box.addItem(options.get(i));
//		}
//		return box;
//	}

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