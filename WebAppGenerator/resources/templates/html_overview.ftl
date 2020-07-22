<!DOCTYPE html>
<!--[${.now}]
    This file was generated based on the template "${.current_template_name}".
    Changes to this file may cause incorrect behavior and will be lost if the code is regenerated. -->
<html>

    <head>
        <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />

        <title>${entity.name}</title>

        <script src="https://cdn.jsdelivr.net/npm/jquery@3.4.1/dist/jquery.min.js"></script>

        <!-- BOOTSTRAP -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

        <!-- VALIDATION -->
        <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/jquery.validate.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/additional-methods.js"></script>

        <!-- FONT AWESOME -->
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js"></script>
        <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js"></script>

        <link rel="stylesheet" href="css/index_base.css">
        <script src="js/${entity.name?lower_case}-base.js"></script>
        <script src="../src/js/${entity.name?lower_case}.js"></script>
        
    </head>
    
     <body>
        <div class="wrapper">

            <!-- SIDEBAR  -->
            <nav id="sidebar">
                <div class="sidebar-header">
                    <a href="./index.html"><h3>${application_name}</h3></a>
                </div>

                <ul class="list-unstyled components">
                    <#list groups?keys as ent>
                    <#if ent != 'Other'>
	            	<li class="sidebar-dropdown">
	            		<a href="#${ent}Menu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle" >${ent}</a>
	            		<div class="sidebar-submenu">
			              <ul class="collapse list-unstyled" id="${ent}Menu">
			              <#list groups[ent] as group>
			                <li><a href="./${group?lower_case}.html">${group}</a></li>
			                </#list>
			              </ul>
			            </div>
	            	</li>	
	                <#else>
                	<#list groups['Other'] as single>
                	<li>
                        <a href="./${single?lower_case}.html">${single}</a>
                    </li>
                	</#list>
                    </#if>
                    </#list>
                </ul>
            </nav>
            
            <!-- PAGE CONTENT  -->
            <div id="content">
                <div class="container">
                    <!-- TITLE -->
                    <div class="row">
                        <div class="col-5">
                            <button type="button" id="sidebarCollapse" class="navbar-btn">
                                <span></span>
                                <span></span>
                                <span></span>
                            </button>
                        </div>
                        <div class="col-4">
                            <h3>${entity.label}</h3>
                        </div>
	                    <div class="col-3">
	                        <a id="add${entity.name?cap_first}Btn" style="margin-top: 0.2cm;" class="btn button" href="./${entity.name?lower_case}Form.html"><i style="color: #3c403d;"
	                            class="fas fa-plus-circle"></i><span> Add ${entity.name?lower_case}</span></a>
	                    </div>
	                </div>
                </div>

                <div class="container" style="margin-top: 5%">
                    <div class="table-wrapper">
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                	<th>No.</th>
                                	<#list entity.persistentProperties as property>
                                	<#if property.showColumn>
                                    <th>${property.label}</th>
                                    </#if>
                                    </#list>
                                    <#list entity.linkedProperties as prop>
                                    <th>${prop.name}</th>
                                    </#list>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody id="${entity.name?lower_case}TbodyId">

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <#list entity.linkedProperties as property>
    <#if (property.upper == -1 && property.oppositeEnd.upper == -1) || (property.upper == -1 && property.oppositeEnd.upper == 1)>
    <div class="modal fade" id="${entity.name?lower_case}${property.name?cap_first}Modal">
        <div class="modal-dialog modal-lg" style="min-width: auto; max-width: fit-content; min-width: 20%;">
            <div class="modal-content">

                <div class="modal-header">
                    <h4 class="modal-title" id="modal-title">${property.name?cap_first}</h4>
                    <button type="button" class="close" data-dismiss="modal">×</button>
                </div>
                <div class="modal-body">
                    <form id="${entity.name?lower_case}${property.name?cap_first}Form">
                        <div class="form-group">
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                    	<#list property.type.persistentProperties as prop>
                                        <#if prop.showColumn>
                                        <th>${prop.label}</th>
                                        </#if>
                                        </#list>
                                    </tr>
                                </thead>
                                <tbody id="${property.name?lower_case}Tbody">
                                   
                                </tbody>
                            </table>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
    <#else>
    <div class="modal fade" id="${entity.name?lower_case}${property.name?cap_first}Modal">
        <div class="modal-dialog modal-lg" style="min-width: auto; max-width: fit-content; min-width: 20%;">
            <div class="modal-content">

                <div class="modal-header">
                    <h4 class="modal-title" id="modal-title">${property.name?cap_first}</h4>
                    <button type="button" class="close" data-dismiss="modal">×</button>
                </div>
                <div class="modal-body">
                    <table>
                        <thead>
                            <#list property.type.persistentProperties as prop>
                            <#if prop.showColumn>
                            <tr>
                                <td><label for="name">${prop.label}:</label></td>
                                <td><label name="${prop.name}" id="${prop.name}" class="ml-2"></label></td>
                            </tr>
                            </#if>
                            </#list>
                        </thead>
                        <tbody id="${property.name?lower_case}Tbody">
                            
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    </#if>
    </#list>
    
	</body>

</html>