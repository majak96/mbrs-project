package ${project_package}.services;

import java.util.Optional;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<#list linkedProperties as property>
import ${property.type.typePackage}.${property.type.name?cap_first};
</#list>
import ${package}.${class_name};
import ${project_package}.repositories.${class_name}Repository;

/**
 * [${.now}]
 * This file was generated based on the template "${.current_template_name}".
 * Changes to this file may cause incorrect behavior and will be lost if the code is regenerated.
 */
@Service
public class ${class_name}BaseService {
	
	@Autowired
	protected ${class_name}Repository ${class_name?lower_case}Repository;
	
	public ${class_name} getOne(${id_class?cap_first} id) {
		Optional<${class_name}> ${class_name?lower_case} = this.${class_name?lower_case}Repository.findById(id);
		if(${class_name?lower_case}.isPresent())
			return ${class_name?lower_case}.get();
		return null;
	}
	
	public List<${class_name}> findAll() {
		return this.${class_name?lower_case}Repository.findAll();
	}
	
	public ${class_name} save(${class_name} ${class_name?lower_case}) {
		return this.${class_name?lower_case}Repository.save(${class_name?lower_case});
	}
	
	public boolean delete(${id_class?cap_first} id) {
		${class_name} ${class_name?lower_case} = this.getOne(id);
		if (${class_name?lower_case} != null) {
			this.${class_name?lower_case}Repository.deleteById(id);
			return true;
		}
		return false;
	}
	
	public ${class_name} update(${id_class?cap_first} id, ${class_name} ${class_name?lower_case}) {
		${class_name} old${class_name} = this.getOne(id);
		
		if(old${class_name} == null)
			return null;
		
		<#list properties as property>
   		old${class_name}.set${property?cap_first}(${class_name?lower_case}.get${property?cap_first}());
		</#list>

		return this.save(old${class_name});
	}
	
	<#list linkedProperties as property>
   	public Set<${property.type.name?cap_first}> getAvailable${property.name?cap_first}() {
		return this.${class_name?lower_case}Repository.findAvailable${property.name?cap_first}();
	}
	</#list>


}
