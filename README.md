# java-debugging
Java debugging examples

# JDB examples

# Multithreaded applications debugging

# Code generation debugging

# FFM API debugging

```
gcc -g -o0 -shared -fpic -o libfibffm.so -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux fibffm.cpp

javac -g --enable-preview --release 21 com/github/alextokarew/joker/jvmdebug/FibFFM.java
java --enable-preview --enable-native-access=ALL-UNNAMED com.github.alextokarew.joker.jvmdebug.FibFFM

java --enable-preview --enable-native-access=ALL-UNNAMED -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005 com.github.alextokarew.joker.jvmdebug.FibFFM
```
