package com.testing123.vaadin;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class MainComponentVolatile2 extends CustomComponent {

    private static final String PANEL_WIDTH = "1000px";

    private static final String PANEL_HEIGHT = "500px";

    private static final String GRAPH_WIDTH = "800px";

    private static final String GRAPH_HEIGHT = "400px";

    /*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

    @AutoGenerated
    private AbsoluteLayout mainLayout;

    @AutoGenerated
    private TabSheet tabSheet_1;

    @AutoGenerated
    private Table table_1;

    @AutoGenerated
    private AbsoluteLayout absoluteLayout_2;

    @AutoGenerated
    private Label label_1;

    /**
     * The constructor should first build the main layout, set the
     * composition root and then do any custom initialization.
     * 
     * The constructor will not be automatically regenerated by the
     * visual editor.
     */
    public MainComponentVolatile2() {
        buildMainLayout();
        setCompositionRoot(mainLayout);

        // TODO add user code here
    }

    @AutoGenerated
    private AbsoluteLayout buildMainLayout() {
        // common part: create layout
        mainLayout = new AbsoluteLayout();
        mainLayout.setImmediate(false);
        mainLayout.setWidth(PANEL_WIDTH);
        mainLayout.setHeight(PANEL_HEIGHT);

        // top-level component properties
        setWidth(PANEL_WIDTH);
        setHeight(PANEL_HEIGHT);

        // tabSheet_1
        tabSheet_1 = buildTabSheet_1();
        mainLayout.addComponent(tabSheet_1, "top:0.0px;");

        return mainLayout;
    }

    @AutoGenerated
    private TabSheet buildTabSheet_1() {
        // common part: create layout
        tabSheet_1 = new TabSheet();
        tabSheet_1.setImmediate(true);
        tabSheet_1.setWidth(PANEL_WIDTH);
        tabSheet_1.setHeight(PANEL_HEIGHT);

        // absoluteLayout_2
        absoluteLayout_2 = buildAbsoluteLayout_2();
        tabSheet_1.addTab(absoluteLayout_2, "Graph", null);

        // table_1
        table_1 = new Table();
        table_1.setImmediate(false);
        table_1.setWidth("-1px");
        table_1.setHeight("-1px");
        tabSheet_1.addTab(table_1, "Table", null);

        return tabSheet_1;
    }

    @AutoGenerated
    private AbsoluteLayout buildAbsoluteLayout_2() {
        // common part: create layout
        absoluteLayout_2 = new AbsoluteLayout();
        absoluteLayout_2.setImmediate(false);
        absoluteLayout_2.setWidth(PANEL_WIDTH);
        absoluteLayout_2.setHeight(PANEL_HEIGHT);

        Label graphName = new Label("<b>Platform</b>", ContentMode.HTML);
        graphName.setStyleName("h2");
        absoluteLayout_2.addComponent(graphName);

        Graph chart = new Graph();
        chart.setWidth(GRAPH_WIDTH);
        chart.setHeight(GRAPH_HEIGHT);
        // String readData = "";
        // try {
        // readData = Reader.JSONParser("/Users/chenc/Documents/workspace/QIC/src/data/" + "data.json");
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        chart.setData(MapToJsonArray.mapToString(Reader2.getData("ncloc", "complexity")));

        // String optionsData = "";
        // try {
        // optionsData = Reader.JSONParser("/Users/chenc/Documents/workspace/QIC/src/data/" + "qic.json");
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        String options =
                        "{" +
                                        "series : {" +
                                        "bars: { " +
                                        "show: true, " +
                                        "fill: true, " +
                                        "align: center, " +
                                        "barWidth: 5, " +
                                        "fillColor: \"#033F8D\"" +
                                        "} " +
                                        "}, " +
                                        "colors : [\"#033F8D\", \"#033F8D\"]," +
                                        "yaxis : {" +
                                        "show : true," +
                                        "axisLabel : 'Count of Files'," +
                                        "position: 'left'" +
                                        "}, " +
                                        "xaxis : {" +
                                        "show : true," +
                                        "axisLabel : 'Lines of Code'," +
                                        "autoscaleMargin : .02" +
                                        "},"
                                        +
                                        "grid: {"
                                        +
                                        // "backgroundColor: {" +
                                        // "colors: [" +
                                        // "\"#00CCFF\"," +
                                        // "\"#00CCFF\"" +
                                        // "]" +
                                        // "}" +
                                        "}" +
                                        "}";
        chart.setOptions(options);

        absoluteLayout_2.addComponent(chart, "top: 20px;");
        return absoluteLayout_2;
    }
}