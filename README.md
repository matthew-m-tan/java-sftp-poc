### Description
Simple POC for SFTP in java using Jsch library over ganymed-ssh2 which is using deprecated ciphers and is not actively maintained.

### Build Steps
```sh
javac -cp ".:lib/jsch-0.1.55.jar" -d classes src/**/**/*.java

```

### To Run
```sh
java -cp ".:lib/jsch-0.1.55.jar:classes" src/com/example/Main
```

### To Build as Jar
```sh
# This is out of date don't do this
jar cf sftp-poc.jar classes/*.class
```
