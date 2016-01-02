package models

import java.util.Date

import anorm._
import controllers.routes
import org.joda.time.DateTime
import play.api.db.DB
import play.api.Play.current

/**
 * Created by ivanv_000 on 13.06.2015.
 */
case class News(title:String, description:String, link:String, imageId:Int, date:DateTime)

object News
{
  def GetNews() = {
      DB.withConnection { implicit c =>
      val selectGalleries = SQL("Select * from news ")
      selectGalleries().map(row => News(row[String]("title"), row[String]("description"), row[String]("link"),
        row[Int]("id"), new DateTime(row[Date]("date"))) ).sortWith( (n1, n2) => n1.date.isAfter(n2.date)).toList
    }
  }
}