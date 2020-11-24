package gatling.simulation

import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import io.gatling.core.Predef._

class FirstLoadTest extends Simulation {

  val httpConf = http.baseURL("http://localhost:8081/topics/")
    .header("Accept", "application/json")

  val scn = scenario("my first load test")
    .exec(http("get all topics").get("v1")
      .check(status.is(200)))

  setUp(
    scn.inject(atOnceUsers(3))
      .protocols(httpConf)
  )



}