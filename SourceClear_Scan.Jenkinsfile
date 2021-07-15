// include jenkins library
library "infra-jenkins@master"
// permissions
def allowedUsers = ['thein']
def permissions = []
allowedUsers.each{
  permissions.push("hudson.model.Item.Build:${it}")
  permissions.push("hudson.model.Item.Cancel:${it}")
  permissions.push("hudson.model.Item.Configure:${it}")
  permissions.push("hudson.model.Item.Delete:${it}")
  permissions.push("hudson.model.Item.Discover:${it}")
  permissions.push("hudson.model.Item.Read:${it}")
  permissions.push("hudson.model.Item.Move:${it}")
  permissions.push("hudson.model.Item.Workspace:${it}")
  permissions.push("hudson.model.Run.Delete:${it}")
  permissions.push("hudson.model.Run.Replay:${it}")
  permissions.push("hudson.model.Run.Update:${it}")
  permissions.push("hudson.scm.SCM.Tag:${it}")
}
// build properties
properties([
    parameters([
        string(name: 'BUILD_BRANCH', defaultValue: 'master', description: 'Build branch name')
    ]),
    authorizationMatrix(inheritanceStrategy: inheritingGlobal(), permissions: permissions),
    buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '10')),
])
pipeline {
    agent {
       docker {
            label 'ec2_vam_reserved'
            registryCredentialsId 'fuze_dockerhub'
            image 'fuze/dev-tools:69'
            args '-u 0:0 -v /var/run/docker.sock:/var/run/docker.sock'
        }
    }
    // Trigger job every Friday
    triggers { 
        cron('H H(0-5) 1,15 * *')
    }
    options {
        timeout(time: 30, unit: 'MINUTES')
    } 
    stages {
        stage('Checkout settings.xml file') {
            steps {
               script{
                    maven.checkoutSettingsXML()
                }
            }
        }
        stage('Copy settings.xml file for Ant Build') {
            steps {
                sh """
                    [ ! -d ~/.m2 ] && mkdir ~/.m2
                    cp .m2/settings.xml ~/.m2/settings.xml
                """
            }
        }
        stage('Maven Validate'){
            steps{
                script{
                    maven.buildInDocker("validate")
                }
            }
        }
        stage("SourceClear Scan") {
            steps{
                script {
                    sendToSourceClear.credentialsId('SRCCLR_SDK')
                } 
            }  
        }
    }
    post {
        always {
            cleanWs()
            dir("${env.WORKSPACE}@tmp") {
                deleteDir()
            }
            dir("${env.WORKSPACE}@script") {
                deleteDir()
            }
            dir("${env.WORKSPACE}@script@tmp") {
                deleteDir()
            }
        }
    }
}
