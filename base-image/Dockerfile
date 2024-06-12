FROM alpine:3.16 AS builder

RUN apk add --no-cache --update openjdk17 gradle nodejs npm && \
    rm -rf /var/cache/apk/*

WORKDIR /project

COPY build.gradle settings.gradle /project/
RUN gradle clean build --no-daemon > /dev/null 2>&1 || true
