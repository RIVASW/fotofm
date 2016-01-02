package models

import java.util.Date

import anorm._
import play.api.Play.current
import play.api.db.DB

/**
 * Created by Ivan on 23.06.2015.
 */
object SlideShow {
  def imageIdList(name:String):List[Int] = {
   DB.withConnection { implicit c =>
      val slshid = SQL("Select id from contpages Where name = {name}").on("name" -> name)().map(row => row[Int]("id")).head
      SQL("Select id, position from slshimages Where cntpgid = {slshid}").on("slshid" -> slshid)().map( row => (row[Int]("id"),
        row[Int]("position"))).sortWith( (n1, n2) => n1._2 < n2._2).map(_._1).toList
    }
  }
}
