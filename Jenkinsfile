pipeline {
  agent any
  environment {
	MCV='1.16.2'
  }
  stages {
    stage('Check paper depend') {
	/*input version{
	    parameters {
    	    string(name: 'VERSION', defaultValue: '1.16.4', description: 'Version of the plugin')
    	}

	}*/
	when {
	    expression {

    	}
	}
	when {
		not {
			equals excepted: OK, actual: sh """
				if [[ -d ~.m2/repository/com/destroystokyo/paper/paper/${MCV}-R0.1-SNAPSHOT]]
				then
				echo 'OK'
				exit 0
				fi
				exit 1"""
      	}
   	}
  	steps {
   		sh """
   	  	wget repo.palomox.ga/files/downloadLatest.sh
   	  	bash downloadLatest.sh ${MCV}
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
