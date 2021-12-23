#!/bin/sh
docker create --name java --label names=leetcode -w /workdir openjdk:13-buster /bin/sh -c "while true; do sleep 1000; done"
docker create --name golang --label names=leetcode -w /workdir golang:1.16-buster /bin/sh -c "while true; do sleep 1000; done"
docker create --name python3 --label names=leetcode -w /workdir python:3.6-buster /bin/sh -c "while true; do sleep 1000; done"
docker create --name python3 --label names=leetcode -w /workdir python:3.6-buster /bin/sh -c "while true; do sleep 1000; done"