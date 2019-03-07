/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author VICTOR SERRANO
 */
@Entity
@Table(name = "modelo_transaccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaccion.findAll", query = "SELECT t FROM Transaccion t"),
    @NamedQuery(name = "Transaccion.findByTransaccionId", query = "SELECT t FROM Transaccion t WHERE t.transaccionId = :transaccionId"),
    @NamedQuery(name = "Transaccion.findByFecha", query = "SELECT t FROM Transaccion t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "Transaccion.findByTipo", query = "SELECT t FROM Transaccion t WHERE t.tipo = :tipo"),
    @NamedQuery(name = "Transaccion.findByValor", query = "SELECT t FROM Transaccion t WHERE t.valor = :valor"),
    @NamedQuery(name = "Transaccion.findByResponsable", query = "SELECT t FROM Transaccion t WHERE t.responsable = :responsable")})
public class Transaccion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "transaccion_id")
    private Integer transaccionId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "tipo")
    private String tipo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private BigDecimal valor;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 160)
    @Column(name = "responsable")
    private String responsable;
    @JoinColumn(name = "cuenta_id", referencedColumnName = "cuenta_id")
    @ManyToOne(optional = false)
    private Cuenta cuentaId;

    public Transaccion() {
    }

    public Transaccion(Integer transaccionId) {
        this.transaccionId = transaccionId;
    }

    public Transaccion(Integer transaccionId, Date fecha, String tipo, BigDecimal valor, String descripcion, String responsable) {
        this.transaccionId = transaccionId;
        this.fecha = fecha;
        this.tipo = tipo;
        this.valor = valor;
        this.descripcion = descripcion;
        this.responsable = responsable;
    }

    public Integer getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(Integer transaccionId) {
        this.transaccionId = transaccionId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public Cuenta getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Cuenta cuentaId) {
        this.cuentaId = cuentaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transaccionId != null ? transaccionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaccion)) {
            return false;
        }
        Transaccion other = (Transaccion) object;
        if ((this.transaccionId == null && other.transaccionId != null) || (this.transaccionId != null && !this.transaccionId.equals(other.transaccionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unl.proyecto.modelo.Transaccion[ transaccionId=" + transaccionId + " ]";
    }
    
}
