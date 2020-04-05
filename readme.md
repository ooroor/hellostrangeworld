# Three layers template hello world like Java application, back end and downstream microservices with a banal/trivial business logic.

### Business Logic
1. The frontend asks the user whom too greet, and the user answers e.g. ```"world"```.
0. The frontend does a HTTP call to the backend microservice asking for a verb and an adjective to be used in the greeting, e.g. ```"Hello strange"```.
0. The backend does a downstream HTTP call to the far backend microservice asking for an adjective with which to colour the greeting, e.g. ```"strange"```.
0. The frontend presents to the user the resulting greeting , e.g. ```"Hello strange world"```.

### Terms
| Term             |Description| Example |
| :--------------- | :----- | :----- |
|```Greeting``` | |"Hello, strange world!" or "Goodnight, somewhat sleepy dog!"|
|```Initial part``` | |"Hello, strange" or "Goodbye, very dark"|
|```Interjection``` | |"Hello" or "Hi"|
|```Modifier``` | |"strange" or "immensely weird"|
|```Greetee``` | |"world" or "human being"|
|```Frontend``` |Prompts the user for the greetee, calls downstream to get the rest of the greeting and presents the result| |
|```Backend``` |Calls downstream to the far backend to get the modifier and concatenates it to produce the initial part of the greeting| |
|```Far backend``` |Queries an RDBMS to get a random modifier| |

### Environment
- Windows 10
- Java 13
- Maven 3.6.2

### Inspirations
- Simplicity
- Write highly testable classes
- High coverage
- Not using overkill technologies and frameworks
- Decoupling
- No superfluous, silly or evident commenting
- Self explanatory code, using descriptive and precise words for method names, variables etc..
- Kill checked exceptions as fast as they are caught, don't rethrow them.
- When relevant and possible, prefer return codes to exceptions to communicate exceptional flow between classes

### How to use
- This is NOT a library
- This is NOT a framework
- This is NOT a utility repo
- This does not solve any business problems for you
- It is meant as an offer from which you can pick the most appropriate module(s) and code from a working full-fled three layer microservice-based app with a business logic as simple as you can imagine
- It covers many of the technologies often called upon in such an environment, like e.g. REST, json, resilience, swagger, database, ...

### Tools and technologies used so far
- Maven/surefire/site reporting
- Three layers: Frontend, backend and downstream far backend

#### Summary over technologies/designs so far

|                  | Frontend |  Backend | Far backend |                                                 Note |
| :--------------- | :------: | :------: | :---------: | :--------------------------------------------------- |
| ArchUnit   | NOT YET  |       NOT YET |          NOT YET | Architecture checker |
| AssertJ | X | X | X | Fluent test layer above Jupiter |
| Checkstyle       |    X     |        X |           X |                                     Code layout tool |
| Conf2            |   X   |  X |           X |                  Easy configuration, properties etc. |
| doInTransaction |   N/A    |      N/A |     X |                   Database transaction pattern |
| Docker | NOT YET | NOT YET | NOT YET | Self contained (os + app + ...) virtualized application container |
| Elm | ? | N/A | N/A | Functional programming GUI programming tool. Alternative to Web Components. Could probably also exist side-by-side with Web Components. |
| FlyWay  |   N/A    |  N/A   |      X       |                         Database creation and migration |
| H2               |   N/A    |      N/A |           X |                                   In-memory database |
| JaCoCo           |    X     |        X |           X |                                        Code coverage |
| Java 9+ modules  |   N/A?   |  NOT YET |     NOT YET |                                                      |
| JDBC             |   N/A    |      N/A |     No      |                                                      |
| Jersey           |   N/A    |  X |           X |                                                      |
| Jetty            |   N/A    |  X |           X |                                    Servlet container |
| Json deserialize | X |        X |  In test |                       Rest communication data format |
| Json serialize   |   N/A    |      N/A |           X |                       Rest communication data format |
| Junit/Jupiter    |    X     |     X    |      X      |                                         Unit tests |
| Logback          |    X     |        X |           X |                                                      |
| Lombok           |   X   |  X |           X |          Getters, setters and other boilerplate code |
| NCSS             | Incompat. | Incompat. |   Incompat. |                            Code complexity reporting |
| OWASP            |    X     |        X |           X |             Library security vulnerability reporting |
| PACT             | NOT YET |  NOT YET |     NOT YET | REST server contract testing |
| PiTest           |    X     |        X |           X |                                       Mutation tests |
| Prometheus       |   N/A    | X |     X |                            Runtime metrics reporting |
| QueryDSL         |   N/A    |      N/A |           X |                        Thin fluency layer above JDBC |
| Resilience4J     |   N/A    |  NOT YET |         X |               Resilience guard of downstream calls |
| RestAssured | N/A | NOT YET | NOT YET | For testing an application's REST endpoint(s) |
| Rest client      | NOT YET  |        X | In test |                           |
| Rest server      |   N/A    |  X |           X |                          Resource API, REST endpoint |
| Independent project | X  |  X |     X | Split parent and children modules for independencies |
| Swagger          |   N/A    |  NOT YET |   X |                               Rest API documentation |
| Vavr          |   N/A    |  NOT YET |     X | Functional programming collection library etc. (e.g. tuples) |
| WireMock | X | X | N/A | Stubs an endpoint being called by some method |
| Web components   | NOT YET  |      N/A |         N/A | Frontend browser technology. Alternative to Elm. Could probably also exist side-by-side with Elm. |

