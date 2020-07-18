# [${.now}]
# This file was generated based on the template "${.current_template_name}".
# Changes to this file may cause incorrect behavior and will be lost if the code is regenerated.

spring:
  application:
    name: ${project_name}
  datasource:
    url: ${database_url}
    username: ${database_username}
    password: ${database_password}
    initialization-mode: always  
    sql-script-encoding: UTF-8
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: create-drop
    properties:
      hibernate:
        temp:
          use_jdbc_metadata_defaults: false
        dialect: org.hibernate.dialect.PostgreSQL95Dialect