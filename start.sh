#!/bin/sh

TRUNCATE=$1

if [ -z "$SHELLED" ]; then
    export SHELLED=1
    exec "$SHELL" "$0" "$@"
fi

if [ "$TRUNCATE" = "y" ]; then
    ./mvnw compile exec:java 2>&1 | sed 's/\x1b\[[0-9;]*m//g' > logs/out.log
elif [ "$TRUNCATE" = "n" ]; then
    ./mvnw compile exec:java 2>&1 | sed 's/\x1b\[[0-9;]*m//g' >> logs/out.log
else
    echo "Usage: ./start.sh <y|n> (truncate logs)"
    exit 1
fi
