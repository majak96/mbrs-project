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
		selectedValues= $('#pharmacySelect').val();
		$('#medicinePharmacyModal').modal('toggle');
		$("form#medicineForm").unbind('submit').submit(submitFunction(event, selectedValues));
	});

	if(id != undefined){
		editMedicine(id);
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
				window.location.replace("file:///C:/Users/Marijana/Desktop/jquery%20example/index.html");
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
		$('#pharmacySelect').empty();

		$.ajax({
			url: 'http://localhost:8080/pharmacy',
			type: 'GET',
			contentType: 'application/json',
			success: function (data) {
				str ="";
				for (i in data) {
					str += '<option value="'+ data[i].id +'">'+data[i].name+'</option>';
				}
				$("#pharmacySelect").append(str);
				$('#medicinePharmacyModal').modal('show');

			},
			error: function (message) {
				console.log(message.responseText);
			}
		});
	}
}

function editMedicine(id) {
	$('#pharmacySelect').empty();

    $.ajax({
        url: 'http://localhost:8080/medicine/' + id,
        type: 'get',
        contentType: 'application/json',
        success: function (data) {
			$('#name').val(data.name);
			$('#price').val(data.price);
			$('#availableAmount').val(data.availableAmount);

			str ="";
			for (i in data.pharmacy) {
				console.warn(i);
				str += '<option value="'+ data.pharmacy[i].id +'" selected>'+data.pharmacy[i].name+'</option>';
			}
			$("#pharmacySelect").append(str);

			$('#pharmacyBtn').unbind("click").click(function(){
				$('#medicinePharmacyModal').modal('show');
			});
			
            $('#submitBtnId').unbind("click").click(sendEditRequest(event, id)); 
        },
        error: function (message) {
            console.log(message.responseText);
        }
    });

}

function sendEditRequest(e, id) {
	return function(e) {
		e.preventDefault();
		var d = {};
		d.id = id;
		d.name = $('#name').val();
		d.price = $('#price').val();
		d.availableAmount = $('#availableAmount').val();
		
		$.ajax({
			url: 'http://localhost:8080/medicine/' + id,
			type: 'PUT',
			contentType: 'application/json',
			data: JSON.stringify(d),
			success: function (data) {
				window.location.replace("file:///C:/Users/Marijana/Desktop/jquery%20example/index.html");
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