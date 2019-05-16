package com.github.mrzo0m

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.transport.CredentialsProvider
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import org.gradle.api.Plugin
import org.gradle.api.Project

class SenchaExtjsReleasePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        def extension = project.extensions.create("features", PropertiesExtension)

        project.task("release") {

            doFirst {
                def jsonSlurper = new JsonSlurper()
                def reader = new BufferedReader(new InputStreamReader(new FileInputStream("app.json"), "UTF-8"))
                def jsonObject = jsonSlurper.parse(reader)
                assert jsonObject instanceof Map
                println "current version : " + jsonObject.version
                def v = jsonObject.version
                def matcherv = (v ==~ /(\d\.){3}(\d)/)
                println "Version formant validate: " + matcherv
                String[] parts = v.split("\\.")
                println "old build version is " + parts[3]
                if (extension.manual?.size() > 0) {
                    println "manual version setup: " + extension.manual
                    jsonObject.version = extension.manual
                    writeVersion(jsonObject)
                } else {
                    if (extension.major) {
                        println "Extension major: " + extension.major
                        def majorVal = Integer.parseInt(parts[0])
                        parts[0] = ++majorVal
                        parts[1] = 0
                        parts[2] = 0
                        parts[3] = 0
                        println "new major version is: " + parts[0]
                    }

                    if (extension.revision) {
                        println "Extension revision: " + extension.revision
                        def revisionVal = Integer.parseInt(parts[1])
                        parts[1] = ++revisionVal
                        parts[2] = 0
                        parts[3] = 0
                        println "new revision version is: " + parts[1]
                    }

                    if (extension.minor) {
                        println "Extension minor: " + extension.minor
                        def minorVal = Integer.parseInt(parts[2])
                        parts[2] = ++minorVal
                        parts[3] = 0

                    }
                    if (!extension.major && !extension.revision && !extension.minor) {
                        def buildVal = Integer.parseInt(parts[3])
                        parts[3] = ++buildVal
                        println "new build version is " + parts[3]
                    }
                    def newVersion = parts.join(".")
                    jsonObject.version = newVersion
                    writeVersion(jsonObject)
                }

            }
            doLast {
                if (extension.enablegit) {
                    println "Run JGit..."
                    Repository repository = RepositoryHelper.openJGitRepository()
                    String login = "", password = ""
                    if (project.hasProperty("login")) {
                        login = project.login
                    }
                    if (project.hasProperty("password")) {
                        password = project.password
                    }
                    CredentialsProvider credProv = new UsernamePasswordCredentialsProvider(login, password)

                    Git git = new Git(repository)
                    git.add()
                            .addFilepattern("app.json")
                            .call()
                    println "JGit app.json added"
                    git.commit()
                            .setMessage("Commit new version app.json")
                            .call()
                    println "JGit app.json commit complete"
                    git.pull()
                            .call()
                    println "JGit pull"
                    git.push()
                            .setRemote("origin")
                            .setCredentialsProvider(credProv)
                            .call()
                    println "JGit push"
                    println "DONE"
                }
            }
        }

        project.task("pause") {
            doLast {
                println "pause the video"
            }
        }

    }

    private void writeVersion(Map jsonObject) {
        def prettyJson = JsonOutput.prettyPrint(JsonOutput.toJson(jsonObject))
        new File("app.json").write(prettyJson)
        println "version: " + jsonObject.version
        println "====================="
    }
}