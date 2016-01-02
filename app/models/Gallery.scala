package models

import java.util.Date

import controllers.routes
import org.joda.time.DateTime
import play.api.db.DB
import anorm._
import play.api.Play.current
import play.api.templates.Html

/**
 * Created by ivanv_000 on 13.06.2015.
 */

case class GalleryDescription(title:String, thumbnailId:Int, name:String, date:DateTime )
case class Gallery (title:String, description:String, date:DateTime, imgIds:List[Int])

object Gallery{

  def GetGalleriesDescrBySection(sectionName:String):List[GalleryDescription] = {

    var cleanSectionName = sectionName.split('.')(0)

    case class DbGallery(name:String, tittle:String, description:String, date:Date)
    DB.withConnection { implicit c =>
      var cntpgId = SQL("Select id from contpages Where name = {name}").on("name" -> cleanSectionName)().map(_[Int]("id")).toList.head
      val selectGalleries = SQL("Select id, title, name, date from galleries Where cntpgid = {id}").on("id" -> cntpgId)
      selectGalleries().map(row => GalleryDescription(row[String]("title"), row[Int]("id"),
        cleanSectionName + "/" +row[String]("name") + ".html", new DateTime(row[Date]("date"))) ).toList
    }
  }

  def GetGallery(sectionName:String, galleryName:String):Gallery =   {

    var cleanGalleryName = galleryName.split('.')(0)

    DB.withConnection { implicit c =>
      var cntpgId = SQL("Select * from contpages Where name = {name}").on("name" -> sectionName)().map(_[Int]("id")).toList.head
      val selectGallery = SQL("Select title, description, date, id from galleries Where cntpgId = {cntpgId} and name = {name}").
        on("cntpgId" -> cntpgId, "name" -> cleanGalleryName)
      val descr = selectGallery().map(row => (row[String]("title"), row[String]("description"), row[Date]("date"), row[Int]("id")) ).toList.head
      val imgIds = SQL("Select id, position from glryimages Where glryid = {glryid}").on("glryid" -> descr._4)().map( row => (row[Int]("id"),
        row[Int]("position"))).sortWith( (n1, n2) => n1._2 < n2._2).map(_._1).toList
      Gallery(descr._1, descr._2, new DateTime(descr._3), imgIds)
    }
  }
}
