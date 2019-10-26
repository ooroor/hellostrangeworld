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
- (Code Complexity analysis (NCSS) INCOMPATIBLE with Java 9+)
- OWASP Threats Protection
- Checkstyle for consistent code layout etc.

#### Summary over technologies/designs so far

|                  | Frontend |  Backend | Far backend |                                                 Note |
| ---------------- | :------: | -------: | ----------: | ---------------------------------------------------: |
| Checkstyle       |    X     |        X |           X |                                     Code layout tool |
| Conf2            |   N/A?   |  NOT YET |           X |                  Easy configuration, properties etc. |
| DoInTransaction  |   N/A    |      N/A |     X |                         Database transaction support |
| FlyWay  |   N/A    |     NOT YET   |      X       |                         Database creation and migration |
| Jupiter          |    X     |     X    |      X      |                                         Junit tests. |
| Hamcrest         |    X     |        X |           X |                                            For tests |
| H2               |   N/A    |      N/A |           X |                                   In-memory database |
| JaCoCo           |    X     |        X |           X |                                        Code coverage |
| Java 9+ modules  |   N/A?   |  NOT YET |     NOT YET |                                                      |
| Jersey           |   N/A    |  NOT YET |           X |                                                      |
| Jetty            |   N/A    |  NOT YET |           X |                                    Servlet container |
| Json deserialize | NOT YET  |        X |         N/A |                       Rest communication data format |
| Json serialize   |   N/A    |      N/A |           X |                       Rest communication data format |
| Logback          |    X     |        X |           X |                                                      |
| Lombok           |   N/A?   |  NOT YET |           X |          Getters, setters and other boilerplate code |
| NCSS             | Incompat | Incompat |    Incompat |                            Code complexity reporting |
| OWASP            |    X     |        X |           X |             Library security vulnerability reporting |
| PACT             |   N/A    |  NOT YET |     NOT YET |                                   Consumer contracts |
| PiTest           |    X     |        X |           X |                                       Mutation tests |
| Prometheus       |   N/A    |  NOT YET |     NOT YET |                            Runtime metrics reporting |
| QueryDSL         |   N/A    |      N/A |           X |                        Thin fluency layer above JDBC |
| Resilience4J     |   N/A    |  NOT YET |         N/A |               Resilience guard of downstream calls |
| Rest client      | NOT YET  |        X |         N/A |                                         Resource API |
| Rest server      |   N/A    |  NOT YET |           X |                                         Resource API |
| Separate project | NOT YET  |  NOT YET |     NOT YET | Split parent and children modules for independencies |
| Swagger          |   N/A    |  NOT YET |     NOT YET |                               Rest API documentation |
| Web components   | NOT YET  |      N/A |         N/A |                          Frontend browser technology |

#### Detail TODO-s:
- Stop logging SQL errors when selecting from not existing schema/table
- Remove overlapping classes when building fat jars
- Remove JANSI exception logging originating from surefire
- Produce database connection from datasource, not the driver itself
- Liveness and readiness
- Compile all reports to the site directory
- Tidy up all that report/site mess, please...
- Remember to add Prometheus to the datasource as well
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
- Run checkstyle (Report in ./hellostrangeworld/target/site/checkstyle-aggregate.html)

```
CLS&mvn clean install checkstyle:check site -P checkstyle -Dcheckstyle.config.location=google_checks.xml
```
- If hung-up on some FlyWay migration:

```
CLS&mvn flyway:clean
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
[comment]: <> "This is a comment, it will not be included"
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