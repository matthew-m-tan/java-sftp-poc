### Description
Simple POC for SFTP in java using Jsch library over ganymed-ssh2 which is using deprecated ciphers and is not actively maintained.

### Build Steps
```sh
javac -d classes src/*.java
```

### To Run
```sh
java classes/Main
```

### To Build as Jar
```sh
jar cf sftp-poc.jar classes/*.class
```
