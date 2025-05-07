package org.demo

class Util implements Serializable {

    def steps

    Util(steps) {
        this.steps = steps
    }

    void printHeader(String message) {
        steps.echo "=============================="
        steps.echo ">>> ${message}"
        steps.echo "=============================="
    }

    void dockerLogin(String username, String password) {
        steps.sh """
            echo '${password}' | docker login -u '${username}' --password-stdin
        """
    }

    void dockerBuildAndPush(String imageName, String dockerfilePath = '.') {
        steps.sh """
            docker build -t ${imageName} ${dockerfilePath}
            docker push ${imageName}
        """
    }
}
