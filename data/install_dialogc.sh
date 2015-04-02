#!/bin/bash

NEW_UUID=$(cat/dev/urandom | tr -dc 'a-zA-Z' | fold -w 32 | head -n 1) 

echo "Installation Script of Dialogc"
echo "Press 'n' key to proceed"

read next

if [ "$next" == "n" ]; then
	echo "Got it"
	

fi