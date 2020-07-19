$(document).ready(function () {
	addButtonListeners();
	showMedicines();
});

function addButtonListeners() {
	
	$('#sidebarCollapse').on('click', function () {
		$('#sidebar').toggleClass('active');
		$(this).toggleClass('active');
	});

}

function showMedicines() {
    $.ajax({
        url: 'http://localhost:8080/medicine',
        type: 'GET',
        contentType: 'application/json',
        success: function (data) {
			for (i in data) {
                number = parseInt(i) + 1;
				str = ' <tr id=row' + data[i].id + '> <td>' + number + '</td> <td>' + data[i].name + '</td>';
				str += '<td> ' + data[i].price + '</td> ';
				str += '<td> ' + data[i].availableAmount + '</td> ';
                str += '<td> <a href="#" title="Pharmacies" class="pharmacies" name="' + data[i].id + '" id="pharmacies' + data[i].id + '" ><i class="fas fa-external-link-alt" name="' + data[i].id + '"></i></a>';
                str += '<td> <a href="./medicineForm.html?id=' + data[i].id + '" title="Edit" ><i class="fas fa-edit"></i></a>';
                str += ' &nbsp; <a href="#" title="Delete" class="deleteMedicine" name="' + data[i].id + '" id="deleteMedicine' + data[i].id + '"><i class="fas fa-trash-alt" name="' + data[i].id + '"></i></a> </td> </tr>';
                $("#medicineTbodyId").append(str);
			}
			
            $('.pharmacies').unbind("click").click(function () {
                showPharmacy(getSorceId(event));
            });

            $('.editMedicine').unbind("click").click(function () {
                editMedicine(getSorceId(event));
            });

            $('.deleteMedicine').unbind("click").click(function () {
                deleteMedicine(getSorceId(event));
            });
        },
        error: function (message) {
            console.log(message.responseText);
        }
    });
}

function showPharmacy(id) {
	$('#medicinePharmacyModal').modal('show');
	$('#pharmacyTbody').empty();
	$.ajax({
		url: 'http://localhost:8080/medicine/' + id,
		type: 'GET',
		contentType: 'application/json',
		success: function (data) {
			str ="";
			for (i in data.pharmacy) {
				str += '<tr><td>'+data.pharmacy[i].name+'</td>';
				str += '<td>'+data.pharmacy[i].address+'</td></tr>';
			}
			$("#pharmacyTbody").append(str);
			$('#medicinePharmacyModal').modal('show');
		},
		error: function (message) {
			console.log(message.responseText);
		}
	});
}

function deleteMedicine(id) {
	if(confirm("Are you sure you want to delete this?")){
		$.ajax({
			url: 'http://localhost:8080/medicine/' + id,
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
