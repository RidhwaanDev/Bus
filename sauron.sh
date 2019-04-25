#!/bin/sh
NETID=ra754
PHAB=192.168.151.217
ssh $NETID@sauron.rutgers.edu -L 22:$PHAB:22 -L 80:$PHAB:80 -L 443:$PHAB:443

