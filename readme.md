# Three layers template hello world like Java application, back end and downstreams microservices with a banal/trivial business logic.

### Business Logic
1. Frontend asks user whom too greet, e.g. "world"
0. Backend is asked for verb to be used in the greeting, e.g. "Hello"
0. A downstream microservice is asked for the adjective to colour the greeting, e.g. "strange"
0. The resulting greeting is presented for the user, e.g. "Hello strange world"

### Environment
- Windows 10
- Java 13
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
- Maven/surefire/site reporting
- Three layers: Frontend, immediate backend and downstreams far backend

#### Summary over technologies/designs so far

|                  | Frontend |  Backend | Far backend |                                                 Note |
| :--------------- | :------: | :------: | :---------: | :--------------------------------------------------- |
| Checkstyle       |    X     |        X |           X |                                     Code layout tool |
| Conf2            |   N/A?   |  NOT YET |           X |                  Easy configuration, properties etc. |
| DoInTransaction  |   N/A    |      N/A |     X |                         Database transaction support |
| Elm | ? | ? | ? | Functional programming GUI programming tool. Alternative to Web Components. Could probably also exist side-by-side with Web Components. |
| FlyWay  |   N/A    |     NOT YET   |      X       |                         Database creation and migration |
| Hamcrest         |    X     |        X |           X |                                            For tests |
| H2               |   N/A    |      N/A |           X |                                   In-memory database |
| JaCoCo           |    X     |        X |           X |                                        Code coverage |
| Java 9+ modules  |   N/A?   |  NOT YET |     NOT YET |                                                      |
| JDBC             |   N/A    |      N/A |     No      |                                                      |
| Jersey           |   N/A    |  NOT YET |           X |                                                      |
| Jetty            |   N/A    |  NOT YET |           X |                                    Servlet container |
| Json deserialize | NOT YET  |        X |         N/A |                       Rest communication data format |
| Json serialize   |   N/A    |      N/A |           X |                       Rest communication data format |
| Junit/Jupiter    |    X     |     X    |      X      |                                         Unit tests |
| Logback          |    X     |        X |           X |                                                      |
| Lombok           |   N/A?   |  NOT YET |           X |          Getters, setters and other boilerplate code |
| NCSS             | Incompat | Incompat |    Incompat |                            Code complexity reporting |
| OWASP            |    X     |        X |           X |             Library security vulnerability reporting |
| PACT             | NOT YET |  NOT YET |     NOT YET | REST server contract testing |
| PiTest           |    X     |        X |           X |                                       Mutation tests |
| Prometheus       |   N/A    |  NOT YET |     X |                            Runtime metrics reporting |
| QueryDSL         |   N/A    |      N/A |           X |                        Thin fluency layer above JDBC |
| Resilience4J     |   N/A    |  NOT YET |         X |               Resilience guard of downstream calls |
| Rest client      | NOT YET  |        X |         N/A |                           |
| Rest server      |   N/A    |  NOT YET |           X |                          Resource API, REST endpoint |
| Separate project | NOT YET  |  NOT YET |     NOT YET | Split parent and children modules for independencies |
| Swagger          |   N/A    |  NOT YET |   X |                               Rest API documentation |
| Vavr          |   N/A    |  NOT YET |     X | Functional programming collection library ++(e.g. tuples) |
| Web components   | NOT YET  |      N/A |         N/A | Frontend browser technology. Alternative to Elm. Could probably also exist side-by-side with Elm. |

#### Detail TODO-s:
- Let backend call far backend using HTTP
- Increase coverage
- Remove overlapping classes when building fat jars
- Liveness and readiness
- Compile all reports to the site directory
- Tidy up all that report/site mess
- Remember to add Prometheus to the datasource as well
- Skip plugin/dependency management in favour of letting mama become a properties bom (bill of materials). Only use management when required to solve transitive dependencies problems.
### Future plans and ambitions
1. REST with JSON  and HTTP verb calls from frontend to backend
0. Web components with Javascript or Elm. Continuously assessing

### Technologies that will NOT be used
- Spring
- Hibernate and/or JPA or other ORM technologies of very high complexity
- AOP, not even for transaction boundaries

### Useful commands
- Run a complete build

``` 
CLS&mvn clean install org.pitest:pitest-maven:mutationCoverage
```
- Run a complete build of the far backend when flyway clean has been run or it has never before been built

``` 
CLS&cd hellostrangeworld-farbackend&mvn clean flyway:migrate install&cd..
```
- Run the pitests:

```
mvn org.pitest:pitest-maven:mutationCoverage
```
- Run pitests on all the applications (Reports: **/target/pit-reports/YYYYMMDDHHmm/index.htmla

```
CLS&mvn -P mutation-tests clean install
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
CLS&java -ea -cp hellostrangeworld-farbackend/target/* net.barakiroth.hellostrangeworld.farbackend.Main
```
- Access the resources

```
http://localhost:8089/api/GreetingDescription
```
or

```
http://localhost:8089/internal/metrics/
```
- Find ports listened to in Windows

```
netstat -aon | find /i "listening" | find /i "8089"
```
-Kill a process (e.g. when locking a port):

```
taskkill /F /PID 8089
```
- Run owasp analysis

```
CLS&mvn -X org.owasp:dependency-check-maven:check -P owasp
```
- Run checkstyle (Report in ./hellostrangeworld/target/site/checkstyle-aggregate.html)

```
CLS&mvn clean install checkstyle:check site -P checkstyle -Dcheckstyle.config.location=google_checks.xml
```
- If hung-up on some FlyWay migration (first change current directory to farbackend):

```
CLS&cd hellostrangeworld-farbackend&mvn flyway:clean&mvn flyway:migrate&cd..
```
### Useful refs: 
#### Flyway
[Documentation](https://flywaydb.org/documentation/)
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
#### Resilience4J
[Guide to Resilience4j](https://www.baeldung.com/resilience4j)</BR>
[Fault tolerance library designed for functional programming](https://github.com/resilience4j/resilience4j#introduction)</BR>

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