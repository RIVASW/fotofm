package models

import java.util.Date

import anorm._
import controllers.routes
import play.api.Play.current
import play.api.db.DB
import play.api.templates.Html


/**
 * Created by ivanv_000 on 03.06.2015.
 */
case class Section(title:String, slshImgIds:List[Int])

object Section {

  def GetSectionByName(name:String) = {
    val cleanName = name.split('.')(0);

    val title = DB.withConnection { implicit c =>
      val selectGalleries = SQL("Select title from contpages Where name = {name}").on("name" -> cleanName)
      selectGalleries().map(row => (row[String]("title"))).toList.head
    }

    val slshImgIds = SlideShow.imageIdList(cleanName)

    Section(title, slshImgIds)
  }
}
