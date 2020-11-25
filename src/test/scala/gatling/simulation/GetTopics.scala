package gatling.simulation

import io.gatling.http.Predef._
import io.gatling.core.Predef._

object GetTopics {

    val getTopics = exec(http("get all topics").get("v1")
      .check(status.is(200), status.not(404)))

    val specificTopic = exec(http("get specific topics").get("v1/1")
      .check(status.is(200), jsonPath("$.topicName").is("withoutauthor 13")))

    val specificGetTopic = exec(http("get specific topics after doing get all").get("v1")
      .check(status.is(200), jsonPath("$[1].topicId").saveAs("fetchedTopicId")))
      .exec(http("get specific topics").get("v1/${fetchedTopicId}")
        .check(status.is(200), jsonPath("$.topicName").is("withoutauthor 14"))
      .check(bodyString.saveAs("responseBody")))
      .exec{session => println("response body:"+session("responseBody").as[String]); session}


    def getTopicById(topicId:String) = {
        exec(http("get specific topics parameters").get("v1/"+topicId)
          .check(status.is(200)))
    }

}