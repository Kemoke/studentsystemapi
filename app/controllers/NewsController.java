package controllers;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.TopLevelWindow;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.FrameWindow;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import util.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;

public class NewsController extends Controller {
    private final WebClient webClient;

    @Inject
    public NewsController(WebClient webClient) {
        this.webClient = webClient;
    }

    @BodyParser.Of(BodyParser.Text.class)
    public Result getArticle() {
        String uri = request().body().asText();
        try {
            final HtmlPage page = webClient.getPage(uri);
            Document document = Jsoup.parse(page.asXml());
            String title = document.select(".node-title > a").text();
            Elements content = document.select(".node-content").first().child(0).child(0).child(0).children();
            String text = "";
            for (Element paragraph : content) {
                text += paragraph.text() + '\n';
            }
            Article article = new Article();
            article.title = title;
            article.text = text;
            return ok(Json.toJson(article));
        } catch (IOException e) {
            return internalServerError(Arrays.toString(e.getStackTrace()));
        }
    }

    public Result newsList(){
        try {
            final HtmlPage page = webClient.getPage("https://news.ius.edu.ba/news");
            Document document = Jsoup.parse(page.asXml());
            Elements news = document.select("div[id^=article]");
            List<Article> articleList = new ArrayList<>();
            for (Element article : news) {
                Element title = article.select(".node-title > a").first();
                Element image = article.select("img").first();
                Element content = article.select(".node-content").first().child(0).child(0).child(0);
                Article newArticle = new Article();
                newArticle.link = "https://news.ius.edu.ba" + title.attr("href");
                newArticle.imgUri = image.attr("data-src");
                newArticle.text = content.text();
                newArticle.title = title.text();
                articleList.add(newArticle);
            }
            return ok(Json.toJson(articleList));
        } catch (IOException e) {
            return internalServerError(Arrays.toString(e.getStackTrace()));
        }
    }
}
