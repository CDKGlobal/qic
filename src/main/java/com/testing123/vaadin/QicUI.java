package com.testing123.vaadin;

import javax.servlet.annotation.WebServlet;

import com.testing123.ui.NavComponentVolatile;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

/**
 * QicUI acts as the "Main" of the program.  It sets up the server and initializes the layout for
 * the user interface.
 * 
 * @author chenc
 *
 */
@SuppressWarnings("serial")
@Theme("mytheme")
public class QicUI extends UI {

	/**
	 * Required to run the server (autogenerated by the framework)
	 * 
	 * @author chenc
	 */
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = QicUI.class)
	public static class Servlet extends VaadinServlet {}

	/**
	 * Initializes the server with a new request.  Sets the layout of the page.
	 * 
	 * @param request
	 */
	@Override
	protected void init(VaadinRequest request) {
		final GridLayout layout = new GridLayout(3, 3);
		layout.setMargin(true);
		setContent(layout);
		
		AbsoluteLayout l1 = new AbsoluteLayout();
		AbsoluteLayout l2 = new AbsoluteLayout();
		l1.setWidth("150px");
		l1.setHeight("100px");
		
		l2.setWidth("1000px");
		l2.setHeight("100px");
		
		Label title = new Label("<b>Quality in Code</b>", ContentMode.HTML);
		title.setStyleName("h1");
		l2.addComponent(title, "top:25px;");
		
		layout.addComponent(l1, 0, 0);
		layout.addComponent(l2, 1, 0);
		
		MainComponentVolatile comp = new MainComponentVolatile();
		layout.addComponent(comp, 1, 1);
		
		NavComponentVolatile nav = new NavComponentVolatile();
		layout.addComponent(nav, 1, 2);
	}
}