# Setting up sample-springboot-app  

1. Install the repo on your local by: git clone https://github.com/indervirbanipal/sample-springboot-app.git
2. Import the project in Intellij as a gradle project. 
3. Go to command line (in app's home folder) and run: ./gradlew clean build
4. Look for ./build/libs/sample-springboot-app.war which gets generated.
5. The next step is to build a docker image. Do `docker images` to check the existing images on the system.
6. Run `sh dockerCreate.sh` to create the docker image. 
7. You should see sample-springboot-app-image:latest under `docker images`.
8. Go to http://localhost:8080/ and enter paul_admin:123456 to see the app up and runnning.
9. Go to Postman at http://localhost:8080/ to see the app up and running.
10. Use `sh dockerPush.sh` to push to the repository you want to.
11. Use `docker image rm <image-id>` to remove the docker image from your local (see image-id from `docker images`).
12. Use `sh dockerPull.sh` to pull the image from the repository you want from.
13. See the image pulled from repo using `docker images`.

# Setting up deployment (Kubernetes + Helm)

1. Install the required tools using `brew install kubectl`, `brew cask install minikube`, `brew cask install virtualbox`.
2. Run `minikube start`.
3. Run `minikube config set vm-driver virtualbox`.
4. Run `kubectl config use-context minikube`.
5. Run `kubectl cluster-info`. Make sure Kubernetes master is running at https://192.168.99.100:8443 . Checkout the url http://127.0.0.1:50413/api/v1/namespaces/kube-system/services/http:kubernetes-dashboard:/proxy/#!/overview?namespace=default 
6. Push the image to minikube using `docker save sample-springboot-app-image:latest | (eval $(minikube docker-env) && docker load)`
7. Create deployment `kubectl run sample-springboot-app-dep1 --image=sample-springboot-app-image:latest --image-pull-policy=IfNotPresent`. Then check using `kubectl get deployments` >> shows `sample-springboot-app-dep1` running. 
8. Also, check through `kubectl get pods`. Check logs using `kubectl logs <pod name>` or just do `kubectl get all` or `kubectl get all -o wide`.
9. Expose the deployment as a service through `kubectl expose deployment sample-springboot-app-dep1 --port=8080 --type=LoadBalancer --name=sample-springboot-app-serv1`. `service/sample-springboot-app-serv1` gets exposed.
10. `kubectl get services` should show `sample-springboot-app-serv1` running.
11. Do minikube ip to check the IP and lets say you get 192.168.99.105. So, hit the url 192.168.99.105:30658
 where 30658 is listed alongwith 8080 under `kubectl get services` for the current service.
12. Better way of managing distributed deployments - Helm! Install helm using `brew install kubernetes-helm`. Use `kubectl config current-context` to check if the current context is `helm`. Install tiller using `helm init --history-max 200`.
13. After tiller install, it can be verified by `kubectl get pods --namespace kube-system | grep tiller`.
14. Check the versions for the helm client and server using `helm version`.

# Setting up and configuring database using Helm

1. Use `helm install --name postgres1 stable/postgresql` to install local postgres pods. You can verify if they are in the same namespace as the spring boot app using `kubectl get pods --namespace default`.

