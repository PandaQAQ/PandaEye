package com.pandaq.pandaeye.utils.webview;

import android.util.Log;

import com.pandaq.pandaeye.modules.news.newsdetail.NewsContent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by PandaQ on 2016/9/13.
 * email : 767807368@qq.com
 * webView加载html的帮助类
 */
public class WebUtils {

    public static final String BASE_URL = "file:///android_asset/";
    public static final String MIME_TYPE = "text/html";
    public static final String ENCODING = "utf-8";
    public static final String FAIL_URL = "http://daily.zhihu.com/";

    private static final String HTML_CORE = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "\t<title>test</title>\n" +
            "\t<script type=\"text/javascript\" src=\"file:///android_asset/clickPicture.js\">\n" +
            "\t</script>\n" +
            "</head>\n" +
            "<body>\n" +
            "\tbody_holder\n" +
            "</body>\n" +
            "</html>";
    private static final String CSS_LINK_PATTERN = " <link href=\"%s\" type=\"text/css\" rel=\"stylesheet\" />";
    private static final String NIGHT_DIV_TAG_START = "<div class=\"night\">";
    private static final String NIGHT_DIV_TAG_END = "</div>";

    private static final String DIV_IMAGE_PLACE_HOLDER = "class=\"img-place-holder\"";
    private static final String DIV_IMAGE_PLACE_HOLDER_IGNORED = "class=\"img-place-holder-ignored\"";

    public static String buildHtmlWithCss(String html, String[] cssUrls, boolean isNightMode) {
        StringBuilder result = new StringBuilder();
        for (String cssUrl : cssUrls) {
            result.append(String.format(CSS_LINK_PATTERN, cssUrl));
        }
        if (isNightMode) {
            result.append(NIGHT_DIV_TAG_START);
        }
        result.append(html.replace(DIV_IMAGE_PLACE_HOLDER, DIV_IMAGE_PLACE_HOLDER_IGNORED));
        if (isNightMode) {
            result.append(NIGHT_DIV_TAG_END);
        }
        return HTML_CORE.replace("body_holder", result);
    }

    public static String buildHtmlForIt(String content, boolean isNightMode) {
        StringBuilder modifiedHtml = new StringBuilder();
        modifiedHtml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.0//EN\" \"http://www.wapforum.org/DTD/xhtml-mobile10.dtd\">"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">" + "<head>" + "<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\"/>"
                + "<meta http-equiv=\"Cache-control\" content=\"public\" />" + "<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,user-scalable=no,minimum-scale=1.0,maximum-scale=1.0\" />"
                + "<link rel=\"stylesheet\" href=\"file:///android_asset/news.css\" type=\"text/css\">"
                + "</head>");
        modifiedHtml.append("<body ");
        if (isNightMode) {
            modifiedHtml.append("class=\'night\'");
        }
        modifiedHtml.append(">");
        modifiedHtml.append(content);
        modifiedHtml.append("</body></html>");
        return modifiedHtml.toString();
    }

    public static String newsInsertPic(NewsContent newsContent) {
        String result = newsContent.getBody().replaceAll("　　", "");
        List<NewsContent.ImgBean> imgBeen = newsContent.getImg();
        for (int i = 0; i < imgBeen.size(); i++) {
            if (i != 0) {
                String ref = imgBeen.get(i).getRef();
                String url = imgBeen.get(i).getSrc();
                result = result.replaceAll(ref, getImageBody(url));
            }
        }
        return result;
    }

    private static String getImageBody(String url) {
        return "<img src = " +
                url +
                " width = 100%" +
                " height = 100%"
                + "/>"
                + "\n";
    }

    /**
     * 从 HTML 中获取所有的图片链接
     *
     * @param html 待解析的 HTML 代码
     * @return 正则匹配到的图片链接
     */
    public static ArrayList<String> getImageUrlsFromHtml(String html) {
        ArrayList<String> imageSrcList = new ArrayList<>();
        Pattern p = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(html);
        String quote;
        String src;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("//s+")[0] : m.group(2);
            imageSrcList.add(src);
        }
        if (imageSrcList.size() == 0) {
            Log.e("imageSrcList", "资讯中未匹配到图片链接");
            return null;
        }
        return imageSrcList;
    }
}
