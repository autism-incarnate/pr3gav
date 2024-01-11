package ru.pr3.framework.managers;


import ru.pr3.framework.pages.PageGoods;
import ru.pr3.framework.pages.PageHome;

public class PageManager {

    private static PageManager pageManager = null;

    private PageHome pageHome = null;

    private PageGoods pageGoods = null;

    private PageManager() {

    }

    public static PageManager getPageManagerInstance() {
        if(pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    public PageHome getHomeInstance() {
        if(pageHome == null) {
            pageHome = new PageHome();
        }
        return pageHome;
    }

    public PageGoods getGoodsInstance() {
        if(pageGoods == null) {
            pageGoods = new PageGoods();
        }
        return pageGoods;
    }
}
