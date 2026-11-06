package com.mycompany.agustino.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "redfinger_slots")
public class RedfingerSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nomor_cloud", nullable = false)
    private String nomorCloud; // Contoh: "Cloud-01"

    @Column(name = "nomor_aplikasi")
    private Integer nomorAplikasi; // Instance aplikasi di Redfinger

    @Column(name = "akun_roblox", nullable = false)
    private String akunRoblox;

    @Column(name = "sisa_slot", nullable = false)
    private Integer sisaSlot;

    @Column(name = "jam_selesai_joki")
    private LocalDateTime jamSelesaiJoki;

    public RedfingerSlot() {}

    // Getter dan Setter
    public Long getId() { 
        return id; 
    }
    
    public String getNomorCloud() { 
        return nomorCloud; 
    }
    
    public void setNomorCloud(String nomorCloud) { 
        this.nomorCloud = nomorCloud; 
    }
    
    public Integer getNomorAplikasi() { 
        return nomorAplikasi; 
    }
    
    public void setNomorAplikasi(Integer nomorAplikasi) { 
        this.nomorAplikasi = nomorAplikasi; 
    }
    
    public String getAkunRoblox() { 
        return akunRoblox; 
    }
    
    public void setAkunRoblox(String akunRoblox) { 
        this.akunRoblox = akunRoblox; 
    }
    
    public Integer getSisaSlot() { 
        return sisaSlot; 
    }
    
    public void setSisaSlot(Integer sisaSlot) { 
        this.sisaSlot = sisaSlot; 
    }
    
    public LocalDateTime getJamSelesaiJoki() { 
        return jamSelesaiJoki; 
    }
    
    public void setJamSelesaiJoki(LocalDateTime jamSelesaiJoki) { 
        this.jamSelesaiJoki = jamSelesaiJoki; 
    }
}