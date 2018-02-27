package com.linkces.linshi.ui.widget.sectioned.banner;

/**
 * <p>
 * Banner模型类
 */
public class BannerEntity {
    public String title;
    public String img;
    public String link;

    public BannerEntity(String link, String title, String img) {
        this.link = link;
        this.title = title;
        this.img = img;
    }

}
