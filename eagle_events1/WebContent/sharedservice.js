var app = angular.module('myApp', []);

app.service('sharedservice', function() {

	var eventid = '';

	return {
		getEventId : function() {
			return eventid;
		},
		setEventId : function(value) {
			eventid = value;
		}
	};
});