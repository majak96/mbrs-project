package ${entity.typePackage};

<#-- IMPORTS -->
<#list imports as import>
import ${import.typePackage}.${import.name};
</#list>

/**
 * [${.now}]
 * This file was generated based on the template "${.current_template_name}".
 * Changes to this file may cause incorrect behavior and will be lost if the code is regenerated.
 */
@MappedSuperclass<#rt>
<#if (entity.tableName)??>${'\n'}@Table(name="${entity.tableName}")</#if>
${entity.accessModifier} class Base${entity.name?cap_first} <#if (entity.ancestor)??>extends ${entity.ancestor.name}</#if>{ 

	<#-- PERSISTENT PROPERTIES  -->
	<#list entity.persistentProperties as property>
	<#if property.id>
	@Id<#if (property.generatedValue)??>${'\n\t'}@GeneratedValue(strategy = GenerationType.${property.generatedValue})</#if>
	<#else>
	@Column<#if (property.columnName)?? || (property.precision)?? || (property.length)?? || (property.scale)?? || (property.unique)?? || property.lower == 0>(<#rt>
		   <#if (property.columnName)??>
		       <#lt>name = "${property.columnName}"<#rt>
		   </#if>
		   <#if (property.length)??>
		       <#lt><#if (property.columnName)??>, </#if>length = ${property.length}<#rt>
		   </#if>
		   <#if (property.precision)??>
		       <#lt><#if (property.columnName)?? || (property.length)??>, </#if>precision = ${property.precision}<#rt>
		   </#if>
		   <#if (property.scale)??>
		       <#lt><#if (property.columnName)?? || (property.length)?? || (property.precision)??>, </#if>scale = ${property.scale}<#rt>
		   </#if>
		   <#if (property.unique)??>
		       <#lt><#if (property.columnName)?? || (property.length)?? || (property.precision)?? || (property.scale)??>, </#if>unique = ${property.unique?c}<#rt>
		   </#if>
		   <#if property.lower == 0>
		       <#lt><#if (property.columnName)?? || (property.length)?? || (property.precision)?? || (property.scale)?? || (property.unique)??>, </#if>nullable = true<#rt>
		   </#if>
		   <#lt>)</#if>
	</#if>
	<#if property.jsonIgnore>
	@JsonIgnore
	</#if>
	${property.accessModifier} ${property.type.name} ${property.name};<#if !property?is_last>${'\n'}</#if>
	</#list>

	<#-- LINKED PROPERTIES  -->
	<#list entity.linkedProperties as property>
	<#if property.upper == -1 && property.oppositeEnd.upper == -1>@ManyToMany<#elseif property.upper == -1 && property.oppositeEnd.upper == 1>@OneToMany<#elseif property.upper == 1 && property.oppositeEnd.upper == -1>@ManyToOne<#else>@OneToOne</#if><#rt>
	<#lt><#if (property.fetch)?? || (property.cascade)?? || (property.mappedBy)?? || (property.optional)?? || (property.orphanRemoval)??>(<#rt>
		<#if (property.cascade)??>
			<#lt>cascade = CascadeType.${property.cascade}<#rt>
		</#if>
		<#if (property.fetch)??>
			<#lt><#if (property.cascade)??>, </#if>fetch = FetchType.${property.fetch}<#rt>
		</#if>
		<#if (property.mappedBy)??>
			<#lt><#if (property.cascade)?? || (property.fetch)??>, </#if>mappedBy = "${property.mappedBy}"<#rt>
		</#if>
		<#if (property.optional)??>
			<#lt><#if (property.cascade)?? || (property.fetch)?? || (property.mappedBy)??>, </#if>optional = ${property.optional?c}<#rt>
		</#if>
		<#if (property.orphanRemoval)??>
			<#lt><#if (property.cascade)?? || (property.fetch)?? || (property.mappedBy)?? || (property.optional)??>, </#if>orphanRemoval = ${property.orphanRemoval?c}<#rt>
		</#if>
		<#lt>)</#if>
	<#if property.jsonIgnore>
	@JsonIgnore
	</#if>
	${property.accessModifier} <#rt>
	<#if property.upper == -1>
		<#lt>Set<<#rt> 
	</#if>
	<#lt>${property.type.name?cap_first}<#rt>
	<#if property.upper == -1>
		<#lt>><#rt> 
	</#if>
	<#lt> ${property.name};<#if !property?is_last>${'\n'}</#if>
	</#list>

	<#--CONSTRUCTORS#-->
	public Base${entity.name}() {
		super();
	}

	<#--PERSISTENT PROPERTY GETTERS AND SETTERS-->
	<#list entity.persistentProperties as property>
	<#if property.type.name == "boolean">
	public ${property.type.name} is${property.name?cap_first}() {
	<#else>
	public ${property.type.name} get${property.name?cap_first}() {
	</#if>      
		return ${property.name};
	}
	  
	public void set${property.name?cap_first}(${property.type.name} ${property.name}) {
		this.${property.name} = ${property.name};
	}<#if !property?is_last>${'\n'}</#if>
	</#list>

	<#--LINKED PROPERTY GETTERS AND SETTERS-->
	<#list entity.linkedProperties as property>
	public <#rt>
	<#if property.upper == -1>
		<#lt>Set<<#rt> 
	</#if>
	<#lt>${property.type.name?cap_first}<#rt>
	<#if property.upper == -1>
		<#lt>><#rt> 
	</#if>
	<#lt> get${property.name?cap_first}() {
		return ${property.name};
	}

	public void set${property.name?cap_first}(<#rt>
	<#if property.upper == -1>
		<#lt>Set<<#rt> 
	</#if>
	<#lt>${property.type.name}<#rt>
	<#if property.upper == -1>
		<#lt>><#rt> 
	</#if>
	<#lt> ${property.name}) {
		this.${property.name} = ${property.name};
	}${'\n'}
	</#list>
}
