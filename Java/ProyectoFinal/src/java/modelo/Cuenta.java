/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author VICTOR SERRANO
 */
@Entity
@Table(name = "modelo_cuenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuenta.findAll", query = "SELECT c FROM Cuenta c"),
    @NamedQuery(name = "Cuenta.findByCuentaId", query = "SELECT c FROM Cuenta c WHERE c.cuentaId = :cuentaId"),
    @NamedQuery(name = "Cuenta.findByNumero", query = "SELECT c FROM Cuenta c WHERE c.numero = :numero"),
    @NamedQuery(name = "Cuenta.findByEstado", query = "SELECT c FROM Cuenta c WHERE c.estado = :estado"),
    @NamedQuery(name = "Cuenta.findByFechaApertura", query = "SELECT c FROM Cuenta c WHERE c.fechaApertura = :fechaApertura"),
    @NamedQuery(name = "Cuenta.findBySaldo", query = "SELECT c FROM Cuenta c WHERE c.saldo = :saldo"),
    @NamedQuery(name = "Cuenta.findByTipoCuenta", query = "SELECT c FROM Cuenta c WHERE c.tipoCuenta = :tipoCuenta")})
public class Cuenta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cuenta_id")
    private Integer cuentaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "numero")
    private String numero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaApertura")
    @Temporal(TemporalType.DATE)
    private Date fechaApertura;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "saldo")
    private BigDecimal saldo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "tipoCuenta")
    private String tipoCuenta;
    @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id")
    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    private Cliente clienteId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuentaId")
    private Collection<Transaccion> transaccionCollection;

    public Cuenta() {
    }

    public Cuenta(Integer cuentaId) {
        this.cuentaId = cuentaId;
    }

    public Cuenta(Integer cuentaId, String numero, boolean estado, Date fechaApertura, BigDecimal saldo, String tipoCuenta) {
        this.cuentaId = cuentaId;
        this.numero = numero;
        this.estado = estado;
        this.fechaApertura = fechaApertura;
        this.saldo = saldo;
        this.tipoCuenta = tipoCuenta;
    }

    public Integer getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Integer cuentaId) {
        this.cuentaId = cuentaId;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clienteId) {
        this.clienteId = clienteId;
    }

    @XmlTransient
    public Collection<Transaccion> getTransaccionCollection() {
        return transaccionCollection;
    }

    public void setTransaccionCollection(Collection<Transaccion> transaccionCollection) {
        this.transaccionCollection = transaccionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cuentaId != null ? cuentaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuenta)) {
            return false;
        }
        Cuenta other = (Cuenta) object;
        if ((this.cuentaId == null && other.cuentaId != null) || (this.cuentaId != null && !this.cuentaId.equals(other.cuentaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unl.proyecto.modelo.Cuenta[ cuentaId=" + cuentaId + " ]";
    }
    
}
