package ${project_package}.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/${class_name?lower_case}", produces = MediaType.APPLICATION_JSON_VALUE)
public class ${class_name?cap_first}Controller extends ${class_name?cap_first}BaseController {

}