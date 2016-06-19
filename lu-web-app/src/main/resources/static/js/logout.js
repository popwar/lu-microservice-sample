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

$(document).ready(function() {
	$("#logout").click(function() {
		logout();
	});

	var logout = function(){
		$("#X-CSRF-TOKEN").val(readCookie("XSRF-TOKEN"));
		$.ajax({
			url : "http://localhost:8090/uaa/logout",
			type : "POST",
			data : $("#logoutForm").serialize(),
			async : true,
			success : function() {
				$("#logoutForm").submit();
				window.location.href = "http://localhost:8070/";
			}
		});
	}
});