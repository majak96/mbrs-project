package ${project_package}.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Set;

<#list linkedProperties as property>
import ${property.type.typePackage}.${property.type.name?cap_first};
</#list>


import ${package}.${class_name?cap_first};

/**
 * [${.now}]
 * This file was generated based on the template "${.current_template_name}".
 * Changes to this file may cause incorrect behavior and will be lost if the code is regenerated.
 */
@Repository
public interface ${class_name}BaseRepository extends JpaRepository<${class_name}, ${id_class?cap_first}> {


	// for all association fields
	<#list linkedProperties as property>
	@Query("SELECT DISTINCT o from ${class_name} as e RIGHT OUTER JOIN e.${property.name} as o WHERE e is NULL")
	Set<${property.type.name?cap_first}> findAvailable${property.name?cap_first}();
	</#list>
}
