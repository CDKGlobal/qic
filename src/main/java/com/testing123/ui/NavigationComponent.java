package com.testing123.ui;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;

import com.testing123.controller.UIState;
import com.testing123.controller.UIState.XAxis;
import com.testing123.dataObjects.ConvertDate;
import com.testing123.dataObjects.ConvertProject;
import com.testing123.vaadin.DisplayChanges;
import com.testing123.vaadin.GetData;
import com.testing123.vaadin.UseSQLDatabase;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class NavigationComponent extends CustomComponent {
	private FilterComponent filter;
	private AbsoluteLayout navLayout;
	private PopupDateField startDateField;
	private PopupDateField endDateField;
	private ComboBox xAxisComboBox;
	private Button goButton;
	private Button shareButton;
	private TextField linkBox;
	private AbsoluteLayout layout;
	private AbsoluteLayout graph;
	private GetData data;
	private UIState state;
	private Window w;
	private final OptionsComponent optionsBar;
	
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
	public NavigationComponent(final VerticalLayout mainVerticalLayout, final UIState state) {
		this.data = new GetData();
		this.state = state;
		this.layout = new AbsoluteLayout();
		this.graph = new AbsoluteLayout();
		
		optionsBar = new OptionsComponent();
		mainVerticalLayout.addComponent(optionsBar);

		createResizableMidComponent(mainVerticalLayout);
		
		createNavComponentLayout();
		buildNavigationLayout();
		setCompositionRoot(navLayout);
		
		fireChangeAction();
		initializeJSHandler(state);
		
		filter = new FilterComponent(state);
		mainVerticalLayout.addComponent(layout);

	    
	    final VerticalLayout projectContent = new VerticalLayout();
	    createProjectOptions(projectContent);
		 
	    final VerticalLayout authorContent = new VerticalLayout();
		createAuthorOptions(authorContent);
		
	    
	    optionsBar.getWindow3().setContent(projectContent);
	    optionsBar.getWindow4().setContent(authorContent);
	    optionsBar.buildMainLayout(startDateField, endDateField, xAxisComboBox, goButton, linkBox, shareButton);
	}

	private void createResizableMidComponent(
			final VerticalLayout mainVerticalLayout) {
		final HorizontalLayout container = new HorizontalLayout();
		container.setWidth("100%");
		mainVerticalLayout.addComponent(container);
		
		// left margin
		final AbsoluteLayout left = new AbsoluteLayout();
		left.setWidth(null);
		container.addComponent(left);
		
		// graph component
		graph.setWidth(QicUI.COMPONENT_WIDTH);
		graph.setHeight(QicUI.COMPONENT_HEIGHT);
		container.addComponent(graph);
		
		// right margin
		final AbsoluteLayout right = new AbsoluteLayout();
		right.setWidth(null);
		container.addComponent(right);
		
		container.setExpandRatio(left, 1);
		container.setExpandRatio(right, 1);
	}

	private void createProjectOptions(final VerticalLayout content3) {
		final FormLayout projectForm = new FormLayout();
	    content3.addComponent(projectForm);
	    projectForm.setMargin(true);
	    
        final ComboBox autoProjectBox = new ComboBox("Choose Projects");
        buildProjectSuggestionBox(autoProjectBox);
		projectForm.addComponent(autoProjectBox);
		projectForm.addComponent(filter.getProjectFilter());
		
		AbsoluteLayout projectButtons = new AbsoluteLayout();
		projectButtons.setHeight("30px");
		Button removeAllProjects = new Button("Remove All");
		removeAllProjects.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Set<ConvertProject> selected = ((Set<ConvertProject>) filter.getProjectFilter().getValue());
				for (ConvertProject selection : selected) {
					filter.getProjectFilter().unselect(selection);
				}
			}

		});
		projectButtons.addComponent(removeAllProjects);
		
		Button projectsDone = new Button("Done");
		projectsDone.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().removeWindow(optionsBar.getWindow3());
			}

		});
		projectButtons.addComponent(projectsDone, "left: 640px;");
		
		projectForm.addComponent(projectButtons);
	}

	private void createAuthorOptions(final VerticalLayout content4) {
		final FormLayout authorForm = new FormLayout();
	    content4.addComponent(authorForm);
	    authorForm.setMargin(true);
	    
        final ComboBox autoAuthorBox = new ComboBox("Choose authors");
        buildAuthorSuggestionBox(autoAuthorBox);
        authorForm.addComponent(autoAuthorBox);
	    authorForm.addComponent(filter.getAuthorsFilter());
	    
		AbsoluteLayout authorButtons = new AbsoluteLayout();
		authorButtons.setHeight("30px");
		Button removeAllAuthors = new Button("Remove All");
		removeAllAuthors.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Set<String> selected = ((Set<String>) filter.getAuthorsFilter().getValue());
				for (String selection : selected) {
					filter.getAuthorsFilter().unselect(selection);
				}
			}

		});
		authorButtons.addComponent(removeAllAuthors);
	    
		Button authorsDone = new Button("Done");
		authorsDone.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().removeWindow(optionsBar.getWindow4());
			}

		});
		authorButtons.addComponent(authorsDone, "left: 290px");
		
		authorForm.addComponent(authorButtons);
	}

	private void buildProjectSuggestionBox(final ComboBox autoProjectBox) {
		autoProjectBox.setInputPrompt("Search for a project");
		
		autoProjectBox.setFilteringMode(FilteringMode.CONTAINS);
        autoProjectBox.setImmediate(true);
        autoProjectBox.setNullSelectionAllowed(false);
		for (ConvertProject project : new UseSQLDatabase().getAvailableProjects()) {
			autoProjectBox.addItem(project);
		}
		autoProjectBox.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(final ValueChangeEvent event) {
                filter.projectFilter.select((ConvertProject) event.getProperty().getValue());
                autoProjectBox.select(null);
            }
        });
	}

	private void buildAuthorSuggestionBox(final ComboBox autoAuthorBox) {
		autoAuthorBox.setInputPrompt("Search for an author");

        // Set the appropriate filtering mode for this example
        autoAuthorBox.setFilteringMode(FilteringMode.STARTSWITH);
        autoAuthorBox.setImmediate(true);
        autoAuthorBox.setNullSelectionAllowed(false);
		for (String author : new UseSQLDatabase().getAvailableAuthors()) {
			autoAuthorBox.addItem(author);
		}
        autoAuthorBox.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(final ValueChangeEvent event) {
                filter.authorsFilter.select((String) event.getProperty().getValue());
                autoAuthorBox.select(null);
            }
        });
	}
	
	/**
	 * Manages click events on the flot chart
	 */
	private void initializeJSHandler(final UIState state) {
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
	}
	
	/**
	 * Fires the event that will repaint the graph
	 * 
	 */
	public void fireChangeAction() {
		linkBox.setValue(state.getStateURI());
		ComponentController.drawMainComponent(graph, state, data);
	}
	
	public Button buildNavigationLayout() {
		createNavComponentLayout();
		
		// XAxis combo box
		List<XAxis> xAxisOptions = XAxis.possibleValues();
		xAxisComboBox = createAxisComboBox(xAxisOptions, "View");
		xAxisComboBox.addValueChangeListener(new Property.ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
//				state.setX((XAxis) xAxisComboBox.getValue());
				if (((XAxis) xAxisComboBox.getValue()) == XAxis.LINESOFCODE) {
					startDateField.setEnabled(false);
				} else {
					startDateField.setEnabled(true);
				}
//				fireChangeAction();
			}
		});
