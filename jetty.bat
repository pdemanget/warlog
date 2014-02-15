
java -jar target/dependency/jetty-runner.jar target/*.war
rem download alternative:
rem mvn org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
rem    -DrepoUrl=http://download.java.net/maven/2/ \
rem    -Dartifact=robo-guice:robo-guice:0.4-SNAPSHOT
