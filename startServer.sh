#!/bin/bash

# TODO: Set port with first argument of bash script
PORT=

if [[ -z $PORT ]]; then
  echo "Port must be set!"
  exit 1
fi

# TODO: Add java command to start chat server
