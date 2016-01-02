package models

import java.util.Date

import anorm._
import org.joda.time.DateTime
import play.api.Play.current
import play.api.db.DB

/**
 * Created by Ivan on 13.07.2015.
 */
case class StaticPage(title:String, content:String)

object StaticPage {
  def GetPage(pageName:String) = {
    DB.withConnection { implicit c =>
      var page = SQL("Select * from staticpages Where name = {name}").on("name" -> pageName)().
        map( row => (row[String]("title"), row[String]("content"))).toList.head
      StaticPage(page._1, page._2)
    }
  }
}
