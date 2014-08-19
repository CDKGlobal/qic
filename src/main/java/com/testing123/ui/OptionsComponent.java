package com.testing123.ui;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.vaadin.server.Page;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class OptionsComponent extends CustomComponent {
	protected HorizontalLayout mainLayoutContainer;
	protected HorizontalLayout mainLayout;
	protected MenuBar menubar;
	protected Window window;
	protected Window window2;
	protected Window window3;
	protected Window window4;
	protected Window helpW;

	public OptionsComponent() {
		//this.window = new Window("Date Range");
		this.window2 = new Window("Change Axis");
		this.window3 = new Window("Filter by Projects");
		this.window4 = new Window("Filter by Authors");
	}
	
	public Window getWindow() {
		return window;
	}
	
	public Window getWindow2() {
		return window2;
	}
	
	public Window getWindow3() {
		return window3;
	}
	
	public Window getWindow4() {
		return window4;
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
		
		mainLayout.addComponent(startDateField);
	    mainLayout.addComponent(endDateField);
		mainLayout.addComponent(xAxisComboBox);
		//axisOptions(menu);
		
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
				UI.getCurrent().removeWindow(helpW);
				helpW = new Window("Help");
				helpW.setPositionX(500);
                helpW.setPositionY(140);
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
                helpW.setWidth("400px");
                helpW.setContent(label);
		        UI.getCurrent().addWindow(helpW);
			}
		});
	}
	
	private void authorsOptions(MenuBar menu) {
		menu.addItem("Select Authors", null, new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				UI.getCurrent().removeWindow(window4);
				window4.setPositionX(450);
                window4.setPositionY(70);
		        UI.getCurrent().addWindow(window4);
			}
		});
	}

	private void projectsOptions(MenuBar menu) {
		menu.addItem("Select Projects", null, new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				UI.getCurrent().removeWindow(window3);
				window3.setPositionX(300);
                window3.setPositionY(70);
		        UI.getCurrent().addWindow(window3);
			}
		});
	}

	private void axisOptions(MenuBar menu) {
		menu.addItem("Change Axis", null, new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				UI.getCurrent().removeWindow(window2);
				window2.setPositionX(150);
                window2.setPositionY(70);
		        UI.getCurrent().addWindow(window2);
			}
		});
	}

	private void dateRangeOptions(MenuBar menu) {
		menu.addItem("Change Date Range", null, new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				UI.getCurrent().removeWindow(window);
				window.setPositionX(0);
                window.setPositionY(70);
		        UI.getCurrent().addWindow(window);
			}
		});
	}
}
