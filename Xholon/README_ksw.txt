README_ksw.txt
August 5, 2013
Ken Webb
--------------

To build development code:
ant devmode

To build superdevmode code:
ant superdevmode

To build production obfuscated code:
ant build

To build production pretty-printed code:
ant -Dgwt.args="-style PRETTY" gwtc

To build production with strict error checking (shows causes for silent fails)
ant -Dgwt.args="-strict" gwtc

To save generated files
ant -Dgwt.args="-gen /tmp" devmode
ant -Dgwt.args="-strict -gen /tmp" gwtc

To search for text in files
grep -R --include='*.java' -i 'abc' *
grep -R --include='*.xml' -i 'abc' *

To generate javadoc
cd ~/gwtspace/Xholon/Xholon
javadoc -d ~/gwtspace/javadoc -public -sourcepath src -subpackages org -header '<b>Xholon GWT 0.9.0</b>' 2>> javadocErrOut.txt

To run JUnit tests
cd ~/gwtspace/Xholon/Xholon
ant test.prod

To generate yuidoc
 copy XholonJsApi.js to ~/gwtspace/yuidoc
~/gwtspace/yuidoc
yuidoc .

To search and replace in all files in a directory (recursively)
 search recursively from the directory of execution
 operate on only regular, readable, writeable files
 WARNING this changes the file date/time for ALL files it examines
find ./ -type f -readable -writable -exec sed -i "s/Chameleon/Furcifer/g" {} \;
find ./ -type f -readable -writable -exec sed -i "s/xholon.tutorials/user.app/g" {} \;

To use git
git push origin master

