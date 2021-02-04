package com.gitee.kooder.action;

import com.gitee.kooder.server.Action;
import io.vertx.ext.web.RoutingContext;

import java.io.IOException;

/**
 * Default action for web
 * @author Winter Lau<javayou@gmail.com>
 */
public class IndexAction implements Action {

    /**
     * web searcher
     * @param context
     * @return
     */
    public void index(RoutingContext context) throws IOException {
        this.vm(context, "index.vm");
    }

}