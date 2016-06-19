var customerServices = angular.module('customer.service', []);

customerServices.factory('myHttpInterceptor', function($q) {
	return {
		'responseError' : function(rejection) {
			// do something on error
			if (rejection.status === 403) {
				return rejection;
			}
			return $q.reject(rejection);
		}
	};
});

customerServices.config([ '$httpProvider', function($httpProvider) {
	$httpProvider.interceptors.push('myHttpInterceptor');
} ]);

customerServices.factory('customerService', [ '$log', '$http',
		function($log, $http) {
			var factory = {};
			var url = "http://localhost:8070/customers/";

			factory.getCustomers = function(callback) {
				console.log("all");
				$http.get(url).then(function success(response) {
					callback(response.data);
				}, function error(response) {
					callback("failed");
				});
			}

			factory.getCustomer = function(id, callback) {
				console.log("one: " + id);
				$http.get(url + id).then(function success(response) {
					console.log(response.data);
					callback(response.data);
				}, function error(response) {
					callback("failed");
				});
			}

			factory.updateCustomer = function(customer, callback) {
				$http.put(url + customer.id, customer, {
					headers : {
						"Content-Type" : "application/json"
					}
				}).then(function success(response) {
					console.log(response.data);
					callback(response.data);
				}, function error(response) {
					callback("failed");
				});
			}

			factory.saveCustomer = function(customer, callback) {
				$http.post(url, customer, {
					headers : {
						"Content-Type" : "application/json"
					}
				}).then(function success(response) {
					console.log(response.data);
					callback(response.data);
				}, function error(response) {
					callback("failed");
				});
			}

			factory.logout = function(token, callback) {
				$("#_csrf").val(token);
				$.ajax({
					url : "http://localhost:8070/logout",
					type : "POST",
					data : $("#logoutForm").serialize(),
					async : false,
					success : function() {
						$("#logoutForm").submit();
						callback("ok");
					}
				});
			}

			return factory;
		} ]);