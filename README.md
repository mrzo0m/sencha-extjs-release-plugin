# sencha-extjs-release-plugin
Gradle plugin for change extjs app.json version property

Plugin can increment version in app.json automatically or you may set version manually from gradle props args

Then you can use `Ext.manifest.version` in javascript to show application version 

This plugin applicable for CI 

Plugin use jgit to commit and push app.json file into local git repo (workdir is folder with extjs and build.gradle) 

[![We recommend IntelliJ IDEA](http://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)


Before start use you should prepare app.json. By default it contains comments like this:

/**
  some comments here
*/
   
Delete all comments like this. **app.json** should be a **valid json file**.
 
For example to auto increment build value use 

`>gradlew release` 0.0.0.1 to 0.0.0.2

For major version use 
`>gradlew release -Pmajor=true` 0.0.0.1 to 1.0.0.0 

For revision version use 
`>gradlew release -Prevision=true` 0.0.0.1 to 0.1.0.0


For minor version use 
`>gradlew release -Pminor=true` 0.0.0.1 to 0.0.1.0
  
For manual version use 
`>gradlew release  -Pmanual=0.10.0.10` 0.0.0.1 to 0.10.0.10     
     
For commit and push changed app.json file use enablegit.
Possible combine with major, minor e.t.c. 

`>gradlew release -Penablegit=true -Plogin=mygitlogin -Ppassword=mypassword` 0.0.0.1 to 0.0.0.2 then it run: git add, git pull, git push 
  
