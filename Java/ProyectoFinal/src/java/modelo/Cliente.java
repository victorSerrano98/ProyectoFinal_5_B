/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "modelo_cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByClienteId", query = "SELECT c FROM Cliente c WHERE c.clienteId = :clienteId"),
    @NamedQuery(name = "Cliente.findByCedula", query = "SELECT c FROM Cliente c WHERE c.cedula = :cedula"),
    @NamedQuery(name = "Cliente.findByNombres", query = "SELECT c FROM Cliente c WHERE c.nombres = :nombres"),
    @NamedQuery(name = "Cliente.findByApellidos", query = "SELECT c FROM Cliente c WHERE c.apellidos = :apellidos"),
    @NamedQuery(name = "Cliente.findByGenero", query = "SELECT c FROM Cliente c WHERE c.genero = :genero"),
    @NamedQuery(name = "Cliente.findByEstadoCivil", query = "SELECT c FROM Cliente c WHERE c.estadoCivil = :estadoCivil"),
    @NamedQuery(name = "Cliente.findByFechaNacimiento", query = "SELECT c FROM Cliente c WHERE c.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Cliente.findByCorreo", query = "SELECT c FROM Cliente c WHERE c.correo = :correo"),
    @NamedQuery(name = "Cliente.findByTelefono", query = "SELECT c FROM Cliente c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "Cliente.findByCelular", query = "SELECT c FROM Cliente c WHERE c.celular = :celular"),
    @NamedQuery(name = "Cliente.findByEstado", query = "SELECT c FROM Cliente c WHERE c.estado = :estado")})
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cliente_id")
    private Integer clienteId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "cedula")
    private String cedula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "nombres")
    private String nombres;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "apellidos")
    private String apellidos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "genero")
    private String genero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "estadoCivil")
    private String estadoCivil;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaNacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "correo")
    private String correo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "telefono")
    private String telefono;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "celular")
    private String celular;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clienteId")
    private Collection<Cuenta> cuentaCollection;

    public Cliente() {
    }

    public Cliente(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Cliente(Integer clienteId, String cedula, String nombres, String apellidos, String genero, String estadoCivil, Date fechaNacimiento, String correo, String telefono, String celular, String direccion, boolean estado) {
        this.clienteId = clienteId;
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.genero = genero;
        this.estadoCivil = estadoCivil;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.telefono = telefono;
        this.celular = celular;
        this.direccion = direccion;
        this.estado = estado;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<Cuenta> getCuentaCollection() {
        return cuentaCollection;
    }

    public void setCuentaCollection(Collection<Cuenta> cuentaCollection) {
        this.cuentaCollection = cuentaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clienteId != null ? clienteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.clienteId == null && other.clienteId != null) || (this.clienteId != null && !this.clienteId.equals(other.clienteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unl.proyecto.modelo.Cliente[ clienteId=" + clienteId + " ]";
    }
    
}
