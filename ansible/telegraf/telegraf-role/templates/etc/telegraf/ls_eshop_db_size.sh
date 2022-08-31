#!/usr/bin/env bash
ls -s --block-size=1 "/data/eshop/dist/eshop.db" | awk '{print "[ { \"bytes\": "$1", \"lsfile\": \""$2"\" } ]";}'
