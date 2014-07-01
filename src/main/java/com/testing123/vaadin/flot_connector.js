com_testing123_vaadin_Graph = function() {
	var element = $(this.getElement());

	this.onStateChange = function() {
		var state = this.getState();
		var options = state.options;
		var data = state.data;
		$.plot(element, data, options);
	}
	
	element.bind("plothover", function(event, pos, item) {
		$("#tooltip").remove();
		$('<div id="tooltip">' + item.series.data[item.dataIndex][2]+'<br />'+"["+item.datapoint[0]+","+item.datapoint[1]+"]" + '</div>').css( {
			position: 'absolute',
			top: item.pageY + 5,
			left: item.pageX + 5,
			border: '1px solid #fdd',
			padding: '2px',
			'background-color': '#fee',
			opacity: 0.80
		}).appendTo("body").fadeIn(200); 
	});
}