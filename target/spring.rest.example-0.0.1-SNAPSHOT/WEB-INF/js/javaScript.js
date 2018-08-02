$("#showAll").on(
		"click",
		function() {
			$("#monitor").empty();
			$.ajax({
				type : "GET",
				url : "/spring.rest.example/ref/all",
				success : function(result) {
					$(result).each(
							function() {
								$("#monitor").append(
										"<p>" + this.name + " " + this.nickname
												+ "</p>");
							});

				},
				error : function() {

					$("#monitor").append("<h4>" + "error occured" + "</h4>");
				}

			});
		});

$("#oneById").on(
		"click",
		function() {
			$("#monitor").empty();

			$.ajax({
				type : "GET",
				url : "/spring.rest.example/ref/" + $("#input").val(),
				success : function(result) {
					if (result.name === undefined) {
						$("#monitor").append("no such user");
					} else {
						$("#monitor").append(
								"<p>" + result.name + " " + result.nickname
										+ "</p>");
					}

				},
				error : function() {

					$("#monitor").append("<h4>" + "error occured" + "</h4>");
				}
			});
		});

//$("#update").on(
//		"click",
//		function() {
//			$("#monitor").empty();
//
//			$.ajax({
//				type : "GET",
//				url : "/spring.rest.example/ref/" + $("#input").val(),
//				success : function(result) {
//					if (result.name === undefined) {
//						$("#monitor").append("no such user");
//					} else {
//						$("#monitor").append(
//								"<p>" + result.name + " " + result.nickname
//										+ "</p>");
//					}
//
//				},
//				error : function() {
//
//					$("#monitor").append("<h4>" + "error occured" + "</h4>");
//				}
//			});
//		});




$("#save").on(
		"click",
		function() {
			$("#monitor").empty();
			var person = {"name" : $("#name").val(), "nickname" : $("#nickname").val()}
			var send = JSON.stringify(person);
//			var send2 = person.serialize();
			$.ajax({
				   type: "POST",
				   traditional: true,
				   dataType: "json",
				   contentType: "application/json; charset=utf-8",
				   url: "/spring.rest.example/ref/save",
				   data: send,
				   
				   success: function( data ){
	                    $('#monitor').append(data.content);
	                },
				   error: function(result){
					   $("#monitor").append("some error");
				   },
	                complete: function(data) {
	                	$('#monitor').html( data );
					}
			});
		});
























