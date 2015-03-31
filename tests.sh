#!/bin/bash

which aws 1>/dev/null || exit 1

BUCKET="identity-functional-tests"

LOCAL_CONF_FILE="dev.conf"
REMOTE_CONF_FILE="s3://${BUCKET}/DEV/dev.conf"
if [ "$1" = "code" ]; then
    LOCAL_CONF_FILE="code.conf"
    REMOTE_CONF_FILE="s3://${BUCKET}/CODE/code.conf"
    shift
fi

SBT_ARGUMENTS="test"
if [ $# -gt 0 ]; then
    SBT_ARGUMENTS=""
    for t in $@; do
        TEST_ARGUMENT="test-only com.gu.identity.integration.test.features.${t}"
        SBT_ARGUMENTS=${SBT_ARGUMENTS}" "${TEST_ARGUMENT}
    done
fi

if [ ! -f "./identity-tests/${LOCAL_CONF_FILE}" ]; then
    aws s3 cp "${REMOTE_CONF_FILE}" "./identity-tests/${LOCAL_CONF_FILE}"
fi

echo "Running tests using config file: ${LOCAL_CONF_FILE}"
sbt -Dconfig.resource=${LOCAL_CONF_FILE} "project identity-tests" "${SBT_ARGUMENTS}"