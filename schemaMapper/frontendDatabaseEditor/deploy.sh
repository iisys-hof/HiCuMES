#/bin/bash
if [ $# -eq 0 ]
  then
    echo "No arguments supplied"
fi

for var in "$@"
  do
    echo "Copy files to server: $var"
    rsync -rav -e ssh ./dist/frontendDatabaseEditor root@$var:/var/www/html/

done
