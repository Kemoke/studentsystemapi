package controllers;

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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;

public class NewsController extends Controller {
    private final WSClient wsClient;

    @Inject
    public NewsController(WSClient wsClient) {
        this.wsClient = wsClient;
    }

    @BodyParser.Of(BodyParser.Text.class)
    public CompletionStage<Result> getArticle() {
        String uri = request().body().asText();
        WSRequest request = wsClient.url(uri);
        return request.get().thenApply(wsResponse -> {
            try {
                Document document = Jsoup.parse(new String(wsResponse.getBody().getBytes("ISO-8859-1"), "UTF-8"));
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
            } catch (UnsupportedEncodingException e) {
                return internalServerError(Arrays.toString(e.getStackTrace()));
            }
        });
    }

    public CompletionStage<Result> newsList(){
        WSRequest request = wsClient.url("https://news.ius.edu.ba/news");
        return request.get().thenApply(wsResponse -> {
            try {
                Document document = Jsoup.parse(new String(wsResponse.getBody().getBytes("ISO-8859-1"), "UTF-8"));
                Elements news = document.select("div[id^=article]");
                List<Article> articleList = new ArrayList<>();
                for (Element article : news) {
                    Element title = article.select(".node-title > a").first();
                    Element image = article.select("img").first();
                    Element content = article.select(".node-content").first().child(0).child(0).child(0);
                    Article newArticle = new Article();
                    newArticle.imgUrl = image.attr("src");
                    newArticle.link = "https://news.ius.edu.ba" + title.attr("href");
                    newArticle.text = content.text();
                    newArticle.title = title.text();
                    articleList.add(newArticle);
                }
                return ok(Json.toJson(articleList));
            } catch (UnsupportedEncodingException e) {
                return internalServerError(Arrays.toString(e.getStackTrace()));
            }
        });
    }
}
