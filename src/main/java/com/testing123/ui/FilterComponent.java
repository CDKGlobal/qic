package com.testing123.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.testing123.controller.AvailableResources;
import com.testing123.vaadin.ConvertProject;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TwinColSelect;

public class FilterComponent extends CustomComponent {
	protected AbsoluteLayout mainLayout;
	protected ListSelect projectFilter;
	
	protected static final String[] cities = new String[] { "Berlin", "Brussels",
        "Helsinki", "Madrid", "Oslo", "Paris", "Stockholm" };
	
	public FilterComponent() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
	}
	
	private AbsoluteLayout buildMainLayout() {
		createFilterComponentLayout();
		
		List<ConvertProject> projectOptions = AvailableResources.getAvailableProjects();
        projectFilter = createListSelect("projects", projectOptions);
        mainLayout.addComponent(projectFilter, "top: 50px; left: 20px;");
        
        //List<String> authorOptions = AvailableResrounces.getAvail
        
		TwinColSelect authorsFilter = createTwinColSelect(Arrays.asList(cities));
		mainLayout.addComponent(authorsFilter, "top: 220px; left: 20px;");
		
		return mainLayout;
	}
	
	private TwinColSelect createTwinColSelect(List<String> options) {
		TwinColSelect filter = new TwinColSelect();
		for (int i = 0; i < cities.length; i++) {
            filter.addItem(cities[i]);
        }
        filter.setRows(7);
        filter.setNullSelectionAllowed(true);
        filter.setMultiSelect(true);
        filter.setImmediate(true);
        filter.setLeftColumnCaption("Available authors");
        filter.setRightColumnCaption("Selected authors");
        filter.setWidth("350px");
        return filter;
	}
	
	private ListSelect createListSelect(String label, List<ConvertProject> options) {
		ListSelect filter = new ListSelect("Please select one or more " + label);
        for (ConvertProject option : options) {
            filter.addItem(option);
        }
        filter.setRows(10);
        filter.setNullSelectionAllowed(true);
        filter.setMultiSelect(true);
        filter.setImmediate(true);
        filter.setWidth("350px");
        return filter;
	}
	
	private void createFilterComponentLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("400px");
		mainLayout.setHeight("450px");

		// top-level component properties
		setWidth("400px");
		setHeight("450px");
	}
}
