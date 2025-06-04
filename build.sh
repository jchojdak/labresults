#!/bin/bash

set -e

echo "✅ Building Docker images..."

docker build -t jchojdak/labresults:config-server config-server
docker build -t jchojdak/labresults:eureka-server eureka-server
docker build -t jchojdak/labresults:api-gateway api-gateway
docker build -t jchojdak/labresults:customer-service services/customer-service
docker build -t jchojdak/labresults:notification-service services/notification-service
docker build -t jchojdak/labresults:order-service services/order-service
docker build -t jchojdak/labresults:result-service services/result-service
docker build -t jchojdak/labresults:sample-service services/sample-service

echo "✅ Docker images have been built."