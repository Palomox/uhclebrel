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
    when { //equals excepted: 'NO', actual: sh 'if [[ ! -d ~.m2/repository/com/destroystokyo/paper/paper/${MCV}-R0.1-SNAPSHOT]] then echo \'NO\'; exit 0; fi exit 1;' }
  		expression {
		     if(sh (returnStdout: true, """if [[ ! -d ~.m2/repository/com/destroystokyo/paper/paper/${MCV}-R0.1-SNAPSHOT]]
		     then
		     echo \'NO\'
		     exit 0
		     fi
		     exit 1""").contains('OK')){
				return true;
		     }
        }
  	}
  	steps {
   		sh 'wget repo.palomox.ga/files/downloadLatest.sh; bash downloadLatest.sh ${MCV}'
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
