radioexpert
===========

Java Client/Server With PubSub and APIs

Installation
------------
To build this project, install jdk7 with javafx support and add a settings.xml for maven in ~/.m2/settings.xml. settings.xml has to contain the following content :


```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                     http://maven.apache.org/xsd/settings-1.0.0.xsd">

   <profiles>
       <profile>
           <id>javafx</id>
           <activation>
               <activeByDefault>true</activeByDefault>
           </activation>
           <properties>
               <javafx.rt.jar>/Library/Java/JavaVirtualMachines/jdk1.7.0_09.jdk/Contents/Home/jre/lib/jfxrt.jar</javafx.rt.jar>
           </properties>
       </profile>
   </profiles>

   <activeProfiles>
       <activeProfile>javafx</activeProfile>
   </activeProfiles>
</settings>
```

Run Application
---------------
You can run the server instance with:
```sh
mvn exec:java
```
This contains:

* Instance of Apache ActiveMQ as message broker
* Instance of filebased h2 database
* External Data Services like Twitter, Facebook and Mail
* PubSub logik and database storage with hibernate
