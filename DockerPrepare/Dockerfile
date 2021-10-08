FROM docker:dind
RUN sh ./usr/local/bin/dockerd-entrypoint.sh
RUN docker pull gcc:8.5-buster
RUN docker create --name gcc --label names=leetcode -w /workdir gcc:8.5-buster /bin/sh -c "while true; do sleep 1000; done"
RUN docker pull openjdk:13-buster
RUN docker create --name java --label names=leetcode -w /workdir openjdk:13-buster /bin/sh -c "while true; do sleep 1000; done"
RUN docker pull golang:1.16-buster
RUN docker create --name golang --label names=leetcode -w /workdir golang:1.16-buster /bin/sh -c "while true; do sleep 1000; done"
RUN docker pull python:3.6-buster
RUN docker create --name python3 --label names=leetcode -w /workdir python:3.6-buster /bin/sh -c "while true; do sleep 1000; done"
