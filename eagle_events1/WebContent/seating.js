var app = angular.module('myApp', []);

app.controller('seatingCtrl', [ '$scope', '$http', '$timeout',
		function($scope, $http, $timeout) {

			$scope.eventid = 0;
			$scope.loading = false;
			$scope.orderbytable = false;
			$scope.orderbyname = false;
	
			$scope.getEdat = function() {
				$http({
					method : 'get',
					url : 'servletEvnt'
				}).then(function successCallback(response) {
					$scope.evnts = response.data;
				});
			};
			
			$scope.fetchEventbyId = function() {

				$http.post("FetchEventDetailsbyIdServlet", {
					'eventid' : $scope.eventid,
					'action' : ''
				}).then(function onSuccess(response) {
					$scope.showingevent = response.data;
				});
			};

			$scope.getEdat();
			
			$scope.printDiv = function(divName) {
//				  var printContents = document.getElementById(divName).innerHTML;
//				  var popupWin = window.open('', '_blank');
//				  popupWin.document.open();
//				  popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" href="style.css" /><link rel="stylesheet" type="text/css" href="bootstrap.css" /></head><body onload="window.print()">' + printContents + '</body></html>');
//				  popupWin.document.close();
				
				  
				  var mywindow = window.open('', 'PRINT', 'height=400,width=600');

				    mywindow.document.write('<html><head><title>' + document.title  + '</title>');
				    mywindow.document.write('</head><body >');
				    mywindow.document.write('<h1>' + document.title  + '</h1>');
				    mywindow.document.write(document.getElementById(divName).innerHTML);
				    mywindow.document.write('</body></html>');

				    mywindow.document.close(); // necessary for IE >= 10
				    mywindow.focus(); // necessary for IE >= 10*/

				    mywindow.print();
				    mywindow.close();

				    return true;
				console.log(divName);
				
//				var doc = new jsPDF();
//				var specialElementHandlers = {
//				    '#editor': function (element, renderer) {
//				        return true;
//				    }
//				};
//				
//				  doc.fromHTML($('#'+divName)[0], 15, 15, {
//				        'width': 170
//				    });
//				 doc.save('guestsseating.pdf');

				
		}
			
			
			$scope.getCanvas = function() {	
				form.width((a4[0] * 1.33333) - 80).css('max-width', 'none');
				return html2canvas(form, {
					imageTimeout : 2000,
					removeContainer : true
				});
			}
			
			
			$scope.createPDF = function() {	
			$scope.getCanvas().then(function(canvas) {
					var img = canvas.toDataURL("image/png"), doc = new jsPDF({
						unit : 'px',
						format : 'a4'
					});
					doc.addImage(img, 'JPEG', 20, 20);
					doc.save('techumber-html-to-pdf.pdf');
					form.width(cache_width);
				});
			}
		   var form;
			$scope.printPdf = function(divName) {
				
				 form  = $('#'+divName), cache_width = form.width(),
						a4 = [ 595.28, 841.89 ];
				$('body').scrollTop(0);
				$scope.createPDF();
				
			};
			

			$scope.getSeatingArragement = function() {

				console.log();
				$scope.loading = true;
				$http.post("SeatingArrangementServlet", {
					'eventid' : $scope.eventid,
					'order' : $scope.order
				}).then(function onSuccess(response) {
					$scope.eventseatings = response.data;
					console.log(response.data);
					$scope.loading = false;
					$scope.fetchEventbyId();

                    
					if($scope.order=="table"){
						$scope.orderbytable = true;
						$scope.orderbyname = false;

					}else{
						$scope.orderbyname = true;
						$scope.orderbytable = false;
					}

				});
			};

		} ]);