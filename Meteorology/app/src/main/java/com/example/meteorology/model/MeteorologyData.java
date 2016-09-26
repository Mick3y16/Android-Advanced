package com.example.meteorology.model;

/**
 * Created by pedro on 31/08/2016.
 */
public class MeteorologyData {

    private int id;

    private String icon;

    public byte[] iconData;

    private String description;

    private float temperature;

    private float humidity;

    public int getId() {
        return this.id;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getDescription() {
        return this.description;
    }

    public float getTemperature() {
        return this.temperature;
    }

    public float getHumidity() {
        return this.humidity;
    }

    public void setId(int id) {
        this.id = id;

//        switch (id) {
//            case 200: setDescription("trovoada com chuva leve"); break;
//            case 201: setDescription("trovoada com chuva"); break;
//            case 202: setDescription("trovoada com chuva forte"); break;
//            case 210: setDescription("trovoada leve"); break;
//            case 211: setDescription("trovoada"); break;
//            case 212: setDescription("trovoada pesada"); break;
//            case 221: setDescription("trovoada irregular"); break;
//            case 230: setDescription("trovoada com garoa fraca"); break;
//            case 231: setDescription("trovoada com garoa"); break;
//            case 232: setDescription("trovoada com garoa pesada"); break;
//            case 300: setDescription("garoa fraca"); break;
//            case 301: setDescription("garoa"); break;
//            case 302: setDescription("garoa intensa"); break;
//            case 310: setDescription("chuva leve"); break;
//            case 311: setDescription("chuva fraca"); break;
//            case 312: setDescription("chuva forte"); break;
//            case 321: setDescription("chuva de garoa"); break;
//            case 500: setDescription("chuva fraca"); break;
//            case 501: setDescription("Chuva moderada"); break;
//            case 502: setDescription("chuva de intensidade pesado"); break;
//            case 503: setDescription("chuva muito forte"); break;
//            case 504: setDescription("Chuva Forte"); break;
//            case 511: setDescription("chuva com congelamento"); break;
//            case 520: setDescription("chuva moderada"); break;
//            case 521: setDescription("chuva"); break;
//            case 522: setDescription("chuva de intensidade pesada"); break;
//            case 600: setDescription("Neve branda"); break;
//            case 601: setDescription("neve"); break;
//            case 602: setDescription("Neve pesada"); break;
//            case 611: setDescription("chuva com neve"); break;
//            case 621: setDescription("banho de neve"); break;
//            case 701: setDescription("Névoa"); break;
//            case 711: setDescription("fumaça"); break;
//            case 721: setDescription("neblina"); break;
//            case 731: setDescription("turbilhões de areia/poeira"); break;
//            case 741: setDescription("Neblina"); break;
//            case 800: setDescription("céu claro"); break;
//            case 801: setDescription("Algumas nuvens"); break;
//            case 802: setDescription("nuvens dispersas"); break;
//            case 803: setDescription("nuvens quebrados"); break;
//            case 804: setDescription("tempo nublado"); break;
//            case 900: setDescription("tornado"); break;
//            case 901: setDescription("tempestade tropical"); break;
//            case 902: setDescription("furacão"); break;
//            case 903: setDescription("frio"); break;
//            case 904: setDescription("quente"); break;
//            case 905: setDescription("com vento"); break;
//            case 906: setDescription("granizo"); break;
//            default : setDescription("descrição indisponível"); break;
//        }
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

}
