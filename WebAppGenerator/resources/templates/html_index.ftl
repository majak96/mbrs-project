<!DOCTYPE html>
<!--[${.now}]
    This file was generated based on the template "${.current_template_name}".
    Changes to this file may cause incorrect behavior and will be lost if the code is regenerated. -->
<html>
	
	<head>
	    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
	
	    <title>${application_name}</title>
	
	    <script src="https://cdn.jsdelivr.net/npm/jquery@3.4.1/dist/jquery.min.js"></script>
	
	    <!-- Bootstrap -->
	    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
	    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
	
	    <!-- Validation -->
	    <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/jquery.validate.min.js"></script>
	    <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/additional-methods.js"></script>
	
	    <!-- Font Awesome -->
	    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
	    <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js"></script>
	    <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js"></script>
	
        <link rel="stylesheet" href="css/index_base.css">
	    <script src="js/index-base.js"></script>
	    <script src="../src/js/index.js"></script>
	
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
           
	        <!-- Page Content  -->
	        <div id="content">
	            <div class="container">
	                <div class="row">
	                    <div class="col-5">
	                        <button type="button" id="sidebarCollapse" class="navbar-btn">
	                            <span></span>
	                            <span></span>
	                            <span></span>
	                        </button>
	                    </div>
	                    <div class="col-4">
	                        <h3>Welcome</h3>
	                    </div>
	                   
	                </div>
	                	               
	            </div>
	        </div>
	    </div>
	
	</body>

</html>