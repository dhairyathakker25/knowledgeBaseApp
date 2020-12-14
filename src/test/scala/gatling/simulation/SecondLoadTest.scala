package gatling.simulation

import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import io.gatling.core.Predef._
import GetTopics._


class SecondLoadTest extends Simulation {

  val httpConf = http.baseURL("http://localhost:8081/topics/")
    .header("Accept", "application/json")

  val users = System.getenv("users").toInt

  val scn = scenario("my second load test")
    .repeat(10)
    {
         exec(GetTopics.getTopics).pause(5)
        .exec(GetTopics.specificGetTopic).pause(10,15)
        .exec(GetTopics.getTopicById("1"))
        .exec(GetTopics.getTopicByCsvFeeder())

    }


  setUp(
    scn.inject(atOnceUsers(users))
      .protocols(httpConf)
  )

}