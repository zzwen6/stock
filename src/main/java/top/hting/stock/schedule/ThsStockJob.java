package top.hting.stock.schedule;

import cn.hutool.core.util.StrUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import feign.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.hting.stock.api.ths.ThsFeignApi;
import top.hting.stock.dao.StockCategoryRepository;
import top.hting.stock.entity.StockCategory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zzwen6
 * @date 2024/8/11 11:17
 */
@Service
public class ThsStockJob {

    @Autowired
    ThsFeignApi thsFeignApi;
    @Autowired
    StockCategoryRepository stockCategoryRepository;

    /**
     *
     * @return
     */
    @XxlJob("pullThshyBlock")
    public ReturnT<String> pullThshyBlock() throws IOException {

        this.pullList(1);
        this.pullList(2);

        return ReturnT.SUCCESS;
    }

    private void pullList(int type) throws IOException {
        Response response = null;
        if (type == 1) {
            response = thsFeignApi.getThshyPage();
        } else if (type == 2) {
            response = thsFeignApi.getGnPage();
        }

        Response.Body body = response.body();
        InputStream inputStream = body.asInputStream();
        StringBuilder result = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, "GBK"))) {
            int c;
            while ((c = reader.read()) != -1) {
                result.append((char) c);
            }
        }

        List<StockCategory> list = new ArrayList<>();

        Document document = Jsoup.parse(result.toString());
        Elements cateItems = document.getElementsByClass("cate_items");
        for (Element cateItem : cateItems) {
            Elements a = cateItem.getElementsByTag("a");
            for (Element aElement : a) {
                String href = aElement.attr("href");
                String name = aElement.text();
                String s = StrUtil.stripIgnoreCase(href, "/");
                String id = StrUtil.sub(s,s.lastIndexOf("/")+1,s.length());
                list.add(new StockCategory(id,name, type, href));
            }
        }

        stockCategoryRepository.saveAll(list);
    }

}
