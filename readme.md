# Three layers template hello world like Java application, back end and downstreams microservices with a banal/trivial business logic.

### Business Logic
1. Frontend asks user whom too greet, e.g. "world"
0. Backend is asked for verb to be used in the greeting, e.g. "Hello"
0. A downstream microservice is asked for the adjective to colour the greeting, e.g. "strange"
0. The resulting greeting is presented for the user, e.g. "Hello strange world"

### Environment
- Windows 10
- Java 13
- Junit Jupiter 5.4.1 
- Hamcrest 2.1
- Maven 3.6.2

### Inspirations
- Simplicity
- Not using overkill technologies and frameworks
- Decoupling
- No superfluous, silly or evident commenting
- Self explanatory code, using descriptive and precise words for method names, variables etc..
- Kill checked exceptions as fast as they are caught, don't rethrow them.
- Use return codes instead of exceptions to communicate flow variations, including errors

### Tools and technologies used so far
- logback for logging
- Junit 5 (Jupiter) for testing
- JaCoCo for coverage
- PiTest for mutation testing
- Maven/surefire/site reporting
- H2 for RDBMS prod and testing
- Three layers: Frontend, immediate backend and downstreams far backend
- Lombok (magic, but allowing)
- Plain JDBC
- Jetty for servlet container
- Jersey for HTTP/rest communication
- REST with JSON  and HTTP verb calls from backend to far backend
- org.apache.commons.configuration2 for application.properties related stuff

#### Summary over technologies/designs so far

|                  | Frontend      | Backend | Far backend |
| ---------------- |:-------------:| -------:|------------:|
| logback          |      X        |   X     |    X        |
| jupiter          |      X        |   X     |    X        |
| hamcrest         |      X        |   X     |    X        |
| JaCoCo           |      X        |   X     |    X        |
| PiTest           |      X        |   X     |    X        |
| H2               |     N/A       |  N/A    |    X        |
| lombok           |     N/A?      | NOT YET |    X        |
| jetty            |     N/A       | NOT YET |    X        |
| jersey           |     N/A       | NOT YET |    X        |
| rest client      |   NOT YET     |   X     |   N/A       |
| rest server      |     N/A       | NOT YET |    X        |
| json serialize   |     N/A       |  N/A    |    X        |
| json deserialize |   NOT YET     |   X     |   N/A       |
| conf2            |     N/A?      | NOT YET |    X        |
| QueryDSL         |     N/A       |  N/A    |   N/A?      |
| doInTransaction  |     N/A       |  N/A    |  NOT YET    |
| Resilience       |     N/A       | NOT YET |   N/A       |
| Prometheus       |     N/A       | NOT YET |  NOT YET    |
| swagger          |     N/A       | NOT YET |  NOT YET    |
| PACT             |     N/A       | NOT YET |  NOT YET    |
| web components   |   NOT YET     |  N/A    |   N/A       |
| NCSS             |   NOT YET     | NOT YET |  NOT YET    |
| OWASP            |      X        |   X     |    X        |
| checkstyle       |   NOT YET     | NOT YET |  NOT YET    |
| Separate project |   NOT YET     | NOT YET |  NOT YET    |
| Java 9+ modules  |     N/A?      | NOT YET |  NOT YET    |
#### Detail TODO-s:
- Stop logging SQL errors when selecting from not existing schema/table
- Remove overlapping classes when building fat jars
- Remove JANSI exception logging originating from surefire
- Produce database connection from datasource, not the driver itself
- Liveness and readiness
#### Done TODO-s:
### Future plans and ambitions
1. A very thin layer above plain JDBC, like QueryDSL?
0. Transaction boundaries by simple "doInTransaction" like patterns, no AOP
0. REST with JSON  and HTTP verb calls from frontend to backend
0. Resilience for downstreams calls (here: From backend to far backend)
0. Prometheus for monitoring
0. Swagger for documentation
0. PACT for contracts between service consumers and consumed services
0. Web components with Javascript or Elm. Continuously assessing
0. Code Complexity analysis (NCSS)
0. OWASP Threats Protection
0. Checkstyle for consistent code layout etc.

