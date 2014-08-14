package com.testing123.ui;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class OptionsComponent extends CustomComponent {
	protected AbsoluteLayout mainLayout;
	protected MenuBar menubar;
	protected Window subwindow;

	public OptionsComponent() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
	}

	private AbsoluteLayout buildMainLayout() {
		mainLayout = new AbsoluteLayout();
		MenuBar menu = new MenuBar();
		menu.setWidth("100%");
		menu.setHeight("100%");
		menu.setStyleName("main_menubar");
		menu.addItem("Change Settings", null, null);
		mainLayout.addComponent(menu);
		
        //VerticalLayout layout = (VerticalLayout) subwindow.getContent();
//        layout.setMargin(true);
//        layout.setSpacing(true);
        
        subwindow = new Window("A subwindow");

        // Add some content; a label and a close-button
        Label message = new Label("This is a subwindow");
//        subwindow.addComponent(message);
//
//        Button close = new Button("Close", new Button.ClickListener() {
//
//        	@Override
//            public void buttonClick(ClickEvent event) {
//                // close the window by removing it from the parent window
//                (subwindow.getParent()).removeWindow(subwindow);
//            }
//        });
//        // The components added to the window are actually added to the window's
//        // layout; you can use either. Alignments are set using the layout
//        layout.addComponent(close);
//        layout.setComponentAlignment(close, Alignment.TOP_RIGHT);
//
//        // Add a button for opening the subwindow
//        Button open = new Button("Open subwindow", new Button.ClickListener() {
//            // inline click-listener
//            public void buttonClick(ClickEvent event) {
//                if (subwindow.getParent() != null) {
//                    // window is already showing
//                    mainLayout.getWindow().showNotification("Window is already open");
//                } else {
//                    // Open the subwindow by adding it to the parent window
//                    mainLayout.getWindow().addWindow(subwindow);
//                }
//            }
//        });
//        mainLayout.addComponent(open);
		
		mainLayout.setWidth("100%");
		mainLayout.setHeight("25px");
		return mainLayout;
	}
}
