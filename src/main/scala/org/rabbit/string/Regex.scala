package org.rabbit.string

import java.util.regex.Pattern

object Regex {

  /**
    *
    * https://stackoverflow.com/questions/3481828/how-to-split-a-string-in-java
    *
    * there are 12 characters with special meanings: the backslash \, the caret ^,
    * the dollar sign $, the period or dot ., the vertical bar or pipe symbol |, the question mark ?,
    * the asterisk or star *, the plus sign +, the opening parenthesis (, the closing parenthesis ),
    * and the opening square bracket [, the opening curly brace {,
    * These special characters are often called "metacharacters".
    * @param str
    * @return
    */
  def split(str: String) ={
    str.split(Pattern.quote("."))
  }

  def main(args: Array[String]): Unit = {
    split("db.table")
      .foreach(println(_))
  }

}
