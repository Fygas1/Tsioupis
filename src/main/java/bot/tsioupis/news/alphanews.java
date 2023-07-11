package bot.tsioupis.news;


import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;

public class alphanews {

    private String url;
    private final MessageChannel channel;
    private String currentArticle;
    Document NewsPage;
    public alphanews(MessageChannel channel){
        this.channel=channel;
        this.currentArticle="";
        this.url="https://www.alphanews.live/cyprus";
        try {
            this.NewsPage=Jsoup.connect(this.url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkForNewArticle(){
        Date date=new Date();
        this.url="https://www.alphanews.live/cyprus";
        try {
            this.NewsPage=Jsoup.connect(this.url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Element new_article = this.NewsPage.select("div.views-row.subgategories-top-main").first();
        Element href = new_article.selectFirst("a");
        String new_link = href.attr("abs:href");
        if (this.currentArticle.equals(new_link)) {
            System.out.println(java.time.LocalDateTime.now()+":    "+this.currentArticle+"      "+new_link);
            return false; //Not a new article
        }
        else this.currentArticle = new_link;
        System.out.println(currentArticle);
        return true;
    }

    private String getTitle(Document page){
        String title="__***"+page.select("span.field.field--name-title.field--type-string.field--label-hidden").text()+"***__";
        return title;
    }

    private String getTime(Document page){
        String time="*"+page.select("div.article-subheader-head").text()+"*";
        return time;
    }

    private String getFrontImage(Document page){
        String img=page.select("div.field.field--name-field-main-image.field--type-entity-reference.field--label-hidden.field__item").select("img").first().absUrl("src");;
        return img;
    }

    private String getArticleContent(Document page){
        String articleText;
        Elements content = page.select("div.clearfix.text-formatted.field.field--name-field-text.field--type-text-long.field--label-hidden.field__item");
        StringBuilder text = new StringBuilder();

        outer: for (Element paragraph : content) {      //for each paragraph
            inner:  for (Element child : paragraph.children()){          //for links or attributes
                        if (child.hasAttr("href")) continue outer;
                    }

                    for (Node sentence : paragraph.childNodes()) {      //for each individual text in code
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