pipeline {
  agent any
  environment {
	MCV='1.16.1'
  }
  stages {
    stage('Check paper depend') {
    when { //equals excepted: 'NO', actual: sh 'if [[ ! -d ~.m2/repository/com/destroystokyo/paper/paper/${MCV}-R0.1-SNAPSHOT]] then echo \'NO\'; exit 0; fi exit 1;' }
  		expression {
  			 sh '''echo "if [[ ! -d ~.m2/repository/com/destroystokyo/paper/paper/${MCV}-R0.1-SNAPSHOT ]]
  			 then
		     echo NO
		     exit 0
		     fi
		     exit 1" > check.sh'''
		     if(sh (returnStdout: true, script: 'bash check.sh').contains('NO')){
				return true;
		     }
        }
  	}
  	steps {
   		sh 'wget files.palomox.ga/files/downloadLatest.sh; bash downloadLatest.sh ${MCV}'
   	}
    }
      stage('Maven build') {
         steps {
             sh 'mvn install'
           }
      }
      stage('Deploy to maven') {
         steps {
             sh 'mvn deploy'
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
