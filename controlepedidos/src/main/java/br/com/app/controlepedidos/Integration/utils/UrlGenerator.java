package br.com.app.controlepedidos.Integration.utils;

public class UrlGenerator {

    public static String Serie(String nomeSerie) {
        String URLBASE = "https://www.omdbapi.com/?";
        String API_KEY = "apikey=dd0b7265";
        String PARAM_TITULO = "&t=";
        String SERIE = nomeSerie.replace(" ", "%20");
        return URLBASE + API_KEY + PARAM_TITULO + SERIE;
    }

}
