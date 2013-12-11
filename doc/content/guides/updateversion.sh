from="$1"
prefix1="version="
prefix2="gt;"
to="$2"
files="*.html"

if [ $# -eq 2 ]; then
    grep -r "$prefix1$from" $files
    grep -r "$prefix2$from" $files
    echo 'Update all above matches?(y/n)'
    read answer
    if [ "$answer" == "y" ]; then
        grep -rl $prefix1$from $files | while read f; do
            sed -i -e "s/$prefix1$from/$prefix1$to/g" "$f"
        done
        grep -rl $prefix2$from $files | while read f; do
            sed -i -e "s/$prefix2$from/$prefix2$to/g" "$f"
        done
        #grep -rl $prefix2$from $files | xargs sed -i -e "s/$prefix2$from/$prefix2$to/g"
        echo Updated.
    else
        echo Skipped update.
    fi
else
    echo "Updates all tightdb version number in all $files files."
    echo 'Usage: updateversion.sh old-versionnumber new-versionnumber'
    echo '      e.g. updateversion.sh 0.1.2 0.1.3'        
fi
