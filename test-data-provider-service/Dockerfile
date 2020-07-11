FROM openjdk:11
EXPOSE 8080
LABEL MAINTAINER="vkuras"
COPY ./test-data-provider-service/target/test-data-provider-service-2.0.jar ./omni/test-data-provider-service-2.0.jar
COPY open-api.yaml ./omni/
CMD java -jar ./omni/test-data-provider-service-2.0.jar