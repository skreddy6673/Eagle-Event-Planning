
app.directive('file', function() {
	return {
		scope : {
			file : '='
		},
		link : function(scope, el, attrs) {
			el.bind('change', function(event) {
				var file = event.target.files[0];
				scope.file = file ? file : undefined;
				scope.$apply();
			});
		}
	};
});

app.controller('guestsCtrl', [ '$scope', '$http', '$timeout',
		'sharedservice', '$window','$location',
		function($scope, $http, $timeout, sharedservice, $window,$location) {

			$scope.eventid = 0;
			$scope.loading = false;
			$scope.getEdat = function() {
				$http({
					method : 'get',
					url : 'servletEvnt'
				}).then(function successCallback(response) {
					$scope.evnts = response.data;
				});
			};

			$scope.getEdat();
			$scope.printDiv = function(divName) {
				  var printContents = document.getElementById(divName).innerHTML;
//				  var popupWin = window.open('', 'new div', 'height=400,width=600');
//				  popupWin.document.open();
//				  popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" href="style.css" /><link rel="stylesheet" type="text/css" href="bootstrap.css" /></head><body onload="window.print()">' + printContents + '</body></html>');
//				  popupWin.document.close();
//				  
//				  popupWin.focus();
//			      setTimeout(function(){popupWin.print();},2000);
//			      popupWin.close();
				  
				  var mywindow = window.open('', 'new div', 'height=400,width=600');
			      mywindow.document.write('<html><head><title></title>');
			      mywindow.document.write('<link rel="stylesheet" href="bootstrap.css" type="text/css" />');
			      mywindow.document.write('</head><body >');
			      mywindow.document.write(printContents);
			      mywindow.document.write('</body></html>');
			      mywindow.document.close();
			      mywindow.focus();
			      setTimeout(function(){mywindow.print();},2000);
			      mywindow.close();

			      return true;
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
					doc.save('guestcards.pdf');
					form.width(cache_width);
				});
			}
		   var form;
			$scope.printPdf = function() {
				
				 form  = $('#guestschart'), cache_width = form.width(),
						a4 = [ 595.28, 841.89 ];
				$('body').scrollTop(0);
				$scope.createPDF();
				
			};
			
			
			$scope.uploaddata = function() {
				
				if($scope.eventid==null || $scope.eventid==undefined || $scope.eventid==""){
					$scope.selecteventiderror();
					
				}

				else {
				$scope.loading = true;

				var formData = new FormData();
				formData.append("file", $scope.file);
				formData.append('eventid', $scope.eventid);
				$http({
					url : 'FileUploadServlet',
					method : 'POST',
					data : formData,

					headers : {
						'Content-Type' : undefined
					},
					transformRequest : angular.identity
				}).then(function successCallback(response) {
					$scope.loading = false;

				});
				}

			};

			$scope.fetchGuests = function() {

				$http.post("FetchGuestsServlet", {
					'eventid' : $scope.eventid,
					'action' : ''
				}).then(function onSuccess(response) {
					$scope.guests = response.data;
					console.log(response.data);
					$scope.fetchEventbyId();
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

			$scope.addSingleGuest = function() {

				if($scope.eventid==null || $scope.eventid==undefined || $scope.eventid==""){
					$scope.selecteventiderror();
					
				}

				else {
				$window.localStorage.setItem("singleguestmode","add");
				$window.localStorage.setItem("eventid",$scope.eventid);
				$window.location.href = 'addSingleGuest.html';
				}
			};
			
			
			$scope.selecteventiderror = function() {

				bootbox.alert("Please select event id", function(){ 
				});
			};
			$scope.deleteSingleGuest = function() {

				if($scope.eventid==null || $scope.eventid==undefined || $scope.eventid==""){
					$scope.selecteventiderror();
				}
				else {
					var selectedguestindex = document.querySelector('input[name = "selectedguestindex"]:checked').value;
		            var selectedguest = $scope.guests[selectedguestindex];
					$http.post("DeleteSingleGuestServlet", {
						'eventid' : $scope.eventid,
						'guestid' : selectedguest.guestid
					}).then(function onSuccess(response) {
						bootbox.alert("Guest Deleted", function(){ 
						});
						$scope.guests = response.data;

					});
				}
				
			};
			
			
			
			$scope.editSingleGuest = function() {
				if($scope.eventid==null || $scope.eventid==undefined || $scope.eventid==""){
					$scope.selecteventiderror();
				}
				else {
				$window.localStorage.setItem("singleguestmode","edit");
				$window.localStorage.setItem("eventid",$scope.eventid)
		    	var selectedguestindex = document.querySelector('input[name = "selectedguestindex"]:checked').value;
	            var selectedguest = $scope.guests[selectedguestindex];
				$window.localStorage.setItem("guestid",selectedguest.guestid);
				$window.location.href = 'addSingleGuest.html';
				}

			};
			
			console.log("location hash"+$location.hash());
			if($location.hash()!=undefined && $location.hash()!=""){
				$scope.eventid = $location.hash();
				$scope.fetchGuests();
			}
			
		} ]);