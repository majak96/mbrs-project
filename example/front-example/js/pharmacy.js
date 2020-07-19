$(document).ready(function () {
	addButtonListeners();
	showPharmacies();
});

function addButtonListeners() {
	
	$('#sidebarCollapse').on('click', function () {
		$('#sidebar').toggleClass('active');
		$(this).toggleClass('active');
	});

	$('#addPharmacyBtn').unbind("click").click(function () {
		$("#modal-title").text("Add pharmacy");
        $('#editBtnId').hide();
        $('#addBtnId').show();
        $('#pharmacyForm')[0].reset();
        $('#pharmacyModal').modal('show');
	});
	
	$('#addBtnId').unbind("click").click(function () {
		var d = {};
		d.name = $('#pharmacyModal #name').val();
		d.address = $('#pharmacyModal #address').val();
		$.ajax({
			url: 'http://localhost:8080/pharmacy',
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

function showPharmacies() {
    $.ajax({
        url: 'http://localhost:8080/pharmacy',
        type: 'GET',
        contentType: 'application/json',
        success: function (data) {
            for (i in data) {
                number = parseInt(i) + 1;
				str = ' <tr id=row' + data[i].id + '> <td>' + number + '</td> <td>' + data[i].name + '</td>';
				str += '<td> ' + data[i].address + '</td> ';
				str += '<td> ' + + '</td> ';
                str += '<td> <a href="#" title="Edit" class="editPharmacy" name="' + data[i].id + '" id="editPharmacy' + data[i].id + '" ><i class="fas fa-edit" name="' + data[i].id + '"></i></a>';
                str += ' &nbsp; <a href="#" title="Delete" class="deletePharmacy" name="' + data[i].id + '" id="deletePharmacy' + data[i].id + '"><i class="fas fa-trash-alt" name="' + data[i].id + '"></i></a> </td> </tr>';
                $("#pharmacyTbodyId").append(str);
            }

            $('.editPharmacy').unbind("click").click(function () {
                editPharmacy(getSorceId(event));
            });

            $('.deletePharmacy').unbind("click").click(function () {
                deletePharmacy(getSorceId(event));
            });
        },
        error: function (message) {
            console.log(message.responseText);
        }
    });
}


function editPharmacy(id) {
    $.ajax({
        url: 'http://localhost:8080/pharmacy/' + id,
        type: 'get',
        contentType: 'application/json',
        success: function (data) {
			$("#modal-title").text("Edit pharmacy");
			$('#name').val(data.name);
			$('#address').val(data.address);
            $('#addBtnId').hide();
            $('#editBtnId').show();
            $('#pharmacyModal').modal('show');

            $('#editBtnId').unbind("click").click(function () {
				var d = {};
				d.name = $('#pharmacyModal #name').val();
				d.address = $('#pharmacyModal #address').val();
				$.ajax({
					url: 'http://localhost:8080/pharmacy/' + id,
					type: 'PUT',
					contentType: 'application/json',
					data: JSON.stringify(d),
					success: function (data) {
						$('#pharmacyModal').modal('hide');
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

function deletePharmacy(id) {
	if(confirm("Are you sure you want to delete this?")){
		$.ajax({
			url: 'http://localhost:8080/pharmacy/' + id,
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
