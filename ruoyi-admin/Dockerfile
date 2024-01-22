FROM fxdom/maven-openjdk-8:3.6.3
LABEL org.opencontainers.image.authors="fx"

ARG application_name='admin-service'
ENV JAVA_OPTS=""

RUN mkdir /app
COPY ./target/$application_name.jar /app/java-application.jar
WORKDIR /app
RUN chown -R javauser:javauser /app
USER javauser

EXPOSE 8081

ENTRYPOINT ["/usr/local/bin/dumb-init", "--"]

CMD ["bash", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/java-application.jar" ]

