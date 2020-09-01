call java -version
call echo %JAVA_HOME%
call mvn --version
call mvn clean verify -e
PAUSE
