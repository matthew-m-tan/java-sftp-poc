### Description
Simple POC for SFTP in java. Motivation is to replace `ganymed-ssh-2` which is restricted to `ssh-rsa` or `ssh-dss` for signature algorithms & key type.
In particular, signatures with those algorithms are deprecated as of ssh v8.8 as well AND the SHA1 crpyto family (which includes said algorithsm) are disabled by default on RHEL operating systems.

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
