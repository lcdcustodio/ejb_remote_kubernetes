EJB Remote Client over Kubernetes
===========


Deploying EJB Remote Client application over Kubernetes cluster. For achieving the aim is mandatory to follow containerization approach. For this purposes was considered WildFly Docker image on EJB Remote side. In the Client side was used CentOS image but it could OpenJDK from Alpine. It is required also a Kubernetes Cluster up and running.  


------------

1- High Level Design
===========================


![Alt text](/picture/hld.png "Solution Architecture")


------------

2- Installation
===========================

First of all, let’s clone the repository and build the application:

```
    git clone https://github.com/lcdcustodio/ejb_remote_kubernetes.git
    # Server Side    
    mvn clean install -f .\server-side\
    # Client Side
    mvn clean compile assembly:single -f .\client\
```    

Once the Maven build is finished, the deployment archive has been created in target folder. Now, we be able to create images required for Pods in the Solution Architecture.  

```
    # Server Side
    docker build -f server-side/Dockerfile -t ejb_demo_server .
    # Client Side
    docker build -f client/Dockerfile -t ejb_remote_client .    

```    


------------

3- Kubernetes resources assembly 
===========================

Yaml files in charge to create all of kubernetes resources for this demo are available at deploy folder. Through the Kubernetes command-line tool, kubectl, let's run the following instructions:  

```
    # create deployment, pod and replicaset for EJB remote application
    kubectl apply -f .\server-side\deploy\deployment.yaml
    # create service for EJB application
    kubectl apply -f .\server-side\deploy\service.yaml
    # create Client pod
    kubectl apply -f .\client\deploy\pod-client.yaml
```    

------------

4- Health Check 
===========================

One all of kubernetes resources were created, let's proceed with health check through the following commands:  


```
    kubectl get services # get service info
    kubectl get deployments # get deployment info
    kubectl get rs # get replicaset info
    kubectl get pods -o wide  # get pod info
    kubectl describe service svc-ejb-demo-k8s #endpoint address to load balance
    kubectl logs -f pod-client #soap script is up and running
```

------------

5- Use Case - EJB invocation from a remote client 
===========================

According High Level Desigin session, the main purpose is to demonstrate how to lookup and invoke on EJBs deployed in different Pods from another instance. We have two EJBs for a standalone Client. That's why we take in place the service resource from Kubernets. This component is able to abstract EJBs Pods solving their IP addresses to remote Client. Let's check out through following commands: 

- Get a shell to access into the pod:
```
    kubectl exec -it  <pod_name> -- bash 
```
- In that case, <pod_name> = pod-client
- Inside the pod, it is time to run the standalone client:
```
    java -jar app.jar 
```
- The client output will show up in the cli
- It is possible also to see logs from EJBs Pods in order to follow invocations from server side perspective:
```
    kubectl logs -f <pod_name> #pods created from deployment 
 
```
- Running several times the Client you will be able to see the service balancing the load between EJBs Pods.

------------

6- Use Case - check pod resiliency 
===========================


- kubectl get pods -o wide  # get pods info
- kubectl delete pods --all  #pods will be back, except pod-client
- kubectl delete pods <pod_name> # pod downtime is really fast..
- kubectl get pods -o wide  # get pods info
