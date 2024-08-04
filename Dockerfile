FROM stock-base:latest AS builder

WORKDIR /project

COPY ./ /project/

RUN ls -R

RUN gradle clean build -x test --no-daemon

FROM alpine:3.16

RUN apk add --update openjdk17-jre-headless && \
    rm -rf /var/cache/apl/* \
    && ln -sf /usr/share/zoneinfo/Asia/Taipei /etc/localtime \
    && echo "Asia/Taipei" > /etc/timezone \

ARG VERSION
COPY --from=builder ./project/build/libs/stock-1.0.0.war app.war

ENTRYPOINT ["java","-jar","/app.war"]

EXPOSE 7878