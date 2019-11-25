/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.resources;

import co.edu.uniandes.csw.cortos.dtos.ClienteDTO;
import co.edu.uniandes.csw.cortos.dtos.ClienteDetailDTO;
import co.edu.uniandes.csw.cortos.ejb.ClienteLogic;
import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Arturo Rubio Caballero
 */
@Path("cliente")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ClienteResource 
{
    private static final Logger LOGGER = Logger.getLogger(ClienteResource.class.getName());
    private  static final String NO = " no existe.";
    private  static final String REC = "El recurso /cliente/";
    
    @Inject
    private ClienteLogic clienteLogic;
    @POST
    public ClienteDTO createClient(ClienteDTO cliente)throws BusinessLogicException
    {
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        ClienteEntity clienteEntity = cliente.toEntity();
        // Invoca la lógica para crear la editorial nueva
        ClienteEntity nuevoClienteEntity = clienteLogic.createCliente(clienteEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        ClienteDTO nuevoClienteDTO = new ClienteDTO(nuevoClienteEntity);
        LOGGER.log(Level.INFO, "ClienteResource createCliente: output: {0}", nuevoClienteDTO);
        return nuevoClienteDTO;
    }
    
    @GET
    public List<ClienteDetailDTO> getClientes()
    {
        LOGGER.info("ClienteResource getClientes :input : void");
        List<ClienteDetailDTO> listaClientes = listEntity2DTO(clienteLogic.getClientes());
        LOGGER.log(Level.INFO,"ComentarioResource getCalificaciones :output{0}", listaClientes);
        return listaClientes;
    }
    
    @GET
    @Path("{clienteId:\\d+}")
    public ClienteDetailDTO getCliente(@PathParam("clienteId") long id )
    {
        LOGGER.log(Level.INFO,"ClienteReosurce getClient :input : {0}",id);
        ClienteEntity clienteEntity = clienteLogic.getCliente(id);
        if(clienteEntity ==null)
        {
            throw new WebApplicationException(REC+ id+NO, 404);
        }
        ClienteDetailDTO clienteDTO = new ClienteDetailDTO(clienteEntity);
        return clienteDTO;
    }
    
    @GET
    @Path("{ClienteFind: [a-zA-Z][a-zA-Z]*}")
    public ClienteDetailDTO getClienteNombre(@PathParam("ClienteFind") String name )
    {
        LOGGER.log(Level.INFO,"ClienteReosurce getClientName :input : {0}",name);
        ClienteEntity clienteEntity = clienteLogic.getClienteNombre(name);
        if(clienteEntity ==null)
        {
            throw new WebApplicationException("El recurso /cliente/"+ name+"no existe.", 404);
        }
        ClienteDetailDTO clienteDTO = new ClienteDetailDTO(clienteEntity);
        return clienteDTO;
    }
    
    @GET
    @Path("search-{ClienteFind: [a-zA-Z][a-zA-Z]*}")
    public List<ClienteDetailDTO> getClienteNombreLike(@PathParam("ClienteFind") String name )
    {
        LOGGER.log(Level.INFO,"ClienteReosurce getClientName :input : {0}",name);
        List<ClienteEntity> clienteEntity = clienteLogic.getClienteNombreLike(name);
        if(clienteEntity ==null)
        {
            throw new WebApplicationException("El recurso /cliente/"+ name+"no existe.", 404);
        }
        List<ClienteDetailDTO> clienteDTO = listEntity2DTO(clienteEntity);
        return clienteDTO;
    }
    
    @PUT
    @Path("{clienteId:\\d+}")
    public ClienteDTO updateCliente(@PathParam("clienteId") Long id, ClienteDTO cliente) throws BusinessLogicException{
        LOGGER.log(Level.INFO,"ClienteResource updateCliente:input:id:{0},cliente:{1}",new Object[]{id,cliente});
        cliente.setId(id);
        if(clienteLogic.getCliente(id)==null)
        {
            throw new WebApplicationException(REC+id+NO,404);
        }
        ClienteDTO clienteDTO = new ClienteDTO(clienteLogic.updateCliente(id,cliente.toEntity()));
        LOGGER.log(Level.INFO,"ClienteResource updateClienteResource:outpur:{0}",clienteDTO);
        return clienteDTO;
    }
    
    @DELETE
    @Path("{clienteId:\\d+}")
    public void deleteCliente(@PathParam("clienteId") Long id ) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"ClienteResource deleteCliente :input:{0}",id);
        ClienteEntity entity = clienteLogic.getCliente(id);
        if(entity ==null)
        {
            throw new WebApplicationException(REC+id+NO,404);
        }
        clienteLogic.deleteCliente(id);
        LOGGER.info("ClienteResorce deleteCliente:output:void");
    }
    
     /**
     * Conexión con el servicio de libros para una editorial.
     * {@link EditorialBooksResource}
     *
     * Este método conecta la ruta de /editorials con las rutas de /books que
     * dependen de la editorial, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los libros de una editorial.
     *
     * @param clienteId El ID de la editorial con respecto a la cual se
     * accede al servicio.
     * @return El servicio de libros para esta editorial en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @Path("{clienteId: \\d+}/calificaciones")
    public Class<ClienteCalificacionResource> getClienteCalificacionResource(@PathParam("clienteId") Long clienteId) {
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException(REC + clienteId + NO, 404);
        }
        return ClienteCalificacionResource.class;
    }
    
    /**
     * Conexión con el servicio de libros para una editorial.
     * {@link EditorialBooksResource}
     *
     * Este método conecta la ruta de /editorials con las rutas de /books que
     * dependen de la editorial, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los libros de una editorial.
     *
     * @param clienteId El ID de la editorial con respecto a la cual se
     * accede al servicio.
     * @return El servicio de libros para esta editorial en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @Path("{clienteId: \\d+}/comentarios")
    public Class<ClienteComentarioResource> getClienteComentarioResource(@PathParam("clienteId") Long clienteId) {
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException(REC + clienteId + NO, 404);
        }
        return ClienteComentarioResource.class;
    }
    
    /**
     * Conexión con el servicio de libros para una editorial.
     * {@link EditorialBooksResource}
     *
     * Este método conecta la ruta de /editorials con las rutas de /books que
     * dependen de la editorial, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los libros de una editorial.
     *
     * @param clienteId El ID de la editorial con respecto a la cual se
     * accede al servicio.
     * @return El servicio de libros para esta editorial en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @Path("{clienteId: \\d+}/facturas")
    public Class<ClienteFacturaResource> getClienteFacturaResource(@PathParam("clienteId") Long clienteId) {
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException(REC+ clienteId + NO, 404);
        }
        return ClienteFacturaResource.class;
    }
    
    /**
     * Conexión con el servicio de libros para una editorial.
     * {@link EditorialBooksResource}
     *
     * Este método conecta la ruta de /editorials con las rutas de /books que
     * dependen de la editorial, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los libros de una editorial.
     *
     * @param clienteId El ID de la editorial con respecto a la cual se
     * accede al servicio.
     * @return El servicio de libros para esta editorial en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @Path("{clienteId: \\d+}/formasDePago")
    public Class<ClienteFormaPagoResource> getClienteFormaPagoResource(@PathParam("clienteId") Long clienteId) {
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException(REC+ clienteId +NO, 404);
        }
        return ClienteFormaPagoResource.class;
    }
    
    private List<ClienteDetailDTO> listEntity2DTO(List<ClienteEntity> entityList)
    {
        List<ClienteDetailDTO> list = new ArrayList<>();
        for(ClienteEntity entity:entityList){
            list.add(new ClienteDetailDTO(entity));
        }
        return list;
    }
    
}
