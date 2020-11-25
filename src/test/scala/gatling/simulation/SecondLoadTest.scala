package gatling.simulation

import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import io.gatling.core.Predef._
import GetTopics._

class SecondLoadTest extends Simulation {

  val httpConf = http.baseURL("http://localhost:8081/topics/")
    .header("Accept", "application/json")

  val scn = scenario("my second load test")
      .exec(GetTopics.getTopics).pause(5)

    .exec(GetTopics.specificTopic).pause(10,15)


  setUp(
    scn.inject(atOnceUsers(3))
      .protocols(httpConf)
  )



}