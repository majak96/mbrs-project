package ${project_package}.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ${package}.${class_name?cap_first};

@Repository
public interface ${class_name}Repository extends JpaRepository<${class_name}, ${id_class?cap_first}> {

}
