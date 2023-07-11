package bot.tsioupis.news;


import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

import org.jsoup.select.Elements;


import javax.print.Doc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class kerkida {

    private String url;
    private final MessageChannel channel;
    private String currentArticle;
    Document ApollonPage;
    public kerkida(MessageChannel channel){
        this.channel=channel;
        this.currentArticle="";
        this.url="https://www.kerkida.net/eidiseis/a-katigoria/apollonas";
        try {
            this.ApollonPage=Jsoup.connect(this.url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkForNewArticle(){
        Date date=new Date();
        this.url="https://www.kerkida.net/eidiseis/a-katigoria/apollonas";
        try {
            this.ApollonPage=Jsoup.connect(this.url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Element new_article = this.ApollonPage.select("div.col-md-6.article-item").first();
        Element href = new_article.selectFirst("a");
        String new_link = href.attr("abs:href");
        if (this.currentArticle.equals(new_link)) {
            System.out.println(java.time.LocalDateTime.now()+":    "+this.currentArticle+"      "+new_link);
            return false; //Not a new article
        }
        else this.currentArticle = new_link;
        return true;
    }

    private String getTitle(Document page){
        String title="__***"+page.select("h1.page-header").text()+"***__";
        return title;
    }

    private String getTime(Document page){
        String time="*"+page.select("time").text()+"*";
        return time;
    }

    private String getFrontImage(Document page){
        String img=page.select("div.content").select("img").first().absUrl("src");;
        return img;
    }

    private String getArticleContent(Document page){
        String articleText;
        Elements content = page.select("div.paragraph.paragraph--type--text.paragraph--view-mode--default");
        StringBuilder text = new StringBuilder();

        outer: for (Element paragraph : content.select("p")) {
            inner:  for (Element child : paragraph.children()){
                        if (child.hasAttr("href")) continue outer;
                    }

                    for (Node sentence : paragraph.childNodes()) {
                        if (sentence.normalName().equals("strong") || sentence.normalName().equals("b")
                                || sentence.normalName().equals("i") || sentence.normalName().equals("u")) {
                            Element modifiedText=(Element) sentence;
                            text.append("**"+modifiedText.text()+"**");
                        }
                        else if (sentence instanceof TextNode)text.append(((TextNode)sentence).text());
                    }
                    text.append("\n");
                }
        articleText=text.toString().replace("&nbsp;"," ").replace("<br>","");
        return articleText;
    }

    public void sendMessage() {
        if (checkForNewArticle() == false) return;

        try {
            Document page = Jsoup.connect(this.currentArticle).get();
            this.channel.sendMessage(getTitle(page)).queue();
            this.channel.sendMessage(getTime(page)).queue();
            this.channel.sendMessage(getArticleContent(page)).queue();
            this.channel.sendMessage(getFrontImage(page)).queue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException ex) {
            this.channel.sendMessage(this.currentArticle).queue();
            ex.printStackTrace();
        }
    }
}