warlog
======

Display logs everywhere  
    
License: GPL V3  
Author : Philippe DEMANGET  
1st Release Date: 28/06/2013  

note on ExtJS:  
	this is an external library to add to ext/ folder of the webapp. You can get its source from sencha or from dependencies of the pom.xml, just unzip the jar (org.webjars/extjs)  

installation Note:
this war file has been tested under java7 environnement with 4 Application server:
  Tomcat: 	add war to tomcat/webapps
  JBoss: 	add war file in jboss-eap-6.1/standalone/deployments/
  Glassfish: 	add war file to glassfishv3/glassfish/domains/domain1/autodeploy
  Jetty: 	launch "java -jar jetty-runner warlog.war"
  
Change log
==========
V0.3.0
------
managing file as text

V0.2.0
------
manageing tabs

V0.1.0
------
first release with tree, file reading as list

change list
===========
- [ ] : file saving
- [ ] : Security based on openid
- [ ] : 0.3 manage multwindow trees in same page  
- [X] : last page tail option.  
- [ ] : dynamic parsing server-side  
- [X] : line filtering server-side or client side  
- [X] : line number in file  
- [X] : use hash url  
- [X] : only call file url on files  
- [ ] : set more logs  
- [X] : Use id instead of path for identifier (tree bug)  
- [X] : auto-expand tree  
- [ ] : manage folders bookmarks  
  
known bugs  
- [ ] : need to reload page 1 when file content is reduced (on log roll)  
  
Development note:  
- security: this only works in tomcat, and we need to specify a different web.xml

logs path:
glassfish:http://localhost:8080/warlog/#/opt/glassfishv3/glassfish/domains/domain1/logs/server.log?File
tomcat:/var/log/tomcat
