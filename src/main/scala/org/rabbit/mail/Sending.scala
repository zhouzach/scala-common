package org.rabbit.mail

object Sending {


  def main(args: Array[String]): Unit = {

    send a new MailInfo (
      to = "wander669@163.com",
      subject = "scala subject10",
      message = "scala msg10"
    )

    send a new MailInfo (
      to = "wander669@163.com" :: "769087026@qq.com" :: Nil,
      subject = "Our New Strategy (tm1)",
      message = "Please find attach the latest strategy document.",
      richMessage = "Here's the <blink>latest</blink> <strong>Strategy</strong>..."
    )

    send a new MailInfo (
      to = "wander669@163.com" :: "769087026@qq.com" :: Nil,
      subject = "Our 10-year plan",
      message = "Here is the presentation with the stuff we're going to for the next five years.",
      attachment = new java.io.File("/Users/zachzhou/Desktop/myPPt.pptx")
    )

    println("email has been sent.")


  }

}
