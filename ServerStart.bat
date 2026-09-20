@ECHO OFF

java -server -noverify -XX:+UseG1GC -XX:+UseStringDeduplication -XX:+UseFastAccessorMethods -XX:+AggressiveOpts -XX:+OptimizeStringConcat -XX:+UseBiasedLocking -jar l1jserver2.jar

PAUSE