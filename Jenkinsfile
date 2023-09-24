pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                // Récupérer le code source depuis le référentiel Git
                checkout scm
            }
        }
        
        stage('Build and Run Spring Boot App') {
            steps {
                // Build de l'application Spring Boot
                sh 'mvn clean package'  // Vous pouvez également utiliser gradle si vous utilisez Gradle comme gestionnaire de build
                
                // Exécution de l'application Spring Boot
                sh 'java -jar target/ParcInfo-Angular-Spring.jar'  // Assurez-vous de spécifier le chemin correct vers le fichier JAR généré
            }
        }
    }
    
    post {
        success {
            echo 'Le pipeline a réussi !'
        }
        failure {
            echo 'Le pipeline a échoué.'
        }
    }
}
