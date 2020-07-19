$(document).ready(function () {
	addButtonListeners();
});

function addButtonListeners() {

	let selectedValues;
	
	$('#sidebarCollapse').on('click', function () {
		$('#sidebar').toggleClass('active');
		$(this).toggleClass('active');
	});

	var id = getUrlParameter('id');

	$('#pharmacyBtn').unbind("click").click(allPharmacy(event));

	$('#modalPositiveBtnId').unbind("click").click(function () {
		selectedValues = $("input:checkbox:checked", "#pharmacyTbody").map(function() {
			return $(this).val();
		}).get();
		$('#medicinePharmacyModal').modal('toggle');
		$("form#medicineForm").unbind('submit').submit(submitFunction(event, selectedValues));
	});

	if(id != undefined){
		editMedicine(id, selectedValues);
	}
	else{
		$("form#medicineForm").unbind('submit').submit(submitFunction(event, selectedValues));
	}

}

function submitFunction(e, selectedValues) {
	return function(e) {
		e.preventDefault();
		var d = {};
		d.name = $('#name').val();
		d.price = $('#price').val();
		d.availableAmount = $('#availableAmount').val();
		
		if(selectedValues!=undefined){
			var pharmacy=[];
			for(i = 0; i < selectedValues.length; i++){
				item = {}
				item.id=selectedValues[i];
				pharmacy.push(item);
			}
			d.pharmacy=pharmacy;
		}
		
		$.ajax({
			url: 'http://localhost:8080/medicine',
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(d),
			success: function (data) {
				window.location.replace("./medicine.html");
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
					str += '<td scope="row" style="width:30px"><div><input type="checkbox" class="checkBoxClass" value="'+data[i].id+'"></td></tr>';
				}
				$("#pharmacyTbody").append(str);
				$('#medicinePharmacyModal').modal('show');

			},
			error: function (message) {
				console.log(message.responseText);
			}
		});
	}
}

function editMedicine(id, selectedValues) {
	$('#pharmacyTbody').empty();

    $.ajax({
        url: 'http://localhost:8080/medicine/' + id,
        type: 'get',
        contentType: 'application/json',
        success: function (medicineData) {
			$.ajax({
				url: 'http://localhost:8080/pharmacy',
				type: 'GET',
				contentType: 'application/json',
				success: function (data) {
					
					$('#name').val(medicineData.name);
					$('#price').val(medicineData.price);
					$('#availableAmount').val(medicineData.availableAmount);

					str ="";
					for (i in data) {
						str += '<tr><td>'+data[i].name+'</td>';
						str += '<td>'+data[i].address+'</td>';
						str += '<td scope="row"><div><input type="checkbox" class="checkBoxClass" value="'+data[i].id+'"';
						if(containsObject(data[i], medicineData.pharmacy)){
							console.warn(medicineData.pharmacy);
							str+='checked ></td></tr>';
						}
						else{
							str+='></td></tr>';
						}
					}
					$("#pharmacyTbody").append(str);

					//save old values if dialog is not opened
					selectedValues=[];
					for(i=0;i<medicineData.pharmacy.length;i++){
						selectedValues.push(medicineData.pharmacy[i].id);
					}

					$('#submitBtnId').unbind("click").click(sendEditRequest(event, id, selectedValues)); 

					$('#pharmacyBtn').unbind("click").click(function(){
						$('#medicinePharmacyModal').modal('show');

						$('#modalPositiveBtnId').unbind("click").click(function () {
							selectedValues = $("input:checkbox:checked", "#pharmacyTbody").map(function() {
								return $(this).val();
							}).get();
							$('#medicinePharmacyModal').modal('toggle');
							$('#submitBtnId').unbind("click").click(sendEditRequest(event, id, selectedValues)); 
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

function sendEditRequest(e, id, selectedValues) {
	return function(e) {
		e.preventDefault();
		var d = {};
		d.id = id;
		d.name = $('#name').val();
		d.price = $('#price').val();
		d.availableAmount = $('#availableAmount').val();
		console.warn(selectedValues);
		if(selectedValues!=undefined){
			var pharmacy=[];
			for(i = 0; i < selectedValues.length; i++){
				item = {}
				item.id=selectedValues[i];
				pharmacy.push(item);
			}
			d.pharmacy=pharmacy;
		}
		$.ajax({
			url: 'http://localhost:8080/medicine/' + id,
			type: 'PUT',
			contentType: 'application/json',
			data: JSON.stringify(d),
			success: function (data) {
				window.location.replace("./medicine.html");
			},
			error: function (message) {
				console.log(message.responseText);
			}
		});
	}
}

function containsObject(obj, list) {
	var i;
	for (i = 0; i < list.length; i++) {
		if (list[i].id === obj.id) {
			return true;
		}
	}
	return false;
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