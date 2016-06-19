var app = angular.module('myapp', [ 'customer.controller', 'customer.service',
		'ngRoute', 'ngMessages' ]);

app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : '/views/home.html'
	}).when('/customers', {
		templateUrl : '/views/customers.html',
		controller : 'MainCustomerCtrl'
	}).when('/customers/edit/:id', {
		templateUrl : '/views/edit-customer.html',
		controller : 'OperateCustomerCtrl'
	}).when('/customers/new', {
		templateUrl : '/views/new-customer.html',
		controller : 'OperateCustomerCtrl'
	}).otherwise({
		redirectTo : '/'
	});
} ]);

var DECIMAL_REGEXP = /^\d+(\.\d+)?$/;

app.directive('checkAge', function() {
	var checkAge = {
		retrict : 'A',
		require : 'ngModel'
	};

	checkAge.link = function(scope, elm, attrs, ctrl) {
		ctrl.$validators.checkAge = function(viewValue) {
			if (parseInt(viewValue) === 0) {
				return false;
			}
			if (DECIMAL_REGEXP.test(viewValue) && parseInt(viewValue) <= 99) {
				return true;
			}
			return false;
		};
	}

	return checkAge;
});