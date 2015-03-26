#!/bin/bash

which aws 1>/dev/null || exit 1

BUCKET="identity-functional-tests"

LOCAL_CONF_FILE="dev.conf"
REMOTE_CONF_FILE="s3://${BUCKET}/DEV/dev.conf"
if [ "$1" = "code" ]; then
    LOCAL_CONF_FILE="code.conf"
    REMOTE_CONF_FILE="s3://${BUCKET}/CODE/code.conf"
fi

if [ ! -f "./identity-tests/${LOCAL_CONF_FILE}" ]; then
    aws s3 cp "${REMOTE_CONF_FILE}" "./identity-tests/${LOCAL_CONF_FILE}"
fi

echo "Running tests using config file: ${LOCAL_CONF_FILE}"
sbt -Dconfig.resource=${LOCAL_CONF_FILE} "project identity-tests" "test"