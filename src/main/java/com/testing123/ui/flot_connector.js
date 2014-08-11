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
				font-family: 'ariel';
				top: item.pageY + 5,
				left: item.pageX + 5,
				border: '1px solid #fdd',
				padding: '2px',
				'background-color': '#fee',
				opacity: 0.80
			}).appendTo("body").fadeIn(200); 
		}
	});

		// add zoom out button 
/*
	$("<div class='button' style='right:20px;top:20px'>zoom out</div>")
		.appendTo(element)
		.click(function (event) {
			event.preventDefault();
			plot.zoomOut();
		});

	// and add panning buttons

	// little helper for taking the repetitive work out of placing
	// panning arrows

	function addArrow(dir, right, top, offset) {
		$("<img class='button' src='arrow-" + dir + ".gif' style='right:" + right + "px;top:" + top + "px'>")
			.appendTo(element)
			.click(function (e) {
				e.preventDefault();
				plot.pan(offset);
			});
	}

	addArrow("left", 55, 60, { left: -100 });
	addArrow("right", 25, 60, { left: 100 });
	addArrow("up", 40, 45, { top: -100 });
	addArrow("down", 40, 75, { top: 100 });
	*/
}