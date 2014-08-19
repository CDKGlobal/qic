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

@SuppressWarnings("serial")
public class FilterComponent extends CustomComponent {
	protected AbsoluteLayout mainLayout;
	protected TwinColSelect projectFilter;
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
	
	public TwinColSelect getProjectFilter() {
		return projectFilter;
	}
	
	public TwinColSelect getAuthorsFilter() {
		return authorsFilter;
	}
	
	private AbsoluteLayout buildMainLayout() {
		createFilterComponentLayout();
		
		List<ConvertProject> projectOptions = database.getAvailableProjects();
        projectFilter = createProjectsSelect("projects", projectOptions);
        mainLayout.addComponent(projectFilter, "top: 50px; left: 20px;");
        
        List<String> authorOptions = database.getAvailableAuthors();
        
		authorsFilter = createAuthorsSelect(authorOptions);
		mainLayout.addComponent(authorsFilter, "top: 220px; left: 20px;");
		
		return mainLayout;
	}
	
	private TwinColSelect createAuthorsSelect(List<String> options) {
		TwinColSelect filter = new TwinColSelect();
		for (String author : options) {
            filter.addItem(author);
        }
        filter.setRows(20);
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
	
	private TwinColSelect createProjectsSelect(String label, List<ConvertProject> options) {
		TwinColSelect filter = new TwinColSelect();
		for (ConvertProject option : options) {
            filter.addItem(option);
        }
        filter.setRows(20);
        filter.setNullSelectionAllowed(true);
        filter.setMultiSelect(true);
        filter.setImmediate(true);
        filter.setLeftColumnCaption("Available projects");
        filter.setRightColumnCaption("Selected projects");
        filter.setWidth("700px");
        for (ConvertProject option : state.getProjects()) {
        	filter.select(option);
        }
        return filter;
	}
	
	private void createFilterComponentLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		
		// top-level component properties
		setSizeFull();
	}
}
