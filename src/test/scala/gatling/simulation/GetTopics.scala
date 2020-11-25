package gatling.simulation

import io.gatling.http.Predef._
import io.gatling.core.Predef._

object GetTopics {

    val getTopics = exec(http("get all topics").get("v1")
      .check(status.is(200)))

    val specificTopic = exec(http("get specific topics").get("v1/1")
      .check(status.is(200)))
}