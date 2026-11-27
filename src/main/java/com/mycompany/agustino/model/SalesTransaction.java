package com.mycompany.agustino.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales_transaction")
public class SalesTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama_item", nullable = false, length = 100)
    private String namaItem;

    @Column(name = "jumlah_jual", nullable = false)
    private int jumlahJual;

    @Column(name = "harga_jual_total", nullable = false, precision = 15, scale = 2)
    private BigDecimal hargaJualTotal;

    @Column(name = "hpp_saat_terjual", nullable = false, precision = 15, scale = 2)
    private BigDecimal hppSaatTerjual;

    @Column(name = "keuntungan", nullable = false, precision = 15, scale = 2)
    private BigDecimal keuntungan;

    @Column(name = "tanggal_jual", nullable = false)
    private LocalDateTime tanggalJual;

    public SalesTransaction() {
    }

    public SalesTransaction(String namaItem, int jumlahJual, BigDecimal hargaJualTotal, BigDecimal hppPerUnit) {
        this.namaItem = namaItem;
        this.jumlahJual = jumlahJual;
        this.hargaJualTotal = hargaJualTotal;
        this.hppSaatTerjual = hppPerUnit.multiply(BigDecimal.valueOf(jumlahJual));
        this.keuntungan = hargaJualTotal.subtract(this.hppSaatTerjual);
        this.tanggalJual = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaItem() {
        return namaItem;
    }

    public void setNamaItem(String namaItem) {
        this.namaItem = namaItem;
    }

    public int getJumlahJual() {
        return jumlahJual;
    }

    public void setJumlahJual(int jumlahJual) {
        this.jumlahJual = jumlahJual;
    }

    public BigDecimal getHargaJualTotal() {
        return hargaJualTotal;
    }

    public void setHargaJualTotal(BigDecimal hargaJualTotal) {
        this.hargaJualTotal = hargaJualTotal;
    }

    public BigDecimal getHppSaatTerjual() {
        return hppSaatTerjual;
    }

    public void setHppSaatTerjual(BigDecimal hppSaatTerjual) {
        this.hppSaatTerjual = hppSaatTerjual;
    }

    public BigDecimal getKeuntungan() {
        return keuntungan;
    }

    public void setKeuntungan(BigDecimal keuntungan) {
        this.keuntungan = keuntungan;
    }

    public LocalDateTime getTanggalJual() {
        return tanggalJual;
    }

    public void setTanggalJual(LocalDateTime tanggalJual) {
        this.tanggalJual = tanggalJual;
    }
}