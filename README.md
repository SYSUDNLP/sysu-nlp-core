##TUTORIAL

###Introduction
This project is a open-source project created by the Y. Zhou. You can use this package complete many tasks of Natural Language Processing.

###Dependencies

> Java >= 1.8

> Bash Shell

> Ruby

> Maven(A package manager tool of java)

> Jars in the file **pom.xml**


###Usage 
Firstly, you should check out this project to your local environment(or EC2..), an example as follows

+ git clone https://github.com/SYSUNLP/sysu-nlp-core.git /your/local/path
+ git pull
  
Then, download the jars in pom.xml and compile. The maven repositories path is ~/.m2/repository/.

+ cd sysu-nlp-core+
+ mvn package
  
Finally, 
+ cd target
+ sh run.sh 
