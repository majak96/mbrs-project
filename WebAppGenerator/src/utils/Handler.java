package utils;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import model.CascadeType;
import model.FMEntity;
import model.FMLinkedProperty;
import model.FMPersistentProperty;
import model.FMProperty;
import model.FMType;
import model.FetchType;
import model.GeneratedValue;
import model.MethodPropertyAccessModifier;

public class Handler extends DefaultHandler {

	private Map<String, FMEntity> entities = new HashMap<>();
	private Map<String, FMPersistentProperty> persistentProperties = new HashMap<>();
	private Map<String, FMLinkedProperty> linkedProperties = new HashMap<>();
	private Map<String, FMType> primitiveTypes = new HashMap<>();
	private Map<String, String> associationMap = new HashMap<>();

	private String currentClass;
	private String currentProperty;

	@Override
	public void startDocument() throws SAXException {
		System.out.println("Start parsing...");

		// Creating primitive types
		primitiveTypes.put("String", new FMType("String", ""));
		primitiveTypes.put("Integer", new FMType("Integer", ""));
		primitiveTypes.put("double", new FMType("double", ""));
		primitiveTypes.put("long", new FMType("long", ""));
		primitiveTypes.put("String", new FMType("float", ""));
		primitiveTypes.put("date", new FMType("Date", "java.util"));

	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		switch (qName) {
		case "packagedElement":
			if (attributes.getValue("xmi:type") != null && attributes.getValue("xmi:type").equals("uml:Class")) {
				// TODO: Decide what to do with class visibility
				currentClass = attributes.getValue("xmi:id");
				FMEntity newEntity = new FMEntity(attributes.getValue("name"), "");
				entities.put(attributes.getValue("xmi:id"), newEntity);
			}
			break;

		case "generalization":
			String generalizationKey = attributes.getValue("general");
			if (entities.containsKey(generalizationKey)) {
				FMEntity currentEntity = entities.get(currentClass);
				FMEntity ancestorEntity = entities.get(generalizationKey);
				currentEntity.setAncestor(ancestorEntity);
			}

			break;

		case "ownedAttribute":
			FMEntity entity = this.entities.get(currentClass);
			currentProperty = attributes.getValue("xmi:id");
			MethodPropertyAccessModifier modifier = MethodPropertyAccessModifier
					.valueOf(attributes.getValue("visibility").toUpperCase());
			if (attributes.getValue("association") == null && attributes.getValue("xmi:type") != null
					&& attributes.getValue("xmi:type").equals("uml:Property")) {
				FMPersistentProperty newProperty = new FMPersistentProperty(attributes.getValue("name"), modifier);
				entities.get(currentClass).addProperty(newProperty);
				persistentProperties.put(currentProperty, newProperty);
				entity.addProperty(newProperty);

			} else if (attributes.getValue("association") != null) {
				// TODO: association problem
				FMLinkedProperty property = new FMLinkedProperty();

				if (attributes.getValue("name") != null) {
					property.setName(attributes.getValue("name"));
				}

				this.associationMap.put(currentProperty, attributes.getValue("type"));
				property.setAccessModifier(modifier);
				linkedProperties.put(currentProperty, property);
				entity.addProperty(property);

			}
			break;

		case "referenceExtension":
			String referentPath = attributes.getValue("referentPath");
			String[] parts = referentPath.split("::");
			String typeName = parts[3];

			if (primitiveTypes.containsKey(typeName)) {
				FMType newType = primitiveTypes.get(typeName);
				persistentProperties.get(currentProperty).setType(newType);
			}
			break;

		case "lowerValue":
			Integer valueLower = (attributes.getValue("value") == null)? 0 : Integer.parseInt(attributes.getValue("value"));
			if (this.linkedProperties.containsKey(this.currentProperty)) {
					this.linkedProperties.get(currentProperty).setLower(valueLower);
				} else if (this.persistentProperties.containsKey(this.currentProperty)) {
					this.persistentProperties.get(currentProperty).setLower(valueLower);
				}
			
			break;

		case "upperValue":
			if (attributes.getValue("value") != null) {
				Integer value;

				if (attributes.getValue("value").equals("*")) {
					value = -1;
				} else {
					value = Integer.parseInt(attributes.getValue("value"));
				}

				if (this.linkedProperties.containsKey(this.currentProperty)) {
					this.linkedProperties.get(currentProperty).setUpper(value);
				} else if (this.persistentProperties.containsKey(this.currentProperty)) {
					this.persistentProperties.get(currentProperty).setUpper(value);
				}
			}
			break;

		case "BackendProfile:Entity":
			String baseClass = attributes.getValue("base_Class");
			if (entities.containsKey(baseClass)) {
				entity = entities.get(baseClass);
				if (attributes.getValue("tableName") != null) {
					entity.setTableName(attributes.getValue("tableName"));
				}
			}
			break;

		case "BackendProfile:PersistentProperty":
			String baseProperty = attributes.getValue("base_Property");
			if (persistentProperties.containsKey(baseProperty)) {
				FMPersistentProperty persistentProperty = (FMPersistentProperty) persistentProperties.get(baseProperty);
				persistentProperty.setColumnName(attributes.getValue("columnName"));
				// TODO: set default value in model
				if (attributes.getValue("id") == null) {
					persistentProperty.setId(false);
				} else {
					persistentProperty.setId(attributes.getValue("id").equals("true"));
				}
				// TODO:
				if (attributes.getValue("generatedValue") != null) {
					persistentProperty.setGeneratedValue(GeneratedValue.valueOf(attributes.getValue("generatedValue")));
				}
				if (attributes.getValue("length") != null) {
					persistentProperty.setLength(Integer.parseInt(attributes.getValue("length")));
				}
				if (attributes.getValue("precision") != null) {
					persistentProperty.setPrecision(Integer.parseInt(attributes.getValue("precision")));
				}
				if (attributes.getValue("unique") != null) {
					persistentProperty.setUnique(attributes.getValue("unique").equals("true"));
				}
			}
			break;

		case "BackendProfile:LinkedProperty":
			baseProperty = attributes.getValue("base_Property");
			if (this.linkedProperties.containsKey(baseProperty)) {
				FMLinkedProperty linkedProperty = (FMLinkedProperty) this.linkedProperties.get(baseProperty);

				if (attributes.getValue("cascade") != null) {
					linkedProperty.setCascade(CascadeType.valueOf(attributes.getValue("cascade")));
				}

				if (attributes.getValue("fetch") != null) {
					linkedProperty.setFetch(FetchType.valueOf(attributes.getValue("fetch")));
				}

				if (attributes.getValue("mappedBy") != null) {
					linkedProperty.setMappedBy(attributes.getValue("mappedBy"));
				}

				if (attributes.getValue("orphanRemoval") != null) {
					linkedProperty.setOrphanRemoval(Boolean.parseBoolean(attributes.getValue("orphanRemoval")));
				}

				if (attributes.getValue("optional") != null) {
					linkedProperty.setOptional(Boolean.parseBoolean(attributes.getValue("optional")));
				}

			}
			break;

		}
	}

