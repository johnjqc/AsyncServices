#!/bin/sh

echo "Creating topics..."

kafka-topics --bootstrap-server kafka:9092 \
  --create --if-not-exists \
  --topic client.created \
  --partitions 1 \
  --replication-factor 1

kafka-topics --bootstrap-server kafka:9092 \
  --create --if-not-exists \
  --topic client.updated \
  --partitions 1 \
  --replication-factor 1

echo "Topics ready"