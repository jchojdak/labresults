#!/bin/bash

set -e

echo "✅ Pushing Docker images to Hub..."

#docker login

docker push jchojdak/labresults:config-server
docker push jchojdak/labresults:eureka-server
docker push jchojdak/labresults:api-gateway
docker push jchojdak/labresults:customer-service
docker push jchojdak/labresults:notification-service
docker push jchojdak/labresults:order-service
docker push jchojdak/labresults:result-service
docker push jchojdak/labresults:sample-service

echo "✅ Docker images have been pushed to Hub."