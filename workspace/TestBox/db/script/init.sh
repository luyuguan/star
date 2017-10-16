#! /bin/bash
echo 'init db'
cd ~/Documents/jardir/workspace/TestBox/db/script
mysql -u root -plyg < ./init.sql
echo 'init db done'