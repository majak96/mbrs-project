package ${project_package}.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ${package}.${class_name?cap_first};

/**
 * [${.now}]
 * This file was generated based on the template "${.current_template_name}".
 * Changes to this file may cause incorrect behavior and will be lost if the code is regenerated.
 */
@Repository
public interface ${class_name}BaseRepository extends JpaRepository<${class_name}, ${id_class?cap_first}> {

}
