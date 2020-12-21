package com.example.frbase.paketku;

public class Dataku {
    String kunci;
    String isi;

    public Dataku(){

    }
//tekan klik kanan lalu geneate constractor dan getter setternya.
    public Dataku(String kunci, String isi) {
        this.kunci = kunci;
        this.isi = isi;
    }

    public String getKunci() {
        return kunci;
    }

    public void setKunci(String kunci) {
        this.kunci = kunci;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }
}
