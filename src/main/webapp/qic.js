angular.module('myApp', [])
.controller('ViewController', function($scope, $http) {

	var results;
	var opts;

	var sURLVariables = window.location.href.split('?');
	var params = "http://localhost:8080/QIC3/QICServlet?" + sURLVariables[1];

	 $http({method: 'GET', url: params}).success(function(data, status, headers, config) {
			results = data;
			finalPoints = [];
			for (var i in results) {
				var object = results[i];
				var pointSet = object['data'];
				for (var j in pointSet) {
					var eachData = pointSet[j];
					var pt = new Object();
					pt.X = eachData[0];
					pt.Y = eachData[1];
					pt.key = eachData[2];
					finalPoints.push(pt);
				}
			}
			$scope.dataPoints = finalPoints;

			$http({method: 'GET', url: 'http://localhost:8080/QIC3/QICServlet?message=giveOptions'}).success(function(data, status, headers, config) {
					opts = data;
					var element = $("#placeholder");
					$.plot(element, results, opts);

					element.bind("plotclick", function(event, pos, item) {
						if (item) {
							window.notify(item.series.data[item.dataIndex][2]);
						}
					});

					element.bind("plothover", function(event, pos, item) {
						$("#tooltip").remove();
						if (item) {
							$('<div id="tooltip">' + item.series.data[item.dataIndex][2]+'<br />'+item.series.data[item.dataIndex][4]+item.datapoint[0]+'<br />'+'Complexity: '+item.datapoint[1]+ 
								'<br />' + item.series.data[item.dataIndex][3] + '</div>').css( {
									position: 'absolute',
									top: item.pageY + 10,
									left: item.pageX - 200,
									border: '1px solid #fdd',
									padding: '2px',
									'background-color': '#fee',
									opacity: 0.80
								}).appendTo("body").fadeIn(200); 
						}
					});
			});
	});
});
