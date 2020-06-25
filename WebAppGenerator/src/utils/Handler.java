package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import model.CascadeType;
import model.FMAssociation;
import model.FMEntity;
import model.FMLinkedProperty;
import model.FMModel;
import model.FMPersistentProperty;
import model.FMProperty;
import model.FMType;
import model.FetchType;
import model.GeneratedValue;
import model.MethodPropertyAccessModifier;

public class Handler extends DefaultHandler {

	private Map<String, FMEntity> entities = new HashMap<>();
	private Map<String, FMPersistentProperty> persistentProperties = new HashMap<>();
	private Map<String, FMAssociation> associations = new HashMap<>();
	private Map<String, FMLinkedProperty> linkedProperties = new HashMap<>();
	private Map<String, FMType> defaultTypes = new HashMap<>();
	private Map<String, String> propertyTypesMap = new HashMap<>();

	private String currentClass;
	private String currentProperty;
	private String currentAssociation;

	@Override
	public void startDocument() throws SAXException {
		System.out.println("Start parsing...");

		// creating default types
		defaultTypes.put("String", new FMType("String", ""));
		defaultTypes.put("Integer", new FMType("Integer", ""));
		defaultTypes.put("double", new FMType("double", ""));
		defaultTypes.put("long", new FMType("long", ""));
		defaultTypes.put("String", new FMType("float", ""));
		defaultTypes.put("date", new FMType("Date", "java.util"));
		defaultTypes.put("Set", new FMType("Set", "java.util"));
		defaultTypes.put("Entity", new FMType("Entity", "javax.persistence"));
		defaultTypes.put("Column", new FMType("Column", "javax.persistence"));
		defaultTypes.put("GeneratedValue", new FMType("GeneratedValue", "javax.persistence"));
		defaultTypes.put("GeneratedType", new FMType("GeneratedType", "javax.persistence"));
		defaultTypes.put("Id", new FMType("Id", "javax.persistence"));
		defaultTypes.put("ManyToMany", new FMType("ManyToMany", "javax.persistence"));
		defaultTypes.put("OneToMany", new FMType("OneToMany", "javax.persistence"));
		defaultTypes.put("ManyToOne", new FMType("ManyToOne", "javax.persistence"));
		defaultTypes.put("OneToOne", new FMType("OneToOne", "javax.persistence"));
		defaultTypes.put("Table", new FMType("Table", "javax.persistence"));

	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		switch (qName) {
		
		case "packagedElement":
			// save entity
			if (attributes.getValue("xmi:type") != null && attributes.getValue("xmi:type").equals("uml:Class")) {
				// TODO: decide what to do with class visibility
				currentClass = attributes.getValue("xmi:id");
				
				FMEntity newEntity = new FMEntity(attributes.getValue("name"), ProjectInfo.getInstance().getProjectPackage());
				entities.put(attributes.getValue("xmi:id"), newEntity);
			}
			// save association
			else if (attributes.getValue("xmi:type") != null && attributes.getValue("xmi:type").equals("uml:Association")) {
				currentAssociation = attributes.getValue("xmi:id");

				FMAssociation newAssociation = new FMAssociation(attributes.getValue("name"));
				associations.put(attributes.getValue("xmi:id"), newAssociation);
			}
			
			break;

		case "generalization":
			// save entity ancestor
			String generalizationKey = attributes.getValue("general");
			
			if (entities.containsKey(generalizationKey)) {
				FMEntity currentEntity = entities.get(currentClass);
				FMEntity ancestorEntity = entities.get(generalizationKey);
				
				currentEntity.setAncestor(ancestorEntity);
			}

			break;

		case "ownedAttribute":
			if (attributes.getValue("xmi:type") != null && attributes.getValue("xmi:type").equals("uml:Property")) {
				currentProperty = attributes.getValue("xmi:id");
				
				FMEntity entity = this.entities.get(currentClass);
				
				MethodPropertyAccessModifier modifier = MethodPropertyAccessModifier.valueOf(attributes.getValue("visibility").toUpperCase());
				
				// save persistent property to entity
				if (attributes.getValue("association") == null) {
					FMPersistentProperty newProperty = new FMPersistentProperty(attributes.getValue("name"), modifier);
					
					persistentProperties.put(currentProperty, newProperty);
					entity.addProperty(newProperty);
				} 
				// save navigable linked property to entity
				else if (attributes.getValue("association") != null) {
					FMLinkedProperty property = new FMLinkedProperty();
	
					if (attributes.getValue("name") != null) {
						property.setName(attributes.getValue("name"));
					}
	
					property.setAccessModifier(modifier);
					
					this.propertyTypesMap.put(currentProperty, attributes.getValue("type"));
					
					linkedProperties.put(currentProperty, property);
					entity.addProperty(property);
				}
			}
			
			break;
			
		case "ownedEnd":
			// save not navigable linked properties to entity
			if (attributes.getValue("xmi:type") != null && attributes.getValue("xmi:type").equals("uml:Property")) {
				
				FMAssociation association = associations.get(attributes.getValue("association"));

				// check if both ends are not navigable - no need to save the property
				if(association.getFirstMemberEnd() == null && association.getSecondMemberEnd() == null) {
					currentProperty = null;
				} else {
					currentProperty = attributes.getValue("xmi:id");
						
					MethodPropertyAccessModifier ownedEndModifier = MethodPropertyAccessModifier.valueOf(attributes.getValue("visibility").toUpperCase());
							
					FMLinkedProperty property = new FMLinkedProperty();	
					
					propertyTypesMap.put(currentProperty, attributes.getValue("type"));
					
					if (attributes.getValue("name") != null) {
						property.setName(attributes.getValue("name"));
					}
						
					property.setAccessModifier(ownedEndModifier);
					property.setNavigable(false);
					property.setAssociation(association);
					
					linkedProperties.put(currentProperty, property);
					
					// not navigable linked property will always be second
					association.setSecondMemberEnd(property);
				}

			}
			
			break;

		case "referenceExtension":
			// save persistent property type
			if(currentProperty != null) {
				String referentPath = attributes.getValue("referentPath");
				String[] parts = referentPath.split("::");
				String typeName = parts[3];
	
				// set type of the persistent property
				if (defaultTypes.containsKey(typeName)) {
					FMType newType = defaultTypes.get(typeName);
					persistentProperties.get(currentProperty).setType(newType);
	
					// add date to imported packages
					if (typeName.equals("date")) {
						FMEntity currentEntity = entities.get(currentClass);
						currentEntity.addImportedPackage(newType);
					}
				}
			}
			
			break;

		case "lowerValue":
			// save property lower value
			if(currentProperty != null) {
				Integer valueLower = (attributes.getValue("value") == null) ? 0 : Integer.parseInt(attributes.getValue("value"));
				
				if (this.linkedProperties.containsKey(this.currentProperty)) {
					this.linkedProperties.get(currentProperty).setLower(valueLower);
				} else if (this.persistentProperties.containsKey(this.currentProperty)) {
					this.persistentProperties.get(currentProperty).setLower(valueLower);
				}
			}

			break;

		case "upperValue":
			// save property upper value
			if(currentProperty != null && attributes.getValue("value") != null) {
				Integer valueUpper = attributes.getValue("value").equals("*") ? -1 : Integer.parseInt(attributes.getValue("value"));

				if (this.linkedProperties.containsKey(this.currentProperty)) {
					this.linkedProperties.get(currentProperty).setUpper(valueUpper);
				} else if (this.persistentProperties.containsKey(this.currentProperty)) {
					this.persistentProperties.get(currentProperty).setUpper(valueUpper);
				}
			}
			
			break;

		case "memberEnd":
			// set current association member end
			if (attributes.getValue("xmi:idref") != null) {
				FMLinkedProperty memberEndProperty = linkedProperties.get(attributes.getValue("xmi:idref"));

				if(memberEndProperty != null) {
					FMAssociation association = associations.get(currentAssociation);

					if (association.getFirstMemberEnd() == null) {
						association.setFirstMemberEnd(memberEndProperty);
					} else {
						association.setSecondMemberEnd(memberEndProperty);
					}
					
					memberEndProperty.setAssociation(association);
				}			
			}

			break;
			
		case "BackendProfile:Entity":
			// set entity tag values
			String baseClass = attributes.getValue("base_Class");
			
			if (entities.containsKey(baseClass)) {
				FMEntity baseEntity = entities.get(baseClass);

				// check if table name exists
				if (attributes.getValue("tableName") != null) {
					baseEntity.setTableName(attributes.getValue("tableName"));

					// add table to imported packages
					baseEntity.addImportedPackage(defaultTypes.get("Table"));
				}

				// add entity and id to imported packages
				baseEntity.addImportedPackage(defaultTypes.get("Entity"));
				baseEntity.addImportedPackage(defaultTypes.get("Id"));
			}
			
			break;

		case "BackendProfile:PersistentProperty":
			// set persistent property tag values
			String baseProperty = attributes.getValue("base_Property");

			if (persistentProperties.containsKey(baseProperty)) {	
				FMEntity baseEntity = entities.get(currentClass);
				FMPersistentProperty persistentProperty = (FMPersistentProperty) persistentProperties.get(baseProperty);
				
				// add column to imported packages
				baseEntity.addImportedPackage(defaultTypes.get("Column"));
				
				persistentProperty.setColumnName(attributes.getValue("columnName"));
				
				if (attributes.getValue("id") != null) {
					persistentProperty.setId(attributes.getValue("id").equals("true"));
				}
				
				if (attributes.getValue("generatedValue") != null) {
					persistentProperty.setGeneratedValue(GeneratedValue.valueOf(attributes.getValue("generatedValue")));

					// add generated value and generated type to imported packages
					baseEntity.addImportedPackage(defaultTypes.get("GeneratedValue"));
					baseEntity.addImportedPackage(defaultTypes.get("GeneratedType"));
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
			// set linked property tag values
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
		// set linked property name and type
		for (Map.Entry<String, String> entry : this.propertyTypesMap.entrySet()) {
			if (this.entities.containsKey(entry.getValue()) && this.linkedProperties.containsKey(entry.getKey())) {
				FMLinkedProperty linkedProperty = this.linkedProperties.get(entry.getKey());
				FMEntity entity = this.entities.get(entry.getValue());
				
				linkedProperty.setType(entity);

				// if linked property doesn't have a name - set to type name
				if (linkedProperty.getName() == null)
					linkedProperty.setName(entity.getName().toLowerCase());
			}
		}
		
		// add association annotations to imported packages 
		for (Map.Entry<String, FMAssociation> entry : associations.entrySet()) {
			FMAssociation association = entry.getValue();
			
			if(association.getFirstMemberEnd().getNavigable()) {
				FMEntity secondMemberEntity = (FMEntity) association.getSecondMemberEnd().getType();
				Integer firstMemberUpper = association.getFirstMemberEnd().getUpper();
				Integer secondMemberUpper = association.getSecondMemberEnd().getUpper();
				
				if(firstMemberUpper == -1 && secondMemberUpper == -1) {
					secondMemberEntity.addImportedPackage(defaultTypes.get("ManyToMany"));
					secondMemberEntity.addImportedPackage(defaultTypes.get("Set"));
				}
				else if (firstMemberUpper == 1 && secondMemberUpper == -1) {
					secondMemberEntity.addImportedPackage(defaultTypes.get("ManyToOne"));
				}
				else if (firstMemberUpper == -1 && secondMemberUpper == 1) {
					secondMemberEntity.addImportedPackage(defaultTypes.get("OneToMany"));
					secondMemberEntity.addImportedPackage(defaultTypes.get("Set"));
				}
				else {
					secondMemberEntity.addImportedPackage(defaultTypes.get("OneToOne"));
				}
			}

			if(association.getSecondMemberEnd().getNavigable()) {
				FMEntity firstMemberEntity = (FMEntity) association.getFirstMemberEnd().getType();
				Integer firstMemberUpper = association.getFirstMemberEnd().getUpper();
				Integer secondMemberUpper = association.getSecondMemberEnd().getUpper();
				
				if(firstMemberUpper == -1 && secondMemberUpper == -1) {
					firstMemberEntity.addImportedPackage(defaultTypes.get("ManyToMany"));
					firstMemberEntity.addImportedPackage(defaultTypes.get("Set"));
				}
				else if (firstMemberUpper == -1 && secondMemberUpper == 1) {
					firstMemberEntity.addImportedPackage(defaultTypes.get("ManyToOne"));
				}
				else if (firstMemberUpper == 1 && secondMemberUpper == -1) {
					firstMemberEntity.addImportedPackage(defaultTypes.get("OneToMany"));
					firstMemberEntity.addImportedPackage(defaultTypes.get("Set"));
				}
				else {
					firstMemberEntity.addImportedPackage(defaultTypes.get("OneToOne"));
				}
			}
		}
		
		FMModel model = FMModel.getInstance();
		
		List<FMEntity> entitiesList = new ArrayList<FMEntity>(entities.values());
		List<FMAssociation> associationsList = new ArrayList<FMAssociation>(associations.values());

		model.setEntities(entitiesList);
		model.setAssociations(associationsList);

		System.out.println("******************************************");

		for (Map.Entry<String, FMEntity> entry : entities.entrySet()) {
			System.out.println("name: " + entry.getValue().getName());
			System.out.println("tableName: " + entry.getValue().getTableName());
			
			System.out.println("PROPERTIES: ");
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
			
			System.out.println("IMPORTED PACKAGES: ");
			for(FMType packagee : entry.getValue().getImportedPackages()) {
				System.out.println(packagee.getName() + " " + packagee.getTypePackage());
			}
			
			System.out.println("******************************************");
		}

	}

}
