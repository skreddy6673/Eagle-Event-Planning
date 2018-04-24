var app = angular.module('myApp', ['dualmultiselect']);


app.controller('singleGuestCtrl', [
		'$scope',
		'$http',
		'$timeout',
		'$window',
		function($scope, $http, $timeout, $window) {

			
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
			$scope.eventid = $window.localStorage.getItem("eventid");
			$scope.singleguestmode = $window.localStorage.getItem("singleguestmode");
			$scope.guestid = $window.localStorage.getItem("guestid");
			
			
			$scope.fetchEventbyId = function() {

				$http.post("FetchEventDetailsbyIdServlet", {
					'eventid' : $scope.eventid,
					'action' : ''
				}).then(function onSuccess(response) {
					$scope.showingevent = response.data;
				});
			};
			
			
			$scope.fetchEventbyId();
			
			
			
			$scope.fetchsingleguest = function() {
			 $http.post("FetchSingleGuestServlet", {
				'guestid' : $scope.guestid,
				'eventid' : $scope.eventid
				
			 }).then(function onSuccess(response) {
				var guestinfo = response.data;
				$scope.firstname = guestinfo.guest.firstname;
				$scope.lastname = guestinfo.guest.lastname;
			//	$scope.sitwithOptions.selectedItems = guestinfo.sitWith;
				//$scope.notsitwithOptions.selectedItems = guestinfo.notsitWith;
				 $scope.setMultiselectData(guestinfo.sitWith,guestinfo.notsitWith); 

			 });
			};
			
			$scope.fetchGuests = function() {

				$http.post("FetchGuestsServlet", {
					'eventid' : $scope.eventid,
					'action' : ''
				}).then(function onSuccess(response) {
					$scope.guests = response.data;
					console.log(response.data);
					
					$scope.guests1= [];
					angular.copy($scope.guests, $scope.guests1);
					
				

					 if($scope.singleguestmode=="edit"){
						 $scope.fetchsingleguest();
					 }
					 else{
						 $scope.setMultiselectData([],[]); 
					 }
				});
			};
			
			$scope.setMultiselectData = function(selectedItemssitwith,selectedItemsnotsitwith) {
				
				$scope.sitwithOptions = {
						title: 'People Should Sit with',
						filterPlaceHolder: 'Start typing to filter the lists below.',
						labelAll: 'All Guests',
						labelSelected: 'Selected',
						helpMessage: ' Click items to transfer them between fields.',
						/* angular will use this to filter your lists */
						orderProperty: 'name',
						/* this contains the initial list of all items (i.e. the left side) */
						items: $scope.guests,
						/* this list should be initialized as empty or with any pre-selected items */
						selectedItems: selectedItemssitwith
					};
				
				$scope.notsitwithOptions = {
						title: 'People Should not sit with',
						filterPlaceHolder: 'Start typing to filter the lists below.',
						labelAll: 'All',
						labelSelected: 'Selected',
						helpMessage: ' Click items to transfer them between fields.',
						/* angular will use this to filter your lists */
						orderProperty: 'name',
						/* this contains the initial list of all items (i.e. the left side) */
						items: $scope.guests1,
						/* this list should be initialized as empty or with any pre-selected items */
						selectedItems: selectedItemsnotsitwith
					};
			};
			$scope.addGuest = function() {
				
				console.log($scope.sitwithOptions.selectedItems);
				console.log($scope.notsitwithOptions.selectedItems);
				
				var sitwithselectedItems =  $scope.sitwithOptions.selectedItems;
				var notsitwithselectedItems =  $scope.notsitwithOptions.selectedItems;

				var preferredguests = "";
	             angular.forEach(sitwithselectedItems, function(item){
					preferredguests = preferredguests+item.guestid+"-";
				
				});
	             
	         	var notpreferredguests = "";
	             angular.forEach(notsitwithselectedItems, function(item){
	            	 notpreferredguests = notpreferredguests+item.guestid+"-";
				
				});

	             var mode = "insert";
	             var guestid = "";
	             if($scope.singleguestmode=="edit"){
	            	 mode = "edit";
	            	 guestid = $scope.guestid;
	             }
				
				$http.post("AddSingleGuestServlet", {
					'firstname' : $scope.firstname,
					'lastname' : $scope.lastname,
					'eventid' : $scope.eventid,
					'preferredguests' : preferredguests,
					'notpreferredguests' : notpreferredguests,
					'mode' : mode,
					'guestid' : guestid
					
				}).then(function onSuccess(response) {
					$scope.guests = response.data;
					console.log(response.data);
					bootbox.alert("Guest added/Updated successfully", function(){ 
						$window.location.href = 'guests.html#'+$scope.eventid;

					});

				});
			}
			
			$scope.fetchGuests();
		} ]);