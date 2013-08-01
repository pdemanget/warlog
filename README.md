warlog
======

Display logs everywhere  
    
License: GPL V3  
Author : Philippe DEMANGET  
1st Release Date: 28/06/2013  

note on ExtJS:  
	this is an external library to add to ext/ folder of the webapp. You can get its source from sencha or from dependencies of the pom.xml, just unzip the jar (org.webjars/extjs)  
  
change list:  
 - [X] : last page tail option.  
 - [ ] : dynamic parsing server-side  
 - [ ] : line filtering server-side or client side  
 - [X] : line number in file  
 - [X] : use hash url  
 - [X] : only call file url on files  
 - [ ] : set more logs  
 - [X] : Use id instead of path for identifier (tree bug)  
 - [ ] : auto-expand tree  
 - [ ] : manage folders bookmarks  
  
known bugs  
 - [ ] : need to reload page 1 when file content is reduced (on log roll)  
  
Development note:  
- Jetty: uses jettyRunner. need to remove tomcat security annotation usage: java -jar jetty-runner warlog.war  
