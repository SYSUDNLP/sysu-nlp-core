#!/usr/bin/env bash
print_usage ()
{
  echo "Usage: sh run.sh COMMAND"
  echo "where COMMAND is one of the follows:"
  echo "readjson        Read file of Amazon reviews and wrire reviews to another file"
  echo "test            Test whether it works."
  exit 1
}
if [ $# = 0 ] || [ $1 = "help" ]; then
  print_usage
fi

COMMAND=$1
shift

if [ "${JAVA_HOME}" = "" ]; then
  echo "Error: JAVA_HOME is not set."
  exit 1
fi

JAVA=${JAVA_HOME}/bin/java
HEAP_OPTS="-Xmx1500m"

JAR_NAME=`ls |grep jar|grep -v original-`

CLASSPATH=${CLASSPATH}:$JAVA_HOME/lib/tools.jar
CLASSPATH=${CLASSPATH}:conf
CLASSPATH=${CLASSPATH}:$JAR_NAME
for f in lib/*.jar; do
  CLASSPATH=${CLASSPATH}:$f;
done

params=$@

if [ "$COMMAND" = "test" ]; then
    CLASS=cli.TestCli
elif [ "$COMMAND" = "readjson" ]; then
    CLASS=cli.ReadJsonCli
elif [ "$COMMAND" = "tokenize" ]; then
    CLASS=cli.TokenizerCli
else
    CLASS=${COMMAND}
fi

"$JAVA" -Djava.io.tmpdir=/var/spark/tmp -Djava.awt.headless=true ${HEAP_OPTS} -classpath "$CLASSPATH" ${CLASS} ${params}