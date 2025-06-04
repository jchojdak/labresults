#!/bin/bash

set -e

# ======== CONFIG =========
VALID_PROFILES=("local" "k8s" "docker")
# =========================


PROFILE=$1

if [[ ! " ${VALID_PROFILES[*]} " =~ " ${PROFILE} " ]]; then
  echo "❌ Invalid profile: $PROFILE"
  echo "Usage: ./start.sh [local|k8s|docker]"
  exit 1
fi

echo "✅ Starting... Profile: $PROFILE"

if [[ "$PROFILE" == "local" ]]; then
  echo "Starting in local mode..."

  # TODO

  mvn clean install -DskipTests

  sleep 5

  java -jar config-server/target/*.jar --spring.profiles.active=native

  sleep 40

  java -jar eureka-server/target/*.jar --spring.profiles.active=local

  sleep 40

  java -jar api-gateway/target/*.jar --spring.profiles.active=local

  sleep 20

  java -jar services/customer-service/target/*.jar --spring.profiles.active=local
  java -jar services/sample-service/target/*.jar --spring.profiles.active=local

  sleep 5

  java -jar services/order-service/target/*.jar --spring.profiles.active=local
  java -jar services/result-service/target/*.jar --spring.profiles.active=local

  sleep 5

  java -jar services/notification-service/target/*.jar --spring.profiles.active=local

elif [[ "$PROFILE" == "docker" ]]; then
  echo "Starting in Docker mode..."

  docker-compose -f infra/docker/docker-compose.yml up -d
elif [[ "$PROFILE" == "k8s" ]]; then
  echo "Starting in Kubernetes mode..."

  minikube start --nodes=1

  kubectl apply -f infra/k8s/minikube/namespace.yml

  # Postgres
  kubectl apply -f infra/k8s/minikube/postgres/pvc.yml
  kubectl apply -f infra/k8s/minikube/postgres/configmap.yml
  kubectl apply -f infra/k8s/minikube/postgres/deployment.yml
  kubectl apply -f infra/k8s/minikube/postgres/service.yml

  sleep 60

  # Keycloak
  kubectl apply -f infra/k8s/minikube/keycloak/configmap.yml
  #kubectl apply -f infra/k8s/minikube/keycloak/pvc.yml
  kubectl apply -f infra/k8s/minikube/keycloak/deployment.yml
  kubectl apply -f infra/k8s/minikube/keycloak/service.yml

  sleep 20

  # Config Server
  kubectl apply -f infra/k8s/minikube/config-server/deployment.yml
  kubectl apply -f infra/k8s/minikube/config-server/service.yml

  sleep 120

  # Eureka Server
  kubectl apply -f infra/k8s/minikube/eureka-server/deployment.yml
  kubectl apply -f infra/k8s/minikube/eureka-server/service.yml

  sleep 120

  # Api Gateway
  kubectl apply -f infra/k8s/minikube/api-gateway/deployment.yml
  kubectl apply -f infra/k8s/minikube/api-gateway/service.yml

  sleep 20

  # Customer Service
  kubectl apply -f infra/k8s/minikube/services/customer-service/deployment.yml
  kubectl apply -f infra/k8s/minikube/services/customer-service/service.yml

  # Sample Service
  kubectl apply -f infra/k8s/minikube/services/sample-service/deployment.yml
  kubectl apply -f infra/k8s/minikube/services/sample-service/service.yml

  #Order Service
  kubectl apply -f infra/k8s/minikube/services/order-service/deployment.yml
  kubectl apply -f infra/k8s/minikube/services/order-service/service.yml

  # Result Service
  kubectl apply -f infra/k8s/minikube/services/result-service/deployment.yml
  kubectl apply -f infra/k8s/minikube/services/result-service/service.yml

  # Notification Service
  kubectl apply -f infra/k8s/minikube/services/notification-service/deployment.yml
  kubectl apply -f infra/k8s/minikube/services/notification-service/service.yml
else
  echo "❌ Unknown profile: $PROFILE"
  exit 1
fi

echo "✅ LabResult is running."