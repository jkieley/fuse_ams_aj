Dependencies:

- java8
- maven

Install Dependencies:
(On Ubuntu)
Java 8:

    sudo apt-get install python-software-properties
    sudo add-apt-repository ppa:webupd8team/java
    sudo apt-get update
    sudo apt-get install oracle-java8-installer
    
Install Maven:

    sudo apt-get update
    sudo apt-get install maven

start the application:
From the root of the project:

    mvn spring-boot:run