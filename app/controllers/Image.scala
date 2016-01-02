package controllers

import play.api.mvc.{Action, Controller}

/**
 * Created by Ivan on 23.06.2015.
 */
object Image extends Controller{

  def getImage(table:String, id:String) = Action{
      val intId = id.toInt
      val imageData = table match {
        case "news" => models.Image.newsImage(intId)
        case "glry" => models.Image.galleryImage(intId)
        case "thnl" => models.Image.thumbnailImage(intId)
        case "slsh" => models.Image.slideshowImage(intId)
    }
    val MimeType = "image/jpg"
    Ok(imageData).as(MimeType)
  }
}
