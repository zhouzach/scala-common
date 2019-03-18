package org.rabbit.utils

import com.alibaba.fastjson.JSON
import com.squareup.moshi.{Moshi, Types}
import okhttp3._
import org.rabbit.models.SnappyJobResult
//import org.rabbit.models.SnappyJobResult

object HttpClient {

  def get(url: String): String= {
    val builder = new Request.Builder()
    val request = builder.url(url).build()

    execute(request)
  }

  private def execute(request: Request): String = {
    val client = new OkHttpClient
    var response: Response = null
    try {
      response = client.newCall(request).execute
      response.body.string()
    } catch {
      case e =>
        //        throw e
        e.getMessage
//        null
    }
//    finally if (response != null) response.close()

//    OkHttpClientHttpResponse r = new OkHttpClientHttpResponse
  }

  //  @throws[IOException]
//  def post(url: String, json: String): ResponseBody = {
//    val builder = new Request.Builder()
//
//    val JSON = MediaType.get("application/json; charset=utf-8");
//    val body = RequestBody.create(JSON, json)
//    val request = builder.url(url).post(body).build()
//
//    execute(request)
//  }


 case class SnappyJobResult ( jobId: String,
                               status: String
//                               result: String
                             )


  def main(args: Array[String]): Unit = {

//    val  moshi = new Moshi.Builder().build();
//    val jsonAdapter = moshi.adapter(SnappyJobResult.getClass)
    val jsonStr = get("http://172.16.9.220:7070/snappy/job/get/job/result/7902dda7-e15c-4b24-8cad-3e804726ee01")

    val json = "{\"jobId\":\"7902dda7-e15c-4b24-8cad-3e804726ee01\",\"status\":\"FINISHED\"}"

    val o = JSON.parseObject(json, SnappyJobResult.getClass).asInstanceOf[SnappyJobResult]
//    val b = jsonAdapter.fromJson(res1)
    println(jsonStr)
    println("*******")
    println(o.jobId)
    println(o.status)
//        println(b)
//    println(res1.toString)
    println("----")
//    println(res1.string())
    println("=======")

//    val postUrl = "http://172.16.9.220:9090/frd/report/history"
//
//    val json =
//      """
//        |{
//        |"pluginId":"1",
//        |"pSize":"30",
//        |"cPage":"1",
//        |"sort":"",
//        |"cert_type":""
//        |}
//        |
//        |
//        |
//      """.stripMargin
//
//    val res = post(postUrl, json)
//    println(res)
  }

}

//class Job{
//
//  val jobId: String
//
//}