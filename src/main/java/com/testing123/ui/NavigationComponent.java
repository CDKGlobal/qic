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
		mainLayout.addComponent(xAxisComboBox, "top:" + AXIS_BOX_OFFSET);
		
		xAxisComboBox.addValueChangeListener(new Property.ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				state.setX((Axis) xAxisComboBox.getValue());
				ComponentController.drawMainComponent(layout, state);
			}
		});
		
		
		// File Granularity Menu
//		MenuBar barmenu = new MenuBar();
//		barmenu.setStyleName("mybarmenu");
		
//		       
//		// A feedback component
//		final Label selection = new Label("");
//		mainLayout.addComponent(selection);
//
//		// Define a common menu command for all the menu items.
//		MenuBar.Command mycommand = new MenuBar.Command() {
//			MenuItem previous = null;
//
//		    public void menuSelected(MenuItem selectedItem) {
//		        if (previous != null) {
//		            previous.setStyleName(null);
//		        }
//		        selectedItem.setStyleName("highlight");
//		        state.setGrain(selectedItem.getText());
//		        previous = selectedItem;
//		        ComponentController.drawMainComponent(layout, state);
//		    }
//		};
//		
//		// Put some items in the menu
//		barmenu.addItem("Projects", null, mycommand);
//		barmenu.addItem("Directories", null, mycommand);
//		barmenu.addItem("Files", null, mycommand);
//		barmenu.addItem("Authors", null, mycommand);

//		List<String> isDelta = Arrays.asList(new String[] { "Show Change", "Show Absolute"});
		
		// 'Shorthand' constructor - also supports data binding using Containers
//		OptionGroup citySelect = new OptionGroup("Please select a view", isDelta);
//		citySelect.setNullSelectionAllowed(false); // user can not 'unselect'
//	    citySelect.select("Show Change"); // select this by default
//	    citySelect.setImmediate(true); // send the change to the server at once
	    //citySelect.addListener(this); // react when the user selects something
//
//	        addComponent(citySelect);
//
//	        addComponent(new Label("<h3>Multi-selection</h3>", Label.CONTENT_XHTML));
//
//	        // Create the multiselect option group
//	        // 'Shorthand' constructor - also supports data binding using Containers
//	        citySelect = new OptionGroup("Please select cities", cities);

//	        // Set disabled items
//	        citySelect.setItemEnabled("Helsinki", false);
//	        citySelect.setItemEnabled("Oslo", false);

//	        citySelect.setMultiSelect(true);
//	        citySelect.setNullSelectionAllowed(false); // user can not 'unselect'
//	        citySelect.select("Berlin"); // select this by default
//	        citySelect.setImmediate(true); // send the change to the server at once
//	        citySelect.addListener(this); // react when the user selects something

	        //addComponent(citySelect);
//
//	    /*
//	     * Shows a notification when a selection is made. The listener will be
//	     * called whenever the value of the component changes, i.e when the user
//	     * makes a new selection.
//	     */
//	    public void valueChange(ValueChangeEvent event) {
//	        getWindow().showNotification("Selected city: " + event.getProperty());
//
//	    }
		
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
		for (String option : options) {
			box.addItem(new ConvertDate(option));
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