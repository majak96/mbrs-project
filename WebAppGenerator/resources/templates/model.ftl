package ${entity.typePackage};

import javax.persistence.Entity;

@Entity
${entity.accessModifier} class ${entity.name?cap_first} extends Base${entity.name?cap_first} { 

}