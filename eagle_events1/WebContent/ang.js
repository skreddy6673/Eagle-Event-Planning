var app = angular.module('myApp', []);
app
		.controller(
				'myCtrl',
				[
						'$scope',
						'$http',
						'$timeout',
						function($scope, $http, $timeout) {
							$scope.url = 'guests.html';
							$scope.err = false;

							$scope.insertmode = true;
							$scope.selectedcid = -1;
							$scope.customerslist = true;
							$scope.eventslist = true;
							$scope.userslist = true;
							$scope.getDat = function() {
								$http({
									method : 'get',
									url : 'servletMain'
								}).then(function successCallback(response) {
									// Store response data
									$scope.users = response.data;
								});
							};

							$scope.getCdat = function() {
								$http({
									method : 'get',
									url : 'servletCust'
								}).then(function successCallback(response) {
									// Store response data
									$scope.custs = response.data;
								});
							};

							$scope.getEdat = function() {
								$http({
									method : 'get',
									url : 'servletEvnt'
								}).then(function successCallback(response) {
									// Store response data
									$scope.evnts = response.data;
									console.log($scope.evnts);
								});
							};

							$scope.getDat();
							$scope.getCdat();
							$scope.getEdat();

							/* validate */
							$scope.validate = function() {
								$http
										.post("servletValid", {
											'name' : $scope.Name,
											'pass' : $scope.pass
										})
										.then(
												function onSuccess(response) {
													// Handle success
													$scope.usr = response.data;
													console.log($scope.usr);
													if ($scope.usr[0].name === $scope.Name
															&& $scope.usr[0].pass === $scope.pass
															&& $scope.usr[0].desig === "admin") {
														window.location.href = 'admin.html';
													} else if ($scope.usr[0].name === $scope.Name
															&& $scope.usr[0].pass === $scope.pass) {
														window.location.href = 'user.html';
													} else {
														$scope.err = true;
														$timeout(function() {
															$scope.err = false;
														}, 2000);
													}
												});

							};

							$scope.logout = function() {
								window.location.href = 'index.html';
							};

							/* end validate */

							$scope.insert = function() {
								$http.post("servletMain", {
									'name' : $scope.name,
									'id' : $scope.eid,
									'sal' : $scope.sal,
									'desig' : $scope.desig,
									'pass' : $scope.pass,
									'method' : 'insert',
									'email' : $scope.email
								}).then(function onSuccess(response) {
									$scope.getDat();
									$scope.showUsersList();
								});
							};
							$scope.update = function() {
								$http.post("servletMain", {
									'name' : $scope.name,
									'id' : $scope.eid,
									'sal' : $scope.sal,
									'desig' : $scope.desig,
									'pass' : $scope.pass,
									'method' : 'update',
									'email' : $scope.email
								}).then(function onSuccess(response) {
									$scope.getDat();
									$scope.showUsersList();

								});
							};
							$scope.del = function() {
								
								var selectedid = document
								.querySelector('input[name = "selectedcid"]:checked').value;
					          	var selectedemp = $scope.users[selectedid];
								
					          	console.log(selectedemp);
								$http.post("servletMain", {
									'name' : "",
									'id' : selectedemp.eid,
									'sal' : "1",
									'desig' : "",
									'pass' : "",
									'method' : 'delete',
									'email' : ""
								}).then(function onSuccess(response) {
									$scope.getDat();

								});
							};

							/* customers */
							$scope.insertCust = function() {
								$http.post("servletCust", {
									'name' : $scope.cname,
									'id' : $scope.cid,
									'email' : $scope.cemail,
									'phone' : $scope.cphone,
									'method' : 'insert'
										
								}).then(function onSuccess(response) {
									$scope.getCdat();
									$scope.showCustomersList();
								});
							};
							$scope.updateCust = function() {

								$http.post("servletCust", {
									'name' : $scope.cname,
									'id' : $scope.cid,
									'email' : $scope.cemail,
									'phone' : $scope.cphone,
									'method' : 'update'
								}).then(function onSuccess(response) {
									$scope.getCdat();
									$scope.showCustomersList();

								});
							};

							$scope.delCust = function() {

								var selectedid = document
										.querySelector('input[name = "selectedcid"]:checked').value;
								var selectedcus = $scope.custs[selectedid];
								$http.post("servletCust", {
									'name' : selectedcus.cname,
									'id' : selectedcus.cid,
									'email' : $scope.cemail,
									'phone' : $scope.cphone,
									'method' : 'delete'
								}).then(function onSuccess(response) {
									$scope.getCdat();
								});
							};
							/* end customers */

							/* events */
							$scope.insertEvnt = function() {
								$http.post("servletEvnt", {
									'name' : $scope.evname,
									'id' : $scope.evid,
									'man' : $scope.evman,
									'date' : $scope.evdate,
									'time' : $scope.evtime,
									'venue' : $scope.evvenue,
									'tablesize' : $scope.evtablesize,
									'chairspertable' : $scope.evchairspertable,
									'customerid' : $scope.evcus,
  									'method' : 'insert'
								}).then(function onSuccess(response) {
									$scope.getEdat();
									$scope.showEventsList();

								});
							};
							$scope.updateEvnt = function() {
								$http.post("servletEvnt", {
									'name' : $scope.evname,
									'id' : $scope.evid,
									'man' : $scope.evman,

									'date' : $scope.evdate,
									'time' : $scope.evtime,
									'venue' : $scope.evvenue,
									'tablesize' : $scope.evtablesize,
									'chairspertable' : $scope.evchairspertable,
									'customerid' : $scope.evcus,
									'method' : 'update'
								}).then(function onSuccess(response) {
									$scope.getEdat();
									$scope.showEventsList();

								});
							};
							$scope.delEvnt = function() {
								
								var selectedid = document
								.querySelector('input[name = "selectedcid"]:checked').value;
					         	var selected = $scope.evnts[selectedid];
						
								$http.post("servletEvnt", {
									'name' : "",
									'id' : selected.id,
									'man' : "",
									'date' : "",
									'time' : "",
									'venue' : "",
									'tablesize' : "",
									'chairspertable' : "",
									'customerid' : "",
									'method' : 'delete'
								}).then(function onSuccess(response) {
									$scope.getEdat();

								});
							};
							/* end events */

							$scope.setURL = function(nurl) {
								$scope.url = nurl;
								console.log(nurl);
							};

							$scope.hideCustomersList = function() {
								$scope.customerslist = false;

							};

							$scope.showCustomersList = function() {
								$scope.customerslist = true;

							};

							$scope.hideEventsList = function() {
								$scope.eventslist = false;

							};

							$scope.showEventsList = function() {
								$scope.eventslist = true;

							};

							$scope.hideUsersList = function() {
								$scope.userslist = false;

							};

							$scope.showUsersList = function() {
								$scope.userslist = true;

							};

							$scope.addEventForm = function() {

								$scope.evname = "";
								$scope.evid = 0;
								$scope.evman = "";

								$scope.evdate = "", 
								$scope.evtime = "",
								$scope.evvenue = "",
								$scope.evtablesize = "",
								$scope.evchairspertable = "",
								
								$scope.evcus = "";
								

								$scope.hideEventsList();
								$scope.insertmode = true;
							};

							$scope.updateEventForm = function() {

								var selectedid = document
										.querySelector('input[name = "selectedcid"]:checked').value;
								var selected = $scope.evnts[selectedid];
								$scope.evname = selected.name;
								$scope.evid = selected.id;
								$scope.evman = selected.man;
								$scope.evcus = selected.customerid;
								$scope.evdate = selected.date, 
								$scope.evtime = selected.time,
								$scope.evvenue = selected.venue,
								$scope.evtablesize = selected.tablesize,
								$scope.evchairspertable = selected.chairspertable,

								$scope.hideEventsList();
								$scope.insertmode = false;

							};

							$scope.addCustomerForm = function() {

								$scope.cname = "";
								$scope.cid = "0";
								$scope.cemail = "";
								$scope.cphone = "";
								
								$scope.hideCustomersList();
								$scope.insertmode = true;
							};

							$scope.updateCustomerForm = function() {

								var selectedid = document
										.querySelector('input[name = "selectedcid"]:checked').value;
								var selectedcus = $scope.custs[selectedid];
								$scope.cname = selectedcus.cname;
								$scope.cid = selectedcus.cid;
								
								$scope.cemail = selectedcus.email;
								$scope.cphone = selectedcus.phone;
								
								$scope.hideCustomersList();
								$scope.insertmode = false;

							};

							$scope.addUserForm = function() {

								$scope.eid = "0";
								$scope.name = "";
								$scope.sal = "";
								$scope.desig = "";
								$scope.pass = "";

								$scope.hideUsersList();
								$scope.insertmode = true;
							};

							$scope.updateUserForm = function() {

								var selectedid = document
										.querySelector('input[name = "selectedcid"]:checked').value;
								var selected = $scope.users[selectedid];
								$scope.eid = selected.eid;
								$scope.name = selected.name;
								$scope.sal = selected.sal;
								$scope.desig = selected.desig;
								$scope.pass = selected.pass;

								$scope.hideUsersList();
								$scope.insertmode = false;

							};
						} ]);