//				
		startDateField = createDateField("Start Date");
        startDateField.setValue(state.getStart().getDateTime().toDate());
		
        endDateField = createDateField("End Date");
        endDateField.setValue(state.getEnd().getDateTime().toDate());
		
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
				
				if (((XAxis) xAxisComboBox.getValue()) != XAxis.LINESOFCODE
						&& checkIfStartDateIsNotLessThanEndDate(startDate, endDate)) {
					displayMessage("Invalid Date Input", "Date range invalid", Notification.Type.ERROR_MESSAGE);
					return;
				}
				
				if (((Set<ConvertProject>) filter.projectFilter.getValue()).size() == 0) {
					displayMessage("No Projects Selected!", "Click 'Select Project' to select one or more projects",
							Notification.Type.WARNING_MESSAGE);
				}				
				state.setX((XAxis) xAxisComboBox.getValue());
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
		
		createShareOptions();
		
		return goButton;
	}

	private void createShareOptions() {
		// link text field
		linkBox = new TextField();
        linkBox.setImmediate(false);
        linkBox.setCaption("Copy this Link");
        linkBox.setWidth("700px");
		
		// share button
		shareButton = new Button();
		//shareButton.setIcon(ICON);
		shareButton.setCaption("Share/Bookmark");
		shareButton.setImmediate(false);
		shareButton.setWidth(DEFAULT_VALUE);
		shareButton.setHeight(DEFAULT_VALUE);
		shareButton.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().removeWindow(w);
				w = new Window("Use this link to bookmark this view");
				w.setResizable(false);
				w.setPositionX(500);
                w.setPositionY(140);
                FormLayout f = new FormLayout();
                f.addStyleName("link_box");
                final OptionGroup group = new OptionGroup("Select time options");
                List<DateChoice> choices = DateChoice.possibleValues();
                for (DateChoice choice : choices) {
                    group.addItem(choice);
                }
                group.select(choices.get(0));
                group.setNullSelectionAllowed(false);
                group.setImmediate(true);
                group.addValueChangeListener(new ValueChangeListener() {
                	
                	@Override
                    public void valueChange(final ValueChangeEvent event) {
                        DateChoice selected = (DateChoice) group.getValue();
                        if (!selected.equals(DateChoice.STATIC)) {
                        	linkBox.setValue(state.getStateURI("today", selected.getOffset() + ""));
                		} else {
                			linkBox.setValue(state.getStateURI());
                		}
                    }
                });
        		Button bookMarkDone = new Button("OK");
        		bookMarkDone.addClickListener(new Button.ClickListener() {
        			
        			@Override
        			public void buttonClick(ClickEvent event) {
        				UI.getCurrent().removeWindow(w);
        			}
        		});
                f.addComponent(linkBox);
                f.addComponent(group);
        		f.addComponent(bookMarkDone);
				linkBox.setValue(state.getStateURI());
                w.setContent(f);
		        UI.getCurrent().addWindow(w);
			}	
		});
	}

	private PopupDateField createDateField(String tag) {
		PopupDateField field = new PopupDateField(tag);
        field.setImmediate(true);
        field.addStyleName("dates");
        field.setDateFormat("MM/dd/yyyy");
        field.setResolution(Resolution.DAY);
        field.setRangeEnd(new DateTime().toDate()); // sets current day as max date
        return field;
	}

	private void createNavComponentLayout() {
		// common part: create layout
		navLayout = new AbsoluteLayout();
		navLayout.setImmediate(false);
	}

	private void displayMessage(String message, String desc, Notification.Type type) {
		Notification popUp = new Notification(message, desc, type);
		popUp.show(Page.getCurrent());
		popUp.setPosition(Position.TOP_CENTER);
	}
	
	private ComboBox createAxisComboBox(List<XAxis> options, String tag) {
		ComboBox box = createComboBoxWithLabel(tag, true);
		box.addStyleName("axis");
		for (int i = 0; i < options.size(); i++) {
			box.addItem(options.get(i));
			box.setItemCaption(options.get(i), options.get(i).getView());
		}
		box.select(state.getX());
		return box;
	}

	private ComboBox createComboBoxWithLabel(String label, boolean immediate) {
		ComboBox comboBox = new ComboBox(label);
		comboBox.setImmediate(immediate);
		comboBox.setNullSelectionAllowed(false);
		comboBox.setTextInputAllowed(false);
		comboBox.setWidth("280px");
		comboBox.setHeight(DEFAULT_VALUE);
		return comboBox;
	}
	
	private enum DateChoice {
		STATIC("Current Date Settings", 0),
		ONE_WEEK("Last Week", 7),
		TWO_WEEKS("Last Two Weeks", 14),
		MONTH("Last Month (30 days)", 30);
		
		private String display;
		private int value;
		
		private DateChoice(String display, int value) {
			this.display = display;
			this.value = value;
		}
		
		@Override
		public String toString() {
			return display;
		}
		
		public int getOffset() {
			return value;
		}
		
		public static List<DateChoice> possibleValues() {
			return new ArrayList<DateChoice>(EnumSet.allOf(DateChoice.class));
		}
	}
}