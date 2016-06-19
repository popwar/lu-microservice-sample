var readCookie = function(name) {
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ')
			c = c.substring(1, c.length);
		if (c.indexOf(nameEQ) == 0)
			return c.substring(nameEQ.length, c.length);
	}
	return null;
}

var customerControllers = angular.module('customer.controller', [])

customerControllers.controller('MainCustomerCtrl', [ 'customerService',
		'$scope', '$location', mainController ]);

function mainController(customerService, $scope, $location) {
	$scope.access = false;
	customerService.getCustomers(function(customers) {
		if (!customers.error) {
			$scope.access = true;
			console.log(customers);
			$scope.customers = customers;
		}
	});

	$scope.toAdd = function() {
		$location.path("/customers/new");
	}

	$scope.logout = function() {
		var token = readCookie("XSRF-TOKEN");
		customerService.logout(token, function(response) {
			$location.path("/");
		});
	}
}

customerControllers.controller('OperateCustomerCtrl', [ 'customerService',
		'$routeParams', '$location', '$scope', operateController ]);

function operateController(customerService, $routeParams, $location, $scope) {

	$scope.init = function() {
		if ($routeParams.id) {
			customerService.getCustomer($routeParams.id, function(customer) {
				$scope.customer = customer;
			});
		}
	}

	$scope.init();

	$scope.save = function(valid) {
		if (valid) {
			var customer = $scope.customer;
			if ($routeParams.id) {
				customer.id = $routeParams.id;
				customerService.updateCustomer(customer, function(customer) {
					$location.path("/customers");
				});
			} else {
				customerService.saveCustomer(customer, function(customer) {
					$location.path("/customers");
				});
			}
		}
	}
}