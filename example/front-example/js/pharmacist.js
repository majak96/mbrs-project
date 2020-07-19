$(document).ready(function () {
	addButtonListeners();
	showPharmacists();
});

function addButtonListeners() {
	
	$('#sidebarCollapse').on('click', function () {
		$('#sidebar').toggleClass('active');
		$(this).toggleClass('active');
	});

	$('#addPharmacistBtn').unbind("click").click(function () {
		$("#modal-title").text("Add pharmacist");
        $('#editBtnId').hide();
        $('#addBtnId').show();
        $('#pharmacistForm')[0].reset();
        $('#pharmacistModal').modal('show');
	});
	
	$('#addBtnId').unbind("click").click(function () {
		var d = {};
		d.firstName = $('#pharmacistModal #firstName').val();
		d.lastName = $('#pharmacistModal #lastName').val();
		d.email = $('#pharmacistModal #email').val();
		d.address = $('#pharmacistModal #address').val();
		d.dateOfBirth = $('#pharmacistModal #dateOfBirth').val();
		d.phoneNumber = $('#pharmacistModal #phoneNumber').val();
		$.ajax({
			url: 'http://localhost:8080/pharmacist',
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(d),
			success: function (data) {
				console.log(data);
			},
			error: function (message) {
				console.log(message.responseText);
			}
		});
	});

}

function showPharmacists() {
    $.ajax({
        url: 'http://localhost:8080/pharmacist',
        type: 'GET',
        contentType: 'application/json',
        success: function (data) {
            for (i in data) {
				console.warn(data);
				var date = new Date(data[i].dateOfBirth);
				date = + ((date.getDate() > 9) ? date.getDate() : ('0' + date.getDate())) +'/'+ ((date.getMonth() > 8) ? (date.getMonth() + 1) : ('0' + (date.getMonth() + 1))) + '/' + date.getFullYear();

				number = parseInt(i) + 1;
				str = ' <tr id=row' + data[i].id + '> <td>' + number + '</td> <td>' + data[i].firstName + '</td>';
				str += '<td> ' + data[i].lastName + '</td> ';
				str += '<td> ' + data[i].email + '</td> ';
				str += '<td> ' + data[i].phoneNumber + '</td> ';
				str += '<td> ' + date + '</td> ';
				str += '<td> ' + data[i].address + '</td> ';
                str += '<td> <a href="#" title="Edit" class="editPharmacist" name="' + data[i].id + '" id="editPharmacist' + data[i].id + '" ><i class="fas fa-edit" name="' + data[i].id + '"></i></a>';
                str += ' &nbsp; <a href="#" title="Delete" class="deletePharmacist" name="' + data[i].id + '" id="deletePharmacist' + data[i].id + '"><i class="fas fa-trash-alt" name="' + data[i].id + '"></i></a> </td> </tr>';
                $("#pharmacistTbodyId").append(str);
            }

            $('.editPharmacist').unbind("click").click(function () {
                editPharmacist(getSorceId(event));
            });

            $('.deletePharmacist').unbind("click").click(function () {
                deletePharmacist(getSorceId(event));
            });
        },
        error: function (message) {
            console.log(message.responseText);
        }
    });
}


function editPharmacist(id) {
    $.ajax({
        url: 'http://localhost:8080/pharmacist/' + id,
        type: 'get',
        contentType: 'application/json',
        success: function (data) {
			var date = new Date(data.dateOfBirth);
			var day = ("0" + date.getDate()).slice(-2);
			var month = ("0" + (date.getMonth() + 1)).slice(-2);
			var selectedDate = date.getFullYear()+"-"+(month)+"-"+(day) ;
			$("#modal-title").text("Edit pharmacist");
			$('#firstName').val(data.firstName);
			$('#lastName').val(data.lastName);
			$('#email').val(data.email);
			$('#address').val(data.address);
			$('#dateOfBirth').val(selectedDate);
			$('#phoneNumber').val(data.phoneNumber);
			$('#availableAmount').val(data.availableAmount);
            $('#addBtnId').hide();
            $('#editBtnId').show();
            $('#pharmacistModal').modal('show');

            $('#editBtnId').unbind("click").click(function () {
				var d = {};
				d.firstName = $('#pharmacistModal #firstName').val();
				d.lastName = $('#pharmacistModal #lastName').val();
				d.email = $('#pharmacistModal #email').val();
				d.address = $('#pharmacistModal #address').val();
				d.dateOfBirth = $('#pharmacistModal #dateOfBirth').val();
				d.phoneNumber = $('#pharmacistModal #phoneNumber').val();
				
				$.ajax({
					url: 'http://localhost:8080/pharmacist/' + id,
					type: 'PUT',
					contentType: 'application/json',
					data: JSON.stringify(d),
					success: function (data) {
						$('#pharmacistModal').modal('hide');
					},
					error: function (message) {
						console.log(message.responseText);
					}
				});
            });
        },
        error: function (message) {
            console.log(message.responseText);
        }
    });

}

function deletePharmacist(id) {
	if(confirm("Are you sure you want to delete this?")){
		$.ajax({
			url: 'http://localhost:8080/pharmacist/' + id,
			type: 'DELETE',
			contentType: 'application/json',
			success: function (data) {
				$('#row' + id).remove();
			},
			error: function (message) {
				console.log(message.responseText);
			}
		});
	}
	else{
		return false;
	}
}

function getSorceId(event) {
	let sorce = event.target;
    if (sorce.tagName != "A") {
		sorceBtn = $(sorce).parent();
    }
    else {
        sorceBtn = sorce;
    }
    let id = (($(sorceBtn).attr('name')));
    return id;
}
