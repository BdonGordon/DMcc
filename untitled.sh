#!/bin/bash

folder="$PWD/Dialogc"

ARCHIVE=`awk '/^__ARCHIVE_BELOW__/ {print NR + 1; exit 0; }' $0`
echo "Dialogc Installation has commenced."
echo "Press 'n' key to proceed"
read name

if [ "$name" = "n" ]; then
    echo "Once the script is executed a new directory in the current working directory will be created called 'Dialogc'. In this directory there will be a Dialogc.tar.gz file and another directory called CIS2750A4. The contents of both file and directory are identical. Both contain the source code of Dialogc which will be unpacked."
    echo "Press 'n' key to proceed"

    read name
    if [ "$name" = "n" ]; then
        if [ -d "$folder" ]; then
            echo "Subdirectory by script is unable to be created due to existing directory named 'Dialogc'."
            echo "Installation aborted."
            exit 0
        else
            mkdir "$folder"
            tail -n+$ARCHIVE $0 > "$folder/data.tar.gz"
            cd "$folder"
            tar -zxvf data.tar.gz
        fi
    else
        echo "Bye"
        exit 0
    fi
else
    echo "Nah"
    exit 0
fi

echo "Extraction and build has been complete."

exit
__ARCHIVE_BELOW__
