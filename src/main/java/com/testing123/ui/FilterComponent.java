package com.testing123.ui;

import java.util.List;

import com.testing123.controller.UIState;
import com.testing123.dataObjects.ConvertProject;
import com.testing123.interfaces.DatabaseInterface;
import com.testing123.vaadin.UseSQLDatabase;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TwinColSelect;

public class FilterComponent extends CustomComponent {
	protected AbsoluteLayout mainLayout;
	protected ListSelect projectFilter;
	protected TwinColSelect authorsFilter;
	private UIState state;
	private DatabaseInterface database;
	
	public FilterComponent(UIState state, DatabaseInterface DBI){
		this.state = state;
		this.database = DBI;
		buildMainLayout();
		setCompositionRoot(mainLayout);

	}
	
	public FilterComponent(UIState state) {
		this(state, new UseSQLDatabase());
	}
	
	private AbsoluteLayout buildMainLayout() {
		createFilterComponentLayout();
		
		List<ConvertProject> projectOptions = database.getAvailableProjects();
        projectFilter = createListSelect("projects", projectOptions);
        mainLayout.addComponent(projectFilter, "top: 50px; left: 20px;");
        
        List<String> authorOptions = database.getAvailableAuthors();
        
		authorsFilter = createTwinColSelect(authorOptions);
		mainLayout.addComponent(authorsFilter, "top: 220px; left: 20px;");
		
		return mainLayout;
	}
	
	private TwinColSelect createTwinColSelect(List<String> options) {
		TwinColSelect filter = new TwinColSelect();
		for (String author : options) {
            filter.addItem(author);
        }
        filter.setRows(14);
        filter.setNullSelectionAllowed(true);
        filter.setMultiSelect(true);
        filter.setImmediate(true);
        filter.setLeftColumnCaption("Available authors");
        filter.setRightColumnCaption("Selected authors");
        filter.setWidth("350px");
        for (String option : state.getAuthorsFilter()) {
        	filter.select(option);
        }
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
        for (ConvertProject selectedProject : state.getProjects()) {
        	filter.select(selectedProject);
        }
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
