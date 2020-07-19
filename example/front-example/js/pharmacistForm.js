$(document).ready(function () {
	addButtonListeners();
});

function addButtonListeners() {
	
	$('#sidebarCollapse').on('click', function () {
		$('#sidebar').toggleClass('active');
		$(this).toggleClass('active');
	});

	var id = getUrlParameter('id');

	$('#pharmacyBtn').unbind("click").click(allPharmacy(event));

	if(id != undefined){
		editPharmacist(id);
	}
	else{
		$("form#pharmacistForm").unbind('submit').submit(submitFunction(event));
	}

}

function submitFunction(e, pharmacyId) {
	return function(e) {
		e.preventDefault();
		var d = {};
		d.firstName = $('#firstName').val();
		d.lastName = $('#lastName').val();
		d.email = $('#email').val();
		d.address = $('#address').val();
		d.dateOfBirth = $('#dateOfBirth').val();
		d.phoneNumber = $('#phoneNumber').val();
		
		if(pharmacyId!=undefined){
			var pharmacy={};
			pharmacy.id=pharmacyId;
			d.pharmacy=pharmacy;
		}
		
		$.ajax({
			url: 'http://localhost:8080/pharmacist',
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(d),
			success: function (data) {
				window.location.replace("./pharmacist.html");
			},
			error: function (message) {
				console.log(message.responseText);
			}
		});
	}
}

function allPharmacy(e) {
	return function(e) {
		e.preventDefault();
		$('#pharmacyTbody').empty();

		$.ajax({
			url: 'http://localhost:8080/pharmacy',
			type: 'GET',
			contentType: 'application/json',
			success: function (data) {
				str ="";
				for (i in data) {
					str += '<tr><td>'+data[i].name+'</td>';
					str += '<td>'+data[i].address+'</td>';
					str += '<td><input type="radio" id="'+data[i].id+'" name="pharmacy" value="'+data[i].id+'"></td></tr>'
				}
				$("#pharmacyTbody").append(str);
				$('#pharmacistPharmacyModal').modal('show');
				
				$('#modalPositiveBtnId').unbind("click").click(choosePharmacy(event));

			},
			error: function (message) {
				console.log(message.responseText);
			}
		});
	}
}

function choosePharmacy(e){
	return function(e) {
		e.preventDefault();
		let pharmacyId=$('input[name="pharmacy"]:checked').val();
		$('#pharmacistPharmacyModal').modal('toggle');
		$("form#pharmacistForm").unbind('submit').submit(submitFunction(event, pharmacyId));
		return pharmacyId;
	}
}

function editPharmacist(id) {
	$('#pharmacyTbody').empty();

    $.ajax({
        url: 'http://localhost:8080/pharmacist/' + id,
        type: 'get',
        contentType: 'application/json',
        success: function (pharmacistData) {
			$.ajax({
				url: 'http://localhost:8080/pharmacy',
				type: 'GET',
				contentType: 'application/json',
				success: function (data) {
					var date = new Date(pharmacistData.dateOfBirth);
					var day = ("0" + date.getDate()).slice(-2);
					var month = ("0" + (date.getMonth() + 1)).slice(-2);
					var selectedDate = date.getFullYear()+"-"+(month)+"-"+(day) ;
					$('#firstName').val(pharmacistData.firstName);
					$('#lastName').val(pharmacistData.lastName);
					$('#email').val(pharmacistData.email);
					$('#address').val(pharmacistData.address);
					$('#dateOfBirth').val(selectedDate);
					$('#phoneNumber').val(pharmacistData.phoneNumber);
					$('#availableAmount').val(pharmacistData.availableAmount);

					str ="";
					for (i in data) {
						str += '<tr><td>'+data[i].name+'</td>';
						str += '<td>'+data[i].address+'</td>';
						str += '<td><input type="radio" id="'+data[i].id+'" name="pharmacy" value="'+data[i].id+'"';
						if(pharmacistData.pharmacy.id == data[i].id){
							str+='checked ></td></tr>';
						}
						else{
							str+='></td></tr>';
						}
					}
					$("#pharmacyTbody").append(str);
	
					if(pharmacistData.pharmacy!=null){
						$('#submitBtnId').unbind("click").click(savedEdit(event, id, pharmacistData.pharmacy.id));
					}
					else{
						$('#submitBtnId').unbind("click").click(savedEdit(event, id, undefined));
					}

					$('#pharmacyBtn').unbind("click").click(function(){
						$('#pharmacistPharmacyModal').modal('show');
						
						$('#modalPositiveBtnId').unbind("click").click(function(event){
							event.preventDefault();
							let pharmacyId=$('input[name="pharmacy"]:checked').val();
							$('#pharmacistPharmacyModal').modal('toggle');
							$("form#pharmacistForm").unbind('submit').submit(submitFunction(event, pharmacyId));
							$('#submitBtnId').unbind("click").click(savedEdit(event, id, pharmacyId));
						});
		
					});
		
				},
				error: function (message) {
					console.log(message.responseText);
				}
			});

        },
        error: function (message) {
            console.log(message.responseText);
        }
    });

}

function savedEdit(e, pharmacistId, pharmacyId){
	return function(e) {
		e.preventDefault();
		var d = {};
		d.firstName = $('#firstName').val();
		d.lastName = $('#lastName').val();
		d.email = $('#email').val();
		d.address = $('#address').val();
		d.dateOfBirth = $('#dateOfBirth').val();
		d.phoneNumber = $('#phoneNumber').val();
		d.id = pharmacistId;
		if(pharmacyId!=undefined){
			var pharmacy={};
			pharmacy.id=pharmacyId;
			d.pharmacy=pharmacy;
		}
		$.ajax({
			url: 'http://localhost:8080/pharmacist/' + pharmacistId,
			type: 'PUT',
			contentType: 'application/json',
			data: JSON.stringify(d),
			success: function (data) {
				window.location.replace("./pharmacist.html");
			},
			error: function (message) {
				console.log(message.responseText);
			}
		});

	}
}

var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = window.location.search.substring(1),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
        }
    }
};