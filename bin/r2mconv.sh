#!/bin/sh
CLASSPATH=target/
for i in target/*jar ; do
	CLASSPATH=$CLASSPATH:$i
done
export CLASSPATH

java org.iterra.utils.ConverterR2M $1
