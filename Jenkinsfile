import groovy.json.JsonSlurperClassic
import java.security.*
import java.util.Base64

pipeline {
    agent any
	tools {
        maven 'maven353'
        jdk 'java8172'
    }
    environment{
    	ARTIVER = "1.${BUILD_NUMBER}"
    }
    stages {
    	stage('init') {
    		steps{
    			sh '''
    				echo "PATH				: ${PATH}"
    				echo "M2_HOME 			: ${M2_HOME}"
    				echo "Build Number		: $BUILD_NUMBER"
    				echo "Artifact Version	: ${ARTIVER}"
    			'''
    		}
    	}
        stage('build') {
        	steps{
	            echo 'Building..'
	            sh 'mvn package -Drason.version=${ARTIVER} surefire-report:report site -DgenerateReports=false -Dmaven.javadoc.skip=true -B -V'
            }
        }
        stage('release') {
        	steps{
				echo 'Releasing..'
				script {
					// Create New Release
					// Set @ http://192.168.1.7:8080/configure -> Global properties -> Environment variables -> Add -> GITHUB_OAUTH_TOKEN
					response = sh (
					  script: 'curl -H "Content-Type: application/json" -X POST -d \'{ "tag_name": "\'"${ARTIVER}"\'", "target_commitish": "master", "name": "\'"${ARTIVER}"\'", "body": "Jenkins: \'"${ARTIVER}"\'", "draft": false, "prerelease": false }\' https://api.github.com/repos/anandchakru/rason/releases?access_token=${GITHUB_OAUTH_TOKEN}',
					  returnStdout: true
					).trim()
					def rsp = new JsonSlurperClassic().parseText(response)

					//Upload jar
					uploadCmd = 'curl -H "Authorization: token ${GITHUB_OAUTH_TOKEN}" -H "Content-Type: application/zip" -T target/rason-${ARTIVER}.jar -X POST https://uploads.github.com/repos/anandchakru/rason/releases/' + rsp.id + '/assets?name=rason-${ARTIVER}.jar'
					response2 = sh (
					  script: uploadCmd,
					  returnStdout: true
					).trim()
					def rsp2 = new JsonSlurperClassic().parseText(response2)
					println 'rsp2: ' + response2

					commitId = sh (
						script: 'git log -n 1 --pretty=format:%H',
						returnStdout: true
					)
					def payld = $/ '{"assetId":"${rsp2.id}", "assetUrl":"${rsp2.browser_download_url}","commitId":"${commitId}", "status":"BUILT", "version":"${rsp.id}", "tag":"${ARTIVER}","appId":"2"}' /$
					def signature = sh (
						script: 'echo -n '+payld+' | openssl dgst -sha1 -hmac "kWQdbKwZT3KcmaMCKw9Ta9HAs4AkW2L7AtVB33cyVzmSzJ6U55DQPc3R6t3D6qmw" | sed "s/(stdin)= /sha1=/" ',
						returnStdout: true
					).trim()
					def whCmd = 'curl -H "Content-Type: application/json" -H "X-Hub-Signature: '+signature+'" -X POST -d '+ payld +' https://api.jrvite.com/webhook/cifi-pipe'
					println whCmd
					response3 = sh (
						script: whCmd,
					  	returnStdout: true
					).trim()
					println 'rsp3: ' + response3
				}
			}
		}
        stage('e2e') {
            steps {
                echo 'TODO: Automated End to end testing..'
            }
        }
        stage('deploy') {
            steps {
                echo 'TODO: Automated deploy to test..'
            }
        }
    }
}
