 pipeline {
  agent any
  stages {
    stage('Check paper depend') {
	/*input version{
	    parameters {
    	    string(name: 'VERSION', defaultValue: '1.16.4', description: 'Version of the plugin')
    	}

	}*/
  	   steps {
   	    def hasMaven = sh 'mvn dependency:get -Dartifact=com.destroystokyo.paper:paper:$1.16.2-R0.1-SNAPSHOT -o -DrepoUrl=file://~/.m2/repository';
   	  	echo hasMaven;
   	  	if(hasMaven.equals("[INFO] BUILD SUCCESS")){

   	  	}
   	  }

    }
      stage('Maven build') {
      when {
      	equals expected: [INFO] BUILD SUCCESS, actual: hasMaven
      }
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

}
