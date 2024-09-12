Liquibase Setup Guide
=====================

Overview
--------
Liquibase is a tool for managing database changes in a version-controlled manner. This guide provides instructions on how to set up Liquibase in your Spring Boot project, including file creation and commands to run.

File Creation
--------------
1. `liquibase.properties`

   Create a `liquibase.properties` file in the root directory of your project. This file contains configuration settings for Liquibase commands.

   .. code-block:: properties

      changeLogFile=db/changelog/db.changelog-master.xml
      url=jdbc:mysql://localhost:3307/finalexam
      username=spring
      password=secret
      driver=com.mysql.cj.jdbc.Driver

2. `db/changelog/db.changelog-master.xml`

   Create the master changelog file that Liquibase will use to track and apply changes to your database.

   .. code-block:: xml

      <?xml version="1.0" encoding="UTF-8"?>
      
      <databaseChangeLog
          xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
                              http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.8.xsd">
      
          <changeSet id="1" author="your_name">
              <createTable tableName="product">
                  <column name="id" type="char(36)">
                      <constraints primaryKey="true"/>
                  </column>
                  <column name="name" type="varchar(60)">
                      <constraints nullable="false"/>
                  </column>
                  <column name="description" type="varchar(100)">
                      <constraints nullable="false"/>
                  </column>
                  <column name="price" type="bigint">
                      <constraints nullable="false"/>
                  </column>
                  <column name="manufacturer" type="varchar(60)">
                      <constraints nullable="false"/>
                  </column>
                  <column name="created_at" type="datetime">
                      <constraints nullable="false"/>
                  </column>
                  <column name="updated_at" type="datetime">
                      <constraints nullable="false"/>
                  </column>
                  <column name="region" type="varchar(60)">
                      <constraints nullable="false"/>
                  </column>
                  <column name="category_id" type="integer">
                      <constraints nullable="false"/>
                  </column>
              </createTable>
          </changeSet>
      
          <changeSet id="2" author="your_name">
              <createTable tableName="category">
                  <column name="id" type="int">
                      <constraints primaryKey="true" nullable="false"/>
                  </column>
                  <column name="name" type="varchar(60)">
                      <constraints nullable="false"/>
                  </column>
              </createTable>
          </changeSet>
      
          <changeSet id="3" author="your_name">
              <addForeignKeyConstraint
                  constraintName="fk_product_category"
                  baseTableName="product"
                  baseColumnNames="category_id"
                  referencedTableName="category"
                  referencedColumnNames="id"/>
          </changeSet>
      
      </databaseChangeLog>

Configuration in `application.properties`
-----------------------------------------
Ensure that your `application.properties` file includes the following settings to enable Liquibase with Spring Boot:

.. code-block:: properties

   spring.application.name=final-exam
   spring.datasource.url=jdbc:mysql://localhost:3307/finalexam
   spring.datasource.username=spring
   spring.datasource.password=secret
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml
   spring.liquibase.enabled=true
   spring.liquibase.default-schema=public

Maven Plugin Configuration
--------------------------
To use Liquibase with Maven, you need to add the Liquibase Maven plugin to your `pom.xml` file. Add the following configuration under the `<build>` section:

.. code-block:: xml

   <build>
       <plugins>
           <plugin>
               <groupId>org.liquibase</groupId>
               <artifactId>liquibase-maven-plugin</artifactId>
               <version>4.29.2</version>
               <configuration>
                   <propertyFile>liquibase.properties</propertyFile>
               </configuration>
           </plugin>
       </plugins>
   </build>

Running Liquibase Commands
--------------------------
1. Using Maven

   - Check the Status

     .. code-block:: bash

        mvn liquibase:status
    
    - Generate SQL for Updates

     The `mvn liquibase:updateSQL` command generates the SQL statements that Liquibase will execute to update the database. This allows you to review the SQL commands before applying them to the database.

     .. code-block:: bash

        mvn liquibase:updateSQL

   - Update the Database

     .. code-block:: bash

        mvn liquibase:update

   - Tag the Database

     .. code-block:: bash

        mvn liquibase:tag -Dliquibase.tag=my_tag

   - Rollback Changes

     .. code-block:: bash

        mvn liquibase:rollback -Dliquibase.rollbackTag=my_tag

2. Using JAR File

   - Ensure you have Liquibase installed or included in your project. You can use a JAR file to execute Liquibase commands.

   - Check the Status

     .. code-block:: bash

        java -jar liquibase.jar --changeLogFile=db/changelog/db.changelog-master.xml status

   - Update the Database

     .. code-block:: bash

        java -jar liquibase.jar --changeLogFile=db/changelog/db.changelog-master.xml update

   - Tag the Database

     .. code-block:: bash

        java -jar liquibase.jar --changeLogFile=db/changelog/db.changelog-master.xml tag my_tag

   - Rollback Changes

     .. code-block:: bash

        java -jar liquibase.jar --changeLogFile=db/changelog/db.changelog-master.xml rollback my_tag

Summary
-------
- Create `liquibase.properties`: Configure command-line settings.
- Define Changes in `db/changelog/db.changelog-master.xml`: Specify database changes.
- Configure Liquibase in `application.properties`: Enable Liquibase in Spring Boot.
- Add Liquibase Maven Plugin to `pom.xml`: Configure Maven plugin for Liquibase.
- Run Maven Commands: Use Maven commands to manage database schema changes.
- Run JAR Commands: Use Liquibase JAR file to manage database changes.
