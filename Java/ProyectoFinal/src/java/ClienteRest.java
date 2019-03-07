/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import datos.CrudService;
import java.util.List;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.AuthUser;
import modelo.AuthUserGroups;
import modelo.Cliente;
import modelo.Cuenta;
import modelo.Transaccion;
import herramientas.Hasher;

/**
 *
 * @author VICTOR SERRANO
 */
@Path("Clientes")
public class ClienteRest {
    @GET
    public void sumar(){
       
    }
    
   @Path("Guardar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response GuardarAlumno(Cliente cliente){
        CrudService crudService = new CrudService();
        return Response.status(Response.Status.CREATED).entity(crudService.create(cliente)).build();
    }
    
    @Path("GuardarCuenta")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response GuardarCuenta(Cuenta cliente){
        CrudService crudService = new CrudService();
        return Response.status(Response.Status.CREATED).entity(crudService.create(cliente)).build();
    }
    
        @Path("BuscarCuenta")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response BuscarCuenta(@QueryParam("id") int id){
        CrudService crudService = new CrudService();
        return Response.ok(crudService.find(Cuenta.class, id)).build();
    }
    
    @Path("BuscarTransaccion")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response BuscarTransaccion(@QueryParam("id") int id){
        CrudService crudService = new CrudService();
        return Response.ok(crudService.find(Transaccion.class, id)).build();
    }
    
    @Path("GuardarTransaccionDeposito")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response GuardarTransaccionDeposito(Transaccion transaccion){
        CrudService crudService = new CrudService();
        crudService.create(transaccion);
        Cuenta cuenta = transaccion.getCuentaId();
        Cuenta c = crudService.find(Cuenta.class, cuenta.getCuentaId());
        cuenta.setSaldo(c.getSaldo().add(transaccion.getValor()));
        crudService.update(cuenta);
        return Response.status(Response.Status.CREATED).entity(transaccion).build();
    }
    
    @Path("GuardarTransaccionRetiro")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response GuardarTransaccionRetiro(Transaccion transaccion){
        CrudService crudService = new CrudService();
        crudService.create(transaccion);
        Cuenta cuenta = transaccion.getCuentaId();
        Cuenta c = crudService.find(Cuenta.class, cuenta.getCuentaId());
        cuenta.setSaldo(c.getSaldo().subtract(transaccion.getValor()));
        crudService.update(cuenta);
        return Response.status(Response.Status.CREATED).entity(transaccion).build();
    }
    
    @Path("BuscarUser")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response BuscarUser(@QueryParam("user") String user){
        List <AuthUser> usuario = new CrudService().findWithQuery("SELECT a FROM AuthUser a WHERE a.username = '"+ user + "'");
        return Response.ok(usuario.get(0)).build();
    }
    
    @Path("BuscarNumero")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response BuscarNumero(@QueryParam("numero") String numero){
        List <Cuenta> cuenta = new CrudService().findWithQuery("SELECT c FROM Cuenta c WHERE c.numero = '" + numero + "'");
        return Response.ok(cuenta.get(0)).build();
    }
    
    @Path("BuscarCedula")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response BuscarCedula(@QueryParam("cedula") String cedula){
        List <Cliente> cliente = new CrudService().findWithQuery("SELECT c FROM Cliente c WHERE c.cedula = '" + cedula + "'");
        List <Cuenta> cuenta = new CrudService().findWithQuery("SELECT e FROM Cuenta e WHERE e.cuentaId = '" + cliente.get (0) .getClienteId () + "'");
        return Response.ok(cuenta.get(0)).build();
    }
    
    @Path("GuardarTransaccionNumero")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response GuardarTransaccionNumero(String numero){
       // List <Cuenta> cuenta = new CrudService().findWithQuery("SELECT c FROM Cuenta c WHERE c.numero = '" + numero.toString() + "'");
        
        return Response.ok(numero).build();
    }
    
    @Path("EditarCuenta")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response EditarCuenta(Cuenta cuenta){
        CrudService crudService = new CrudService();
        return Response.status(Response.Status.CREATED).entity(crudService.update(cuenta)).build();
    }
    
    @Path("PresentarTodos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cuenta> PresentarTods(){
        List <Cuenta> cuenta = new CrudService().findWithQuery("SELECT c FROM Cuenta c WHERE c.estado = '" + 1 + "'");
        return cuenta;
    }
    
    @Path("PresentarTransacciones")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transaccion> PresentarTransacciones(@QueryParam("numero") String numero){
        List <Cuenta> cuenta = new CrudService().findWithQuery("SELECT c FROM Cuenta c WHERE c.numero = '" + numero + "'");
        List <Transaccion> transaccion = new CrudService().findWithQuery("SELECT c FROM Transaccion c WHERE c.cuentaId = '" + cuenta.get(0).getCuentaId() + "'");
        
        return transaccion;
    }
    
      @POST
    @Path("/buscarUsuariosData")
    @Consumes({"application/json", "application/json"})
    @Produces({"application/json", "application/json"})
    public Response buscarUsuarios(JsonObject jsonArray) {
        List<AuthUser> listaUsuario= new CrudService().findWithQuery("SELECT e FROM AuthUser e WHERE e.username='" + jsonArray.getString("nombre") + "'");
        
        //tabla auth_user conectada con auth_user_groups donde se encuentran los id que se conecta con auth_group
        if (listaUsuario.isEmpty()) {
            
            return Response.noContent().build();
        } else {
            List<AuthUserGroups> listaGrupo= new CrudService().findWithQuery("SELECT e FROM AuthUserGroups e WHERE e.userId=" + listaUsuario.get(0).getId() );

            Hasher validar= new Hasher();
            
            boolean validarClave= validar.passwordShouldMatch(jsonArray.getString("clave"),listaUsuario.get(0).getPassword());
            
            if(validarClave){
                
                
                
                return Response.ok(listaGrupo.get(0)).build();
                
            }else{
                return Response.noContent().build();
            }
            
            
        }
    }


}
