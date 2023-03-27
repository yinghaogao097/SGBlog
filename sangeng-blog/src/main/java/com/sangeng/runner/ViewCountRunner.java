package com.sangeng.runner;

import com.sangeng.domain.entity.Article;
import com.sangeng.mapper.ArticleMapper;
import com.sangeng.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 实现CommandLineRunner接口，在应用启动时初始化缓存。
 *
 * @author Achen
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        // 查询博客信息  id  viewCount
        List<Article> articles = articleMapper.selectList(null);
        // 将文章id和浏览量存到map集合中
        Map<String, Integer> viewCountMap = articles
                .stream()
                .collect(Collectors.toMap(article -> article.getId().toString(),
                        article -> article.getViewCount().intValue()));
        // 存储到redis中
        redisCache.setCacheMap("article:viewCount", viewCountMap);
    }
}