	public void endDocument() throws SAXException {

		for (Map.Entry<String, String> entry : this.associationMap.entrySet()) {
			if (this.entities.containsKey(entry.getValue()) && this.linkedProperties.containsKey(entry.getKey())) {
				FMLinkedProperty linkedProperty = this.linkedProperties.get(entry.getKey());
				FMEntity entity = this.entities.get(entry.getValue());
				
				if (linkedProperty.getName() == null)
					linkedProperty.setName(entity.getName().toLowerCase());

				linkedProperty.setType(entity);
			}
		}

		System.out.println("******************************************");

		for (Map.Entry<String, FMEntity> entry : entities.entrySet()) {
			System.out.println("name: " + entry.getValue().getName());
			System.out.println("tableName: " + entry.getValue().getTableName());
			for (FMProperty fmp : entry.getValue().getProperties()) {
				System.out.println("Property name: " + fmp.getName());
				System.out.println("Property access modifier: " + fmp.getAccessModifier());
				System.out.println("Property type name: " + fmp.getType().getName());
				System.out.println("Property type package: " + fmp.getType().getTypePackage());
				System.out.println("Property upper: " + fmp.getUpper());
				System.out.println("Property lower: " + fmp.getLower());

				if (fmp instanceof FMPersistentProperty) {
					FMPersistentProperty persistentProperty = (FMPersistentProperty) fmp;
					System.out.println("Property generated value: " + persistentProperty.getGeneratedValue());
					System.out.println("Property id: " + persistentProperty.getId());
					System.out.println("Property column name: " + persistentProperty.getColumnName());
					System.out.println("Property len: " + persistentProperty.getLength());
					System.out.println("Property precision: " + persistentProperty.getPrecision());
					System.out.println("Property unique: " + persistentProperty.getUnique());
				} else if (fmp instanceof FMLinkedProperty) {
					FMLinkedProperty linkedProperty = (FMLinkedProperty) fmp;
					System.out.println("Property cascade: " + linkedProperty.getCascade());
					System.out.println("Property fetch: " + linkedProperty.getFetch());
					System.out.println("Property mappedBy: " + linkedProperty.getMappedBy());
					System.out.println("Property orphanRemoval: " + linkedProperty.getOrphanRemoval());
					System.out.println("Property optional: " + linkedProperty.getOptional());
				}
				System.out.println("******************************************");

			}
		}

	}

}
