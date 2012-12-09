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