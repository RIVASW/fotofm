package controllers


import models._
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    val slshImgIds = SlideShow.imageIdList("main")
    Ok(views.html.index("Romanyuk Natalya", NavBar.NavItems, "index.html")(slshImgIds, views.html.news(News.GetNews())) )
  }
  def section(name:String) = Action{
    val page = Section.GetSectionByName(name)
    var galleries = Gallery.GetGalleriesDescrBySection(name)
    Ok(views.html.index(page.title, NavBar.NavItems, name)(page.slshImgIds,views.html.galleryIndex(galleries)) )
  }

  def gallery(secName:String, galName:String) = Action{
    val gallery =   Gallery.GetGallery(secName, galName)
    val metaTag = "<meta property=\"og:image\" content=\"http://fotofm.ru/images/glry/" + gallery.imgIds(0).toString + "\"/>"
    Ok(views.html.gallery(gallery.title, NavBar.NavItems, secName, metaTag)(gallery.description, gallery.imgIds))
  }

  def static(pageName: String) = Action{
    val page = StaticPage.GetPage(pageName.split('.')(0))
    Ok(views.html.staticPage(page.title, NavBar.NavItems, pageName)(page.content))
  }

}