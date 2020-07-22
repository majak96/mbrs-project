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
        <script src="js/${entity.name?lower_case}-form-base.js"></script>
        <script src="../src/js/${entity.name?lower_case}-form.js"></script>
        
    </head>

    <body>
        <div class="wrapper">

            <!-- SIDEBAR  -->
            <nav id="sidebar">
                <div class="sidebar-header">
                    <a href="./index.html"><h3>Example app</h3></a>
                </div>

                <ul class="list-unstyled components">
                    <#list entities as ent>
                    <li class="active">
                        <a href="./${ent?lower_case}.html">${ent}</a>
                    </li>
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
                    </div>

                    <!-- FORM -->
                    <div class="container" style="margin-top: 5%">
                        <form id="${entity.name?lower_case}Form">
                            <!-- FORM FIELDS FOR PERSISTENT PROPERTIES -->
                            <#list entity.persistentProperties as property>
                            <#if property.editable>
                                <div class="form-group">
                                    <label for="name">${property.label}:</label>
                                    <#if property.componentType == "TEXT_FIELD">
                                    <input type="text" class="form-control" name="${property.name}" id="${property.name}">
                                    <#elseif property.componentType == "TEXT_AREA">
                                    <textarea class="form-control" name="${property.name}" id="${property.name}"></textarea>
                                    <#elseif property.componentType == "NUMBER_FIELD" && (property.type.name == "Double" || property.type.name == "Float")>
                                    <input type="number" class="form-control" name="${property.name}" id="${property.name}" step="0.0001">
                                    <#elseif property.componentType == "NUMBER_FIELD" && !(property.type.name == "Double" || property.type.name == "Float")>
                                    <input type="number" class="form-control" name="${property.name}" id="${property.name}">
                                    <#elseif property.componentType == "DATE_FIELD">
                                    <input class="form-control" type="date" name="${property.name}" id="${property.name}">
                                    </#if>
                                </div>
                            </#if>
                            </#list>

                            <!-- FORM FIELDS FOR LINKED PROPERTIES -->
                            <#list entity.linkedProperties as property>
                                <div class="form-group">
                                    <label for="name">Choose ${property.label}:</label>
                                    <button type="button" class="btn" id="${property.name}Btn"><i class="fas fa-external-link-alt"></i></button>
                                </div>
                            </#list>
                            <button type="submit" id="submitBtnId" class="btn btn-info py-2 px-4 mt-3">Submit</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <#list entity.linkedProperties as property>
        <!-- MODAL DIALOGS FOR LINKED PROPERTIES -->
        <div class="modal fade" id="${entity.name?lower_case}${property.name?cap_first}Modal">
            <div class="modal-dialog modal-lg" style="min-width: auto; max-width: fit-content; min-width: 30%;">
                <div class="modal-content">

                    <div class="modal-header">
                        <h4 class="modal-title" id="modal-title">${property.label}</h4>
                        <button type="button" class="close" data-dismiss="modal">×</button>
                    </div>

                    <div class="modal-body">
                        <form id="${entity.name}${property.name?cap_first}Form">
                            <div class="form-group">
                                <table class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <#list property.type.persistentProperties as prop>
                                            <#if prop.showColumn>
                                            <th>${prop.label}</th>
                                            </#if>
                                            </#list>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody id="${property.name}Tbody">
                                       
                                    </tbody>
                                </table>
                            </div>
                            <button type="button" id="${property.name}PositiveBtnId" class="btn btn-info py-2 px-3" style="float: right;">Choose</button>
                        </form>
                        
                    </div>

                </div>
            </div>
        </div>
        </#list>
    </body>

</html>