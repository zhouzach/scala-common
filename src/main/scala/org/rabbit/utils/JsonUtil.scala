package org.rabbit.utils

import com.google.gson.Gson

object JsonUtil {

  def main(args: Array[String]): Unit = {

    val gson = new Gson
    val str =gson.toJson(Array("1","2"))
    println(str)
  }

}

