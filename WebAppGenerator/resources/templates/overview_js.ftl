/**
 * [${.now}]
 * This file was generated based on the template "${.current_template_name}".
 * Changes to this file may cause incorrect behavior and will be lost if the code is regenerated.
 */
 
$(document).ready(function () {
	addButtonListeners();
	show${class_name}s();
});

function addButtonListeners() {
	
	$('#sidebarCollapse').on('click', function () {
		$('#sidebar').toggleClass('active');
		$(this).toggleClass('active');
	});

}

function show${class_name}s() {
    $.ajax({
        url: 'http://localhost:${port}/${class_name?lower_case}',
        type: 'GET',
        contentType: 'application/json',
        success: function (data) {
			for (i in data) {
                number = parseInt(i) + 1;
				str = ' <tr id=row' + data[i].${id_class} + '> <td>' + number + '</td>';
				
				<#-- PERSISTENT PROPERTIES  -->
				<#list entity.persistentProperties as property>
				<#if property.showColumn>
				<#if property.type.name=='Date'>
				
				var date = new Date(data[i].${property.name});
				date = + ((date.getDate() > 9) ? date.getDate() : ('0' + date.getDate())) +'/'+ ((date.getMonth() > 8) ? (date.getMonth() + 1) : ('0' + (date.getMonth() + 1))) + '/' + date.getFullYear();
				str += '<td>' + date + '</td>';
				
				<#else>
				str += '<td>' + data[i].${property.name} + '</td>';
				</#if>
				</#if>
				</#list>
				
				<#-- LINKED PROPERTIES  -->
				<#list entity.linkedProperties as property>
				str += '<td> <a href="#" title="${property.name?cap_first}s" class="${property.name?lower_case}s" name="' + data[i].${id_class} + '" id="${property.name?lower_case}s' + data[i].${id_class} + '" ><i class="fas fa-external-link-alt" name="' + data[i].${id_class} + '"></i></a>';
				</#list>
                
                str += '<td> <a href="./${class_name?lower_case}Form.html?id=' + data[i].${id_class} + '" title="Edit" ><i class="fas fa-edit"></i></a>';
                str += ' &nbsp; <a href="#" title="Delete" class="delete${class_name?cap_first}" name="' + data[i].${id_class} + '" id="delete${class_name?cap_first}' + data[i].${id_class} + '"><i class="fas fa-trash-alt" name="' + data[i].${id_class} + '"></i></a> </td> </tr>';
                $("#${class_name?lower_case}TbodyId").append(str);
			}
			
			<#list entity.linkedProperties as property>
			<#-- Many to many -->
			$('.${property.name}s').unbind("click").click(function () {
            	show${property.name?cap_first}(getSorceId(event));
            });
			</#list>
			
            $('.delete${class_name?cap_first}').unbind("click").click(function () {
                delete${class_name?cap_first}(getSorceId(event));
            });
            
        },
        error: function (message) {
            console.log(message.responseText);
        }
    });
}

<#list entity.linkedProperties as property>
<#if (property.upper == -1 && property.oppositeEnd.upper == -1) || (property.upper == -1 && property.oppositeEnd.upper == 1)>
	
function show${property.name?cap_first}(id) {
	$('#${class_name?lower_case}${property.name?cap_first}Modal').modal('show');
	$('#${property.name}Tbody').empty();
	$.ajax({
		url: 'http://localhost:${port}/${class_name?lower_case}/' + id,
		type: 'GET',
		contentType: 'application/json',
		success: function (data) {

			str ="";
			for (i in data.${property.name}) {
				str += '<tr>';
				<#-- LINKED PROPERTIES  -->
				<#list property.type.persistentProperties as persistentProperty>
				<#if persistentProperty.showColumn>
				<#if persistentProperty.type.name=='Date'>
				var date = new Date(data.${property.name}[i].${persistentProperty.name});
				date = + ((date.getDate() > 9) ? date.getDate() : ('0' + date.getDate())) +'/'+ ((date.getMonth() > 8) ? (date.getMonth() + 1) : ('0' + (date.getMonth() + 1))) + '/' + date.getFullYear();
				str += '<td>' + date + '</td>';
				<#else>
				str += '<td>'+data.${property.name}[i].${persistentProperty.name}+'</td>';
				</#if>
				</#if>
				</#list>
				str+='</tr>';
			}
			$("#${property.name}Tbody").append(str);
			$('#${class_name?lower_case}${property.name?cap_first}Modal').modal('show');
		},
		error: function (message) {
			console.log(message.responseText);
		}
	});
}
<#else>
function show${property.name?cap_first}(id) {
	$.ajax({
		url: 'http://localhost:8080/${class_name?lower_case}/' + id,
		type: 'GET',
		contentType: 'application/json',
		success: function (data) {
			if (data.${property.name} != null) {
				<#list property.type.persistentProperties as prop>
                <#if prop.showColumn>
				<#if prop.type.name=='Date'>
				var date = new Date(data.${property.name}[i].${prop.name});
				date = + ((date.getDate() > 9) ? date.getDate() : ('0' + date.getDate())) +'/'+ ((date.getMonth() > 8) ? (date.getMonth() + 1) : ('0' + (date.getMonth() + 1))) + '/' + date.getFullYear();
				str += '<td>' + date + '</td>';
				<#else>
				$('#${prop.name}').text(data.${property.name}.${prop.name});
				</#if>
				</#if>
				</#list>
			}else{
				<#list property.type.persistentProperties as prop>
                <#if prop.showColumn>
				$('#${prop.name}').text("");
				</#if>
				</#list>
			}
			$('#${class_name?lower_case}${property.name?cap_first}Modal').modal('show');
		},
		error: function (message) {
			console.log(message.responseText);
		}
	});
}

</#if>
</#list>

function delete${class_name?cap_first}(id) {
	if(confirm("Are you sure you want to delete this?")){
		$.ajax({
			url: 'http://localhost:${port}/${class_name?lower_case}/' + id,
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