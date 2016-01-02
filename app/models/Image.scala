package models

import java.util.Date

import anorm._
import play.api.db.DB
import play.api.Play.current

/**
 * Created by Ivan on 23.06.2015.
 */
object Image {
  def newsImage(id:Int): Array[Byte] ={
   getImageFromDb("news", "image", id)
  }

  def galleryImage(id:Int): Array[Byte] ={
    getImageFromDb("glryimages", "image", id)
  }

  def thumbnailImage(id:Int): Array[Byte] ={
    getImageFromDb("galleries", "thumbnail", id)
  }

  def slideshowImage(id:Int): Array[Byte] ={
    getImageFromDb("slshimages", "image", id)
  }

  private def getImageFromDb(table:String, field:String, id:Int): Array[Byte] ={
    DB.withConnection { implicit c =>

      implicit def rowToByteArray: Column[Array[Byte]] = {
        Column.nonNull[Array[Byte]] { (value, meta) =>
          val MetaDataItem(qualified, nullable, clazz) = meta
          value match {
            case bytes: Array[Byte] => Right(bytes)
            case _ => Left(TypeDoesNotMatch("..."))
          }
        }
      }

      val image = SQL("Select " + field + " from " + table +" Where id = {id}").on("id" -> id)
      image().map(row => row[Array[Byte]](field) ).head
    }
  }
}
