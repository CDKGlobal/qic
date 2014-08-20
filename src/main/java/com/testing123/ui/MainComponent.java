package com.testing123.ui;


import java.util.Iterator;

import com.testing123.controller.UIState;
import com.testing123.controller.UIState.XAxis;
import com.testing123.dataObjects.ConvertProject;
import com.testing123.dataObjects.DataPointSet;
import com.testing123.dataObjects.FooterData;
import com.testing123.vaadin.FooterSummary;
import com.testing123.vaadin.GetData;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class MainComponent extends CustomComponent {
    public static final String PANEL_WIDTH = QicUI.COMPONENT_WIDTH;
    public static final String PANEL_HEIGHT = QicUI.COMPONENT_HEIGHT;
    public static final String GRAPH_WIDTH = QicUI.GRAPH_WIDTH;
    public static final String GRAPH_HEIGHT = QicUI.GRAPH_HEIGHT;

    private final VerticalLayout container;
    private AbsoluteLayout graph;
    private final UIState state;
    private final DataPointSet queried;

    /**
     * The constructor should first build the main layout, set the
     * composition root and then do any custom initialization.
     *
     * The constructor will not be automatically regenerated by the
     * visual editor.
     */
    public MainComponent(UIState state, GetData data) {
        container = new VerticalLayout();
        this.state = state;
        queried = data.requestData(state);
        buildMainLayout();
        setCompositionRoot(container);
        // setCompositionRoot(getSummary(state.getX(), data.requestData(state)));
    }

    @AutoGenerated
    private AbsoluteLayout buildMainLayout() {
        // top-level component properties
        setWidth(PANEL_WIDTH);
        setHeight("100%");

        // Graph
        graph = buildGraph();
        Label numberOfProjectAndAuthorSelectedLabel = getNumberOfProjectAndAuthorSelected(state);
        Label projectSelectLabel = getProjectSelected(state);
        Label authorSectectLabel = getAuthorSelected(state);
        numberOfProjectAndAuthorSelectedLabel.addStyleName("selectLabelDisplay");
        container.addComponent(numberOfProjectAndAuthorSelectedLabel);
        container.addComponent(projectSelectLabel);
        container.addComponent(authorSectectLabel);
        container.addComponent(graph);
        HorizontalLayout labelLayout = new HorizontalLayout();
        Label titleLabel = new Label("Summary: ");
        titleLabel.setContentMode(ContentMode.HTML);
        // summaryLabel.setWidth("800px");
        Label textLabel = getSummary(state.getX(), queried);
        textLabel.setWidth("505px");
        titleLabel.setId("labelForTitle");
        textLabel.setId("labelForText");
        labelLayout.addComponent(titleLabel);
        labelLayout.addComponent(textLabel);
        container.addComponent(labelLayout);
        return graph;
    }

    @AutoGenerated
    public AbsoluteLayout buildGraph() {
        // common part: create layout
        graph = new AbsoluteLayout();
        graph.setImmediate(false);
        graph.setWidth(PANEL_WIDTH);
        graph.setHeight(PANEL_HEIGHT);

        //		Label graphName = new Label("<b>" + state.getY() + " vs " + state.getX() + "</b>", ContentMode.HTML);
        //		graphName.setStyleName("h2");
        //		absoluteLayout_2.addComponent(graphName);
        //		Label dateRange = new Label(state.getStart()
        //				+ " to " + state.getEnd());
        //		dateRange.setStyleName("p");
        //		absoluteLayout_2.addComponent(dateRange, "top: 20px;");

        Graph chart = new Graph();
        chart.setWidth(GRAPH_WIDTH);
        chart.setHeight(GRAPH_HEIGHT);

        String d = queried.toString();
        chart.setData(d);
        System.out.println(d);

        chart.setOptions(FlotOptions.getString(state));

        graph.addComponent(chart, "top: 25px");
        return graph;
    }

    public Label getSummary(XAxis xValue, DataPointSet dataPointList) {
        String summary = "";
        FooterData ftData = FooterSummary.getFooterData(dataPointList);
        FooterData ftDataByFile = FooterSummary.getFooterDataByFile(dataPointList);
        if (xValue.equals(XAxis.DELTA_LINESOFCODE)) {
            summary += " Churn : " + ftData.getTotal() + " lines of code modified.";
        } else if (xValue.equals(XAxis.DELTA_COMPLEXITY)) {
            summary += " Total Change in Cyclomatic Complexity for the project(s) : " + ftData.getTotal() + " ( <font color=\"red\">+" + ftData.getPositive() + "</font>, <font color=\"green\">- " + ftData.getNegative() + "</font> ).";
            summary += "\n" + "Change in Cyclomatic Complexity by files : " + ftDataByFile.getTotal() + " ( " + ftDataByFile.getPositive() + " + , " + ftDataByFile.getNegative() + " - ).";
        } else if (xValue.equals(XAxis.LINESOFCODE)) {
            summary += " Total Change in Non Commented Lines of Code for the project(s) : " + ftData.getTotal() + " ( +" + ftData.getPositive() + ", -" + ftData.getNegative() + " )";
            summary += "\n" + "Change in Non Commented Lines of Code by files : " + ftDataByFile.getTotal() + " ( " + ftDataByFile.getPositive() + " + , " + ftDataByFile.getNegative() + " -)";
        }
        Label summaryLabel = new Label(summary, ContentMode.HTML);
        return summaryLabel;
    }

    public Label getNumberOfProjectAndAuthorSelected(UIState state) {
        int numberOfProjects = state.getProjects().size();
        int numberOfAuthors = state.getAuthorsFilter().size();
        String summary = "";
        if (state.getX() != XAxis.LINESOFCODE) {
        	summary = "From: " + state.getStart().toString() + " to " + state.getEnd().toString() + ", ";
        } else {
        	summary = "On: " + state.getStart().toString() + ", ";
        }
        summary += "\tView: " + state.getX().getView() + ". \t";
        summary += numberOfProjects + " projects and ";
        if (numberOfProjects != 0) {
            if (numberOfAuthors == 0) {
                summary += "all authors(default) selected. ";
            } else {
                summary += numberOfAuthors + " authors selected. ";
            }
        } else {
            summary += numberOfAuthors + " authors selected. ";
        }
        Label numberOfProjectAndAuthorSelected = new Label(summary, ContentMode.TEXT);
        return numberOfProjectAndAuthorSelected;
    }

    public Label getProjectSelected(UIState state) {
        int numberOfProjects = state.getProjects().size();
        String summary = "";
        if (numberOfProjects != 0) {
            summary += "<b>Project(s) Selected:</b> [ ";
            Iterator<ConvertProject> iter = state.getProjects().iterator();
            summary += iter.next().getName();
            while (iter.hasNext()) {
                summary += ", " + iter.next().getName();
            }
        }

        if (summary.length() > 350) {
            summary = summary.substring(0, 350);
            summary += "... ]";
        } else if (summary.length() != 0) {
            summary += " ]";
        }
        Label projectSelected = new Label(summary, ContentMode.HTML);
        return projectSelected;
    }

    public Label getAuthorSelected(UIState state) {
        int numberOfAuthors = state.getAuthorsFilter().size();
        String summary = "";
        if (numberOfAuthors != 0) {
            summary += "<b>Author(s) Selected: </b> [ ";

            Iterator<String> iter = state.getAuthorsFilter().iterator();
            summary += iter.next();
            while (iter.hasNext()) {
                summary += ", " + iter.next();
            }
        }

        if (summary.length() > 380) {
            summary = summary.substring(0, 380);
            summary += "... ]";
        } else if (summary.length() != 0) {
            summary += " ]";
        }

        Label authorSelected = new Label(summary, ContentMode.HTML);
        return authorSelected;
    }

}
