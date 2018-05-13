package com.app.altex.alttexio.customelements;

/**
 * Created by igorqua on 13.03.2018.
 */
public class WalletItem {

    public String id = "";// bitcoin,
    public String name = "";// Bitcoin,
    public String symbol = "";// BTC,
    public double rank = 0;// 1,
    public double price_usd = 0;// 7914.71,
    public double price_btc = 0;// 1.0,
    public double _24h_volume_usd = 0;// 7228850000.0,
    public double market_cap_usd = 0;// 133917882539,
    public double available_supply = 0;// 16920125.0,
    public double total_supply = 0;// 16920125.0,
    public double max_supply = 0;// 21000000.0,
    public double percent_change_1h = 0;// -0.54,
    public double percent_change_24h = 0;// -13.43,
    public double percent_change_7d = 0;// -20.17,
    public double last_updated = 0;// 1521102566

    public WalletItem(String id, String name, String symbol, double price_usd,  double price_btc, double total_supply, double percent_change_1h, double percent_change_24h, double percent_change_7d, double last_updated){
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.price_usd = price_usd;
        this.price_usd = price_usd;
        this.percent_change_1h = percent_change_1h;
        this.percent_change_24h = percent_change_24h;
        this.percent_change_7d = percent_change_7d;
        this.last_updated = last_updated;
    }
}