### Technologies that will NOT be used
- Spring
- Hibernate and/or JPA or other ORM technologies of very high complexity
- AOP, not even for transaction boundaries

### Useful commands
- Run the pitests:

```
mvn org.pitest:pitest-maven:mutationCoverage
```
- Run pitests on all the applications (Reports: **/target/pit-reports/YYYYMMDDHHmm/index.htmla

```
CLS&mvn -P mutation-tests clean install
```
- Run a complete build

``` 
mvn clean install org.pitest:pitest-maven:mutationCoverage
```
- Make a build site (run from the sub roots)


```
mvn site
```
- Run the application from the command line

```
CLS&java -jar hellostrangeworld-backend/target/hellostrangeworld-backend-0.3.6.jar
```
- Build the application without tests and then run it from the command line

```
CLS&mvn clean install -v -DskipTests=true&java -jar hellostrangeworld-backend/target/hellostrangeworld-backend-0.3.6.jar
```
- Update the parent versions in the children's pom.xml's

```
mvn -N versions:update-child-modules
```
- Run the far backend servletcontainer:

```
CLS&java -ea -cp hellostrangeworld-backend/target/*  net.barakiroth.hellostrangeworld.farbackend.Main
```
- Find ports listened to in Windows

```
netstat -aon | find /i "listening" | find /i "8087"
```
- Run owasp analysis

```
CLS&mvn -X org.owasp:dependency-check-maven:check -P owasp
```
### Useful refs: 

#### Git
[Ecipse/Git: EGit/User Guide](https://wiki.eclipse.org/EGit/User_Guide#The_Preferences_Dialog)</BR>
#### Java 11
[Migrating to Java 11 ](https://blog.codefx.org/java/java-11-migration-guide/)</BR>
[Migrate Maven Projects to Java 11](https://winterbe.com/posts/2018/08/29/migrate-maven-projects-to-java-11-jigsaw/)</BR>
#### Jersey-Jackson
[Producing and consuming JSON or XML in Java REST Services with Jersey and Jackson](https://www.nabisoft.com/tutorials/java-ee/producing-and-consuming-json-or-xml-in-java-rest-services-with-jersey-and-jackson)</BR>
#### Markdown
[Markdown Cheatsheet](https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet#links)</BR>
#### Pi tests/Mutation tests
[Mutation Testing with PITest](https://www.baeldung.com/java-mutation-testing-with-pitest)</BR>
[Real world mutation testing](http://pitest.org/)</BR>
[My PITEST won't run. Coverage generation minion exited abnormally. I need help to configure my pom.xml properly](https://stackoverflow.com/questions/55680025/my-pitest-wont-run-coverage-generation-minion-exited-abnormally-i-need-help-t/55680225#55680225)</BR>
#### RDBMS
[Most fundamental use of H2](https://github.com/h2database/h2database/blob/master/h2/src/test/org/h2/samples/HelloWorld.java)</BR>
#### Testing
[JUnit 5 Tutorial: Writing Assertions With Hamcrest](https://www.petrikainulainen.net/programming/testing/junit-5-tutorial-writing-assertions-with-hamcrest/)</BR>
[JUnit test for System.out.println()
](https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println)</BR>

<!---
[comment]: <> (This is a comment, it will not be included)
[//]: # (
[XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX](WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW)</BR>
[XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX](WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW)</BR>
[XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX](WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW)</BR>
[XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX](WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW)</BR>
[XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX](WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW)</BR>
[XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX](WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW)</BR>
[XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX](WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW)</BR>
[XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX](WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW)</BR>
[XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX](WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW)</BR>
[XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX](WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW)</BR>
[XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX](WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW)</BR>
[XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX](WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW)</BR>
[XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX](WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW)</BR>
)
-->