/**
 * [${.now}]
 * This file was generated based on the template "${.current_template_name}".
 * Changes to this file may cause incorrect behavior and will be lost if the code is regenerated.
 */

<#list entity.linkedProperties as linkedProperty>
var ${linkedProperty.name}Values;
</#list>

$(document).ready(function () {
	addButtonListeners();
});

function addButtonListeners() {
	
	$('#sidebarCollapse').on('click', function () {
		$('#sidebar').toggleClass('active');
		$(this).toggleClass('active');
	});

	var id = getUrlParameter('id');

	<#-- LINKED PROPERTIES  -->
	<#list entity.linkedProperties as property>
	$('#${property.name}Btn').unbind("click").click(all${property.name?cap_first}(event));
	</#list>

	if(id != undefined){
		edit${class_name?cap_first}(id);
	}
	else{
		$("form#${class_name?lower_case}Form").unbind('submit').submit(submitFunction(event<#list entity.linkedProperties as param>, ${param.name}Values</#list>));
	}

}

function submitFunction(event<#list entity.linkedProperties as param>, ${param.name}Values</#list>) {
	return function(event) {
		event.preventDefault();
		var d = {};
		
		<#-- PERSISTENT PROPERTIES  -->
		<#list entity.persistentProperties as property>
		<#if property.id>
		<#else>
		<#if property.editable>
		d.${property.name} = $('#${property.name}').val();
		</#if>
		</#if>
		</#list>
	
		<#list entity.linkedProperties as linkedProperty>
		<#if (linkedProperty.upper == -1 && linkedProperty.oppositeEnd.upper == -1) || (linkedProperty.upper == -1 && linkedProperty.oppositeEnd.upper == 1)>
		if(${linkedProperty.name}Values != undefined){
			var ${linkedProperty.name}=[];
			for(i = 0; i < ${linkedProperty.name}Values.length; i++){
				item = {}
				<#list linkedProperty.type.persistentProperties as pp>
				<#if pp.id>
				item.${pp.name}=${linkedProperty.name}Values[i];
				<#else>
				</#if>
				</#list>
				${linkedProperty.name}.push(item);
			}
			d.${linkedProperty.name}=${linkedProperty.name};
		}
		<#-- ManyToOne -->
		<#else>
		if(${linkedProperty.name}Values != undefined){
			var ${linkedProperty.name}={};
			<#list linkedProperty.type.persistentProperties as pp>
			<#if pp.id>
			${linkedProperty.name}.${pp.name}=${linkedProperty.name}Values;
			<#else>
			</#if>
			</#list>
			d.${linkedProperty.name}=${linkedProperty.name};
		}
		</#if>
		</#list>

		$.ajax({
			url: 'http://localhost:8080/${class_name?lower_case}',
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(d),
			success: function (data) {
				window.location.replace("./${class_name?lower_case}.html");
			},
			error: function (message) {
				console.log(message.responseText);
			}
		});
	}
}

<#list entity.linkedProperties as property>
function all${property.name?cap_first}(event) {
	return function(event) {
		event.preventDefault();
		$('#${property.name}Tbody').empty();

		$.ajax({
			url: 'http://localhost:8080/${property.name}',
			type: 'GET',
			contentType: 'application/json',
			success: function (data) {
				str ="";
				for (i in data) {
					str += '<tr>';
					<#list property.type.persistentProperties as persistentProperty>
					<#if persistentProperty.showColumn>
					<#if persistentProperty.type.name=='Date'>
					var date = new Date(data[i].${persistentProperty.name});
					date = + ((date.getDate() > 9) ? date.getDate() : ('0' + date.getDate())) +'/'+ ((date.getMonth() > 8) ? (date.getMonth() + 1) : ('0' + (date.getMonth() + 1))) + '/' + date.getFullYear();
					str += '<td>' + date + '</td>';
					<#else>
					str += '<td>'+data[i].${persistentProperty.name}+'</td>';
					</#if>
					</#if>
					</#list>
					<#list property.type.persistentProperties as persistentProperty>
					<#if persistentProperty.id>
					<#if (property.upper == -1 && property.oppositeEnd.upper == -1) || (property.upper == -1 && property.oppositeEnd.upper == 1)>
					str += '<td scope="row" style="width:30px"><div><input type="checkbox" class="checkBoxClass" value="'+data[i].${persistentProperty.name}+'"></td></tr>';
					<#else>
					str += '<td><input type="radio" id="'+data[i].${persistentProperty.name}+'" name="${property.name}" value="'+data[i].${persistentProperty.name}+'"></td></tr>'
					</#if>
					<#else>
					</#if>
					</#list>
					str+='</tr>';
				}
				
				$("#${property.name}Tbody").append(str);
				$('#${class_name?lower_case}${property.name?cap_first}Modal').modal('show');

				$('#${property.name}PositiveBtnId').unbind("click").click(choose${property.name?cap_first}(event));

			},
			error: function (message) {
				console.log(message.responseText);
			}
		});
	}
}
</#list>

<#list entity.linkedProperties as property>
function choose${property.name?cap_first}(event){
	return function(event) {
		event.preventDefault();
		<#if (property.upper == -1 && property.oppositeEnd.upper == -1) || (property.upper == -1 && property.oppositeEnd.upper == 1)>
		${property.name}Values = $("input:checkbox:checked", "#${property.name}Tbody").map(function() {
			return $(this).val();
		}).get();
		<#else>
		let ${property.name}Values=$('input[name="${property.name}"]:checked').val();
		</#if>
		$('#${class_name?lower_case}${property.name?cap_first}Modal').modal('toggle');
		$("form#${class_name?lower_case}Form").unbind('submit').submit(submitFunction(event<#list entity.linkedProperties as param>, ${param.name}Values</#list>));
		return ${property.name}Values;
	}
}
</#list>

function edit${class_name?cap_first}(id) {

	<#list entity.linkedProperties as property>
	$('#${property.name}Tbody').empty();
	</#list>
	
    $.ajax({
        url: 'http://localhost:8080/${class_name?lower_case}/' + id,
        type: 'get',
        contentType: 'application/json',
        success: function (${class_name?lower_case}Data) {
        
        <#list entity.linkedProperties as property>
			$.ajax({
				url: 'http://localhost:8080/${property.name}',
				type: 'GET',
				contentType: 'application/json',
				success: function (${property.name}Data) {
				
				<#list entity.persistentProperties as persistentProperty>
				<#if persistentProperty.id>
				<#else>
				<#if persistentProperty.editable>
				<#if persistentProperty.type.name=='Date'>
				var date = new Date(${class_name?lower_case}Data.${persistentProperty.name});
				var day = ("0" + date.getDate()).slice(-2);
				var month = ("0" + (date.getMonth() + 1)).slice(-2);
				var selectedDate = date.getFullYear()+"-"+(month)+"-"+(day);
				$('#${persistentProperty.name}').val(selectedDate);
				<#else>
				$('#${persistentProperty.name}').val(${class_name?lower_case}Data.${persistentProperty.name});
				</#if>
				</#if>
				</#if>
				</#list>
				
				str ="";
				for (i in ${property.name}Data) {
					str += '<tr>'
					<#list property.type.persistentProperties as linksPersistentProperty>
					<#if linksPersistentProperty.showColumn>
					<#if linksPersistentProperty.type.name=='Date'>
					var date = new Date(${property.name}Data[i].${linksPersistentProperty.name});
					date = + ((date.getDate() > 9) ? date.getDate() : ('0' + date.getDate())) +'/'+ ((date.getMonth() > 8) ? (date.getMonth() + 1) : ('0' + (date.getMonth() + 1))) + '/' + date.getFullYear();
					str += '<td>' + date + '</td>';
					<#else>
					str += '<td>'+${property.name}Data[i].${linksPersistentProperty.name}+'</td>';
					</#if>
					</#if>
					</#list>
					<#list property.type.persistentProperties as linksPersistentProperty>
					<#if linksPersistentProperty.id>
					<#if (property.upper == -1 && property.oppositeEnd.upper == -1) || (property.upper == -1 && property.oppositeEnd.upper == 1)>
					str += '<td scope="row"><div><input type="checkbox" class="checkBoxClass" value="'+${property.name}Data[i].${linksPersistentProperty.name}+'"';
					if(containsObject(${property.name}Data[i], ${class_name?lower_case}Data.${property.name})){
					<#else>
					str += '<td><input type="radio" id="'+${property.name}Data[i].${linksPersistentProperty.name}+'" name="${property.name}" value="'+${property.name}Data[i].${linksPersistentProperty.name}+'"';
					if(${class_name?lower_case}Data.${property.name} != null && ${class_name?lower_case}Data.${property.name}.${linksPersistentProperty.name} == ${property.name}Data[i].${linksPersistentProperty.name}){
					</#if>
						str+='checked ></td>';
					}
					else{
						str+='></td>';
					}
					str += '</tr>';
					<#else>
					</#if>
					</#list>
				}
				$("#${property.name}Tbody").append(str);	
				
				<#list property.type.persistentProperties as linksPersistentProperty>
				<#if linksPersistentProperty.id>
				<#if (property.upper == -1 && property.oppositeEnd.upper == -1) || (property.upper == -1 && property.oppositeEnd.upper == 1)>
				${property.name}Values=[];	
				for(i=0;i<${class_name?lower_case}Data.${property.name}.length;i++){
					${property.name}Values.push(${class_name?lower_case}Data.${property.name}[i].${linksPersistentProperty.name});
				}
				<#else>
				if(${class_name?lower_case}Data.${property.name}!=null){
					${property.name}Values = ${class_name?lower_case}Data.${property.name}.${linksPersistentProperty.name};
				}
				</#if>
				</#if>
				</#list>		

				$('#submitBtnId').unbind("click").click(sendEditRequest(event, id<#list entity.linkedProperties as param>, ${param.name}Values</#list>)); 

				$('#${property.name}Btn').unbind("click").click(function(){
					$('#${class_name?lower_case}${property.name?cap_first}Modal').modal('show');

					$('#${property.name}PositiveBtnId').unbind("click").click(function () {
						event.preventDefault();
						
						<#if (property.upper == -1 && property.oppositeEnd.upper == -1) || (property.upper == -1 && property.oppositeEnd.upper == 1)>
						${property.name}Values = $("input:checkbox:checked", "#${property.name}Tbody").map(function() {
							return $(this).val();
						}).get();
						<#else>
						${property.name}Values=$('input[name="${property.name}"]:checked').val();
						</#if>
						$('#${class_name?lower_case}${property.name?cap_first}Modal').modal('toggle');
						$("form#${class_name?lower_case}Form").unbind('submit').submit(submitFunction(event<#list entity.linkedProperties as param>, ${param.name}Values</#list>));
						$('#submitBtnId').unbind("click").click(sendEditRequest(event, id<#list entity.linkedProperties as param>, ${param.name}Values</#list>)); 
					});
				});	
				
			},
			error: function (message) {
				console.log(message.responseText);
			}
			});
				
		</#list>
        },
        error: function (message) {
            console.log(message.responseText);
        }
    });
}

function sendEditRequest(event, id<#list entity.linkedProperties as linkedProperty>, ${linkedProperty.name}Values</#list>) {
	return function(event) {
		event.preventDefault();
		var d = {};
		<#list entity.persistentProperties as persistentProperty>
		<#if persistentProperty.id>
		d.${persistentProperty.name} = id;
		<#else>
		<#if persistentProperty.editable>
		d.${persistentProperty.name} = $('#${persistentProperty.name}').val();
		</#if>
		</#if>
		</#list>
		
		<#list entity.linkedProperties as linkedProperty>
		<#if (linkedProperty.upper == -1 && linkedProperty.oppositeEnd.upper == -1) || (linkedProperty.upper == -1 && linkedProperty.oppositeEnd.upper == 1)>
		if(${linkedProperty.name}Values!=undefined){
			var ${linkedProperty.name}=[];
			for(i = 0; i < ${linkedProperty.name}Values.length; i++){
				item = {}
				<#list linkedProperty.type.persistentProperties as linkerPersistentProperty>
				<#if linkerPersistentProperty.id>
				item.${linkerPersistentProperty.name}=${linkedProperty.name}Values[i];
				<#else>
				</#if>
				</#list>
				${linkedProperty.name}.push(item);
			}
			d.${linkedProperty.name}=${linkedProperty.name};
		}
		<#elseif linkedProperty.upper == 1 && linkedProperty.oppositeEnd.upper == -1>
		if(${linkedProperty.name}Values!=undefined){
			var ${linkedProperty.name}={};
			<#list linkedProperty.type.persistentProperties as linkerPersistentProperty>
			<#if linkerPersistentProperty.id>
			${linkedProperty.name}.${linkerPersistentProperty.name}=${linkedProperty.name}Values;
			d.${linkedProperty.name}=${linkedProperty.name};
			<#else>
			</#if>
			</#list>
		}
		</#if>
		</#list>

		$.ajax({
			url: 'http://localhost:8080/${class_name?lower_case}/' + id,
			type: 'PUT',
			contentType: 'application/json',
			data: JSON.stringify(d),
			success: function (data) {
				window.location.replace("./${class_name?lower_case}.html");
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