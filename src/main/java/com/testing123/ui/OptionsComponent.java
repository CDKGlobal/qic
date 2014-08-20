package com.testing123.ui;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class OptionsComponent extends CustomComponent {
	protected HorizontalLayout mainLayoutContainer;
	protected HorizontalLayout mainLayout;
	protected MenuBar menubar;
	protected Window projectFilterWindow;
	protected Window authorFilterWindow;
	protected Window helpWindow;

	public OptionsComponent() {
		this.projectFilterWindow = new Window("Filter by Projects");
		projectFilterWindow.setResizable(false);
		this.authorFilterWindow = new Window("Filter by Authors");
		authorFilterWindow.setResizable(false);
	}
	
	public Window getProjectFilterWindow() {
		return projectFilterWindow;
	}
	
	public Window getAuthorFilterWindow() {
		return authorFilterWindow;
	}
	
	public HorizontalLayout buildMainLayout(PopupDateField startDateField, PopupDateField endDateField, 
			ComboBox xAxisComboBox, Button goButton, TextField linkBox, Button shareButton) {
		mainLayoutContainer = new HorizontalLayout();
		mainLayoutContainer.addStyleName("toolbar-background");
		mainLayoutContainer.setWidth("100%");
		mainLayout = new HorizontalLayout();
		mainLayout.setWidthUndefined();
		mainLayout.setHeight("100%");
		mainLayout.addStyleName("toolbar-background");
		
		MenuBar menu = new MenuBar();
		menu.setHeight("100%");
		menu.setStyleName("main_menubar");
		
		mainLayout.addComponent(xAxisComboBox);
		mainLayout.addComponent(startDateField);
	    mainLayout.addComponent(endDateField);
		
		projectsOptions(menu);
		
		authorsOptions(menu);
		
		mainLayout.addComponent(menu);
		
		goButton.addStyleName("go_button");
		mainLayout.addComponent(goButton);
		
		shareButton.addStyleName("share_button");
		mainLayout.addComponent(shareButton);
		
		MenuBar help = new MenuBar();
		help.setHeight("100%");
		help.setStyleName("main_menubar");
		helpOptions(help);
		mainLayout.addComponent(help);
		
		mainLayoutContainer.addComponent(mainLayout);
		setCompositionRoot(mainLayoutContainer);
		return mainLayoutContainer;
	}
	
	private void helpOptions(MenuBar menu) {
		menu.addItem("Help", null, new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				UI.getCurrent().removeWindow(helpWindow);
				helpWindow = new Window("Help");
				helpWindow.setResizable(false);
				helpWindow.setPositionX(500);
                helpWindow.setPositionY(140);
                FormLayout helpForm = new FormLayout();
				Label label = new Label(
						"<p>The main function of QIC is to display the changes in Churn and Complexity "
								+ "over two dates selected dates.  It can also show the number of non-commented "
								+ "lines of code during a given date (specified in 'End Date').  For each file, it "
								+ "will plot the chosen value on the x axis, and its final complexity on the y-axis. </p>"
								+ "<p>You can filter by projects or authors to show only results specified by the "
								+ "filter</p>"
								+ "<p>Data points are clickable and will show the diff between the two revisions between"
								+ "the specified dates</p>",
								 ContentMode.HTML);
				label.addStyleName("help_label");
                helpWindow.setWidth("400px");
                helpForm.addComponent(label);
                Button helpDone = new Button("OK");
                helpDone.addClickListener(new Button.ClickListener() {
        			
        			@Override
        			public void buttonClick(ClickEvent event) {
        				UI.getCurrent().removeWindow(helpWindow);
        			}
        		});
        		helpForm.addComponent(helpDone);
                helpWindow.setContent(helpForm);
		        UI.getCurrent().addWindow(helpWindow);
		        
			}
		});
	}
	
	private void authorsOptions(MenuBar menu) {
		menu.addItem("Select Authors", null, new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				UI.getCurrent().removeWindow(authorFilterWindow);
				authorFilterWindow.setPositionX(450);
                authorFilterWindow.setPositionY(70);
		        UI.getCurrent().addWindow(authorFilterWindow);
			}
		});
	}

	private void projectsOptions(MenuBar menu) {
		menu.addItem("Select Projects", null, new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				UI.getCurrent().removeWindow(projectFilterWindow);
				projectFilterWindow.setPositionX(300);
                projectFilterWindow.setPositionY(70);
		        UI.getCurrent().addWindow(projectFilterWindow);
			}
		});
	}
}
