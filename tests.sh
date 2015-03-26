#!/bin/bash
CONF_FILE="local.conf"
if [[ $1 = "code" ]]; then
    CONF_FILE="dev.conf"
fi

echo "${CONF_FILE}"
sbt -Dconfig.resource=${CONF_FILE} "project identity-tests" "test"