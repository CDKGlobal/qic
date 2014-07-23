com_testing123_ui_Graph = function() {
	var element = $(this.getElement());

	this.onStateChange = function() {
		var state = this.getState();
		var options = state.options;
		var data = state.data;
		$.plot(element, data, options);
	}
	
	element.bind("plotclick", function(event, pos, item) {
        if (item) {
          	window.notify(item.series.data[item.dataIndex][2]);
        }
    });

	element.bind("plothover", function(event, pos, item) {
		$("#tooltip").remove();
		if (item) {
			$('<div id="tooltip">' + item.series.data[item.dataIndex][2]+'<br />'+"["+item.datapoint[0]+","+item.datapoint[1]+"]" + 
				'<br />' + item.series.data[item.dataIndex][3] + '</div>').css( {
				position: 'absolute',
				top: item.pageY + 5,
				left: item.pageX + 5,
				border: '1px solid #fdd',
				padding: '2px',
				'background-color': '#fee',
				opacity: 0.80
			}).appendTo("body").fadeIn(200); 
		}
	});
}