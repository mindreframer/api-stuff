#!/usr/bin/env python

import os
import os.path

pom = """
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.bitmechanic</groupId>
  <artifactId>example</artifactId>
  <packaging>war</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>Example Barrister Maven Webapp</name>
  <url>http://maven.apache.org</url>

  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>com.bitmechanic</groupId>
      <artifactId>barrister</artifactId>
      <version>0.1.0-SNAPSHOT</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>servlet-api</artifactId>
        <version>2.5</version>
        <scope>provided</scope>
    </dependency>
  </dependencies>

  <build>
    <finalName>example</finalName>
    <plugins>
        <plugin>
            <inherited>true</inherited>
            <groupId>org.apache.maven.plugins</groupId>
            <version>2.3.2</version>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <source>1.5</source>
                <target>1.5</target>
                <optimize>true</optimize>
                <debug>true</debug>
            </configuration>
        </plugin>
        <plugin>
            <groupId>org.mortbay.jetty</groupId>
            <artifactId>maven-jetty-plugin</artifactId>
            <version>6.1.21</version>
        </plugin>
    </plugins>
  </build>

</project>
"""

webxml = """
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >
<web-app>
  <display-name>Barrister Example</display-name>

  <servlet>
      <servlet-name>example</servlet-name>
      <servlet-class>com.bitmechanic.barrister.BarristerServlet</servlet-class>
      <init-param>
          <param-name>idl</param-name>
          <param-value>classpath:/example.json</param-value>
      </init-param>
      <init-param>
          <param-name>handler.1</param-name>
          <param-value>example.Foo=example.FooImpl</param-value>
      </init-param>
  </servlet>

  <servlet-mapping>
      <servlet-name>example</servlet-name>
      <url-pattern>/</url-pattern>
  </servlet-mapping>

</web-app>
"""

codegen = """#!/bin/sh

set -e

mkdir -p src/main/resources
cp -f ../*.json src/main/resources/example.json
idl2java.sh -j src/main/resources/example.json -p example -o src/main/java
"""

client = """#!/bin/sh

set -e

jackson_m2=$HOME/.m2/repository/org/codehaus/jackson
CLASSPATH=$jackson_m2/jackson-mapper-asl/1.9.4/jackson-mapper-asl-1.9.4.jar:$jackson_m2/jackson-core-asl/1.9.4/jackson-core-asl-1.9.4.jar:$BARRISTER_JAVA/target/classes:target/classes
java -cp $CLASSPATH example.Client
"""

f = open("pom.xml", "w")
f.write(pom)
f.close()

dirs = [ "src/main/java/example", "src/main/resources", "src/main/webapp/WEB-INF" ]
for d in dirs:
    if not os.path.isdir(d):
        os.makedirs(d)

f = open("src/main/webapp/WEB-INF/web.xml", "w")
f.write(webxml)
f.close()

f = open("codegen.sh", "w")
f.write(codegen)
f.close()
os.chmod("codegen.sh", 0755)

f = open("client.sh", "w")
f.write(client)
f.close()
os.chmod("client.sh", 0755)
