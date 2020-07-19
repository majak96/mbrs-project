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

}

function showPharmacists() {
    $.ajax({
        url: 'http://localhost:8080/pharmacist',
        type: 'GET',
        contentType: 'application/json',
        success: function (data) {
            for (i in data) {
				var date = new Date(data[i].dateOfBirth);
				date = + ((date.getDate() > 9) ? date.getDate() : ('0' + date.getDate())) +'/'+ ((date.getMonth() > 8) ? (date.getMonth() + 1) : ('0' + (date.getMonth() + 1))) + '/' + date.getFullYear();

				number = parseInt(i) + 1;
				str = ' <tr id=row' + data[i].id + '> <td>' + number + '</td> <td>' + data[i].firstName + '</td>';
				str += '<td> ' + data[i].lastName + '</td> ';
				str += '<td> ' + data[i].email + '</td> ';
				str += '<td> ' + data[i].phoneNumber + '</td> ';
				str += '<td> ' + date + '</td> ';
				str += '<td> ' + data[i].address + '</td> ';
				str += '<td> <a href="./pharmacistForm.html?id=' + data[i].id + '" title="Edit" ><i class="fas fa-edit"></i></a>';
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
