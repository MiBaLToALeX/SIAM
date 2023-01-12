package com.mibaltoalex.siam;

import kong.unirest.Unirest;

/**
 * @author Miguel J. Carmona (MIBALTOALEX)
 */
final class Config {

    Config() {
        Unirest.config().defaultBaseUrl(getSiamEndpoint());
    }

    public String getSiamKey(){
        return System.getenv("SIAM_KEY");
    }

    private String getSiamEndpoint(){
        return System.getenv("SIAM_ENDPOINT");
    }
}