#### Detail TODO-s:
- Tidy up the poms!
- Increase coverage in far backend
- Remove overlapping classes when building fat jars
- Liveness and readiness
- Compile all reports to the site directory
- Tidy up all that report/site mess
- Remember to add Prometheus to the data source as well
- Remember  to add Prometheus to the logback configuration as well
- Skip plugin/dependency management in favour of letting mama become a properties bom (bill of materials). Only use management when required to solve transitive dependencies problems.
- Config injection
- Consistent naming
- Remove exception rethrows
- Move more common stuff to the common library
- Error page upon HTTP errors
- Docker

### Future plans and ambitions
- Make a Maven archetype of it

### Technologies that will NOT be used
- Spring
- Hibernate and/or JPA or other ORM technologies of very high complexity
- AOP, not even for transaction boundaries

### Run the far backend
- Start the servlet container running the far backend application: ```java -jar hellostrangeworld-farbackend\target\hellostrangeworld-farbackend.jar```
- In the browser, access: ```http://localhost:8099/api/Modifier```
- Refresh the browser to get different database entries

### Run the backend
- Run the far backend as described above
- Start the servlet container running the backend application: ```java -jar hellostrangeworld-backend\target\hellostrangeworld-backend.jar```
- In the browser, access: ```http://localhost:8089/api/InitialPart```
- Refresh the browser to get different database entries

### Run the frontend
- Run the far backend as described above
- Run the backend as described above
- Run the frontend: ```java -jar hellostrangeworld-frontend\target\hellostrangeworld-frontend.jar```

### Useful commands
- Run a complete build

``` 
mvn clean install org.pitest:pitest-maven:mutationCoverage
```
- Run a complete build of the far backend when flyway clean has been run or it has never before been built

``` 
cd hellostrangeworld-farbackend&mvn clean flyway:migrate install&cd..
```
- Run the pi tests:

```
mvn org.pitest:pitest-maven:mutationCoverage
```
- Run pi tests on all the applications (Reports: **/target/pit-reports/YYYYMMDDHHmm/index.html)

```
CLS&mvn -P mutation-tests clean install
```
- Make a build site (run from the sub roots)

```
mvn site
```
- Build the application without tests

```
CLS&mvn clean install -v -DskipTests=true
```
- Run the far backend servlet container:

```
java -ea -cp hellostrangeworld-farbackend/target/* net.barakiroth.hellostrangeworld.farbackend.Main
```
- Access the metrics

```
http://localhost:8099/internal/metrics/ 
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
mvn -X org.owasp:dependency-check-maven:check -P owasp
```
- Run checkstyle (Report in ./hellostrangeworld/target/site/checkstyle-aggregate.html)

```
mvn clean install checkstyle:check site -P checkstyle -Dcheckstyle.config.location=google_checks.xml
```
- If hung-up on some FlyWay migration (first change current directory to far backend):

```
cd hellostrangeworld-farbackend&mvn flyway:clean&mvn flyway:migrate&cd..
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