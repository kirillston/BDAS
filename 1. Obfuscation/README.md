# Obfuscation
This project contains software solution of an XML-obfuscator that affects attributes values and simple texts making it unreadable.

Algorithm uses simple one-character permutation cipher.

Program works in two modes:
- obfuscation
- unobfuscation

If you process file in these two modes serially, you'll get initial file.

##Usage

```bash
gradle run args='[obfuscate/unobfuscate] [filepath]'
``` 

First argument stands for the mode of application.
Second - for the path to input file.