package com.accessone.resources;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Current Project cardholders_dw
 * Created by duncan.browne on 29/08/2016.
 */
public class URIHelper {
    private URIHelper() {
    }

    public static String generateURI(String strResourcePath, String strPath) {
        return generateURI(strResourcePath) + strPath;
    }

    public static String generateURI(String strPath) {
        URI uri = UriBuilder.fromUri("http://{host}:{port}")
                .resolveTemplate("host", Configuration.getInstance().getHost())
                .resolveTemplate("port", Integer.valueOf(Configuration.getInstance().getPort())).build(new Object[0]);
        return uri.toString() + strPath;
    }
}
