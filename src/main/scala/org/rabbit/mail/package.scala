package org.rabbit

import org.apache.commons.mail._
import org.rabbit.config.FileConfig

/**
 * https://gist.github.com/mariussoutier/3436111
 */
package object mail {

  implicit def stringToSeq(single: String): Seq[String] = Seq(single)

  implicit def liftToOption[T](t: T): Option[T] = Some(t)

  sealed abstract class MailType

  case object Plain extends MailType

  case object Rich extends MailType

  case object MultiPart extends MailType

  case class MailInfo(
                       to: Seq[String],
                       cc: Seq[String] = Seq.empty, //抄送地址
                       bcc: Seq[String] = Seq.empty, //密件抄送地址
                       subject: String,
                       message: String,
                       richMessage: Option[String] = None,
                       attachment: Option[(java.io.File)] = None
                     )

  object send {
    def a(emailInfo: MailInfo) {

      val format =
        if (emailInfo.attachment.isDefined) MultiPart
        else if (emailInfo.richMessage.isDefined) Rich
        else Plain

      val email: Email = format match {
        case Plain => new SimpleEmail().setMsg(emailInfo.message)
        case Rich => new HtmlEmail().setHtmlMsg(emailInfo.richMessage.get).setTextMsg(emailInfo.message)
        case MultiPart => {
          val attachment = new EmailAttachment()
          attachment.setPath(emailInfo.attachment.get.getAbsolutePath)
          attachment.setDisposition(EmailAttachment.ATTACHMENT)
          attachment.setName(emailInfo.attachment.get.getName)
          new MultiPartEmail().attach(attachment).setMsg(emailInfo.message)
        }
      }

      val hostName = FileConfig.emailConfig.getString("hostName")
      val userName = FileConfig.emailConfig.getString("userName")
      val password = FileConfig.emailConfig.getString("password")
      email.setHostName(hostName)
      email.setAuthentication(userName, password)
      email.setFrom(userName)
      email.setCharset("UTF-8")
      email.setSSLOnConnect(true)

      emailInfo.to foreach email.addTo

      email
        .setSubject(emailInfo.subject)
        .setMsg(emailInfo.message)
        .send()
    }
  }

}
