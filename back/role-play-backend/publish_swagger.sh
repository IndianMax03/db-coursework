#!/bin/zsh
set -e

echo '----------- Generate role-play-swagger.tar.gz archive -----------'

tar czvf role-play-swagger.tar.gz role-play-swagger/

echo '----------- Copy role-play-swagger.tar.gz to helios.cs.ifmo.ru -----------'

scp -P 2222 role-play-swagger.tar.gz s333057@helios.cs.ifmo.ru:/home/studs/s333057/public_html/

echo '----------- Untar role-play-swagger.tar.gz into ~/public_html/role-play-swagger/ -----------'

ssh s333057@helios.cs.ifmo.ru -p 2222 "cd ~/public_html/ && rm -rf role-play-swagger/ && tar xvf role-play-swagger.tar.gz && rm role-play-swagger.tar.gz"

echo '----------- Cleanup archive -----------'

rm role-play-swagger.tar.gz

echo '----------- Open https://se.ifmo.ru/~s333057/role-play-swagger/ -----------'

open -a "Arc" "https://se.ifmo.ru/~s333057/role-play-swagger/"