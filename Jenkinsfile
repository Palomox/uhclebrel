pipeline {
  agent any
  environment {
	MINECRAFT-VERSION='1.16.2'
  }
  stages {
    stage('Check paper depend') {
	/*input version{
	    parameters {
    	    string(name: 'VERSION', defaultValue: '1.16.4', description: 'Version of the plugin')
    	}

	}*/
	def hasMaven = sh 'mvn dependency:get -Dartifact=com.destroystokyo.paper:paper:${MINECRAFT-VERSION}-R0.1-SNAPSHOT -o -DrepoUrl=file://~/.m2/repository';
  	   when {
   	      not {
			expression hasPaper.contains('OK')
      	   }
   	  }
  	   steps {
   	  	sh """
   	  	wget repo.palomox.ga/files/downloadLatest.sh
   	  	bash downloadLatest.sh ${MINECRAFT-VERSION}
   	  	"""
   	  }

    }
      stage('Maven build') {
         steps {
             sh 'mvn install'
           }
      }
      stage('Save artifacts') {
         steps {
             archiveArtifacts artifacts: 'target/*.jar', followSymlinks: false
         }
      }
  }
  post {
      always {
          deleteDir()
      }
  }

}
