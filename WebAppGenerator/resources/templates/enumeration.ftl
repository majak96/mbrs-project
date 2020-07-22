package ${enum.typePackage};

/**
 * [${.now}]
 * This file was generated based on the template "${.current_template_name}".
 * Changes to this file may cause incorrect behavior and will be lost if the code is regenerated.
 */
public enum ${enum.name?cap_first} {
	<#list enum.options as option>
	${option}<#if !option?is_last>,</#if>
	</#list>

}