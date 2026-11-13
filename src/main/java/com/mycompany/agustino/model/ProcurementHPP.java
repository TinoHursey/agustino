package com.mycompany.agustino.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Entity
@Table(name = "procurement_hpp")
public class ProcurementHPP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama_item", nullable = false, length = 100)
    private String namaItem;

    @Column(nullable = false)
    private Integer jumlah;

    @Column(name = "harga_beli_total", nullable = false, precision = 15, scale = 2)
    private BigDecimal hargaBeliTotal;

    @Column(name = "hpp_per_unit", nullable = false, precision = 15, scale = 2)
    private BigDecimal hppPerUnit;

    @Column(name = "tanggal_input", nullable = false)
    private LocalDateTime tanggalInput;

    public ProcurementHPP() {}

    public ProcurementHPP(String namaItem, Integer jumlah, BigDecimal hargaBeliTotal) {
        this.namaItem = namaItem;
        this.jumlah = jumlah;
        this.hargaBeliTotal = hargaBeliTotal;
        this.hppPerUnit = hargaBeliTotal.divide(BigDecimal.valueOf(jumlah), 2, RoundingMode.HALF_UP);
        this.tanggalInput = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getNamaItem() { return namaItem; }
    public Integer getJumlah() { return jumlah; }
    public BigDecimal getHargaBeliTotal() { return hargaBeliTotal; }
    public BigDecimal getHppPerUnit() { return hppPerUnit; }
    public LocalDateTime getTanggalInput() { return tanggalInput; }
}