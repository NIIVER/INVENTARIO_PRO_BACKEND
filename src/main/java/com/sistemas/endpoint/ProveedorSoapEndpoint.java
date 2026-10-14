package com.sistemas.endpoint;

import com.sistemas.model.Proveedor;
import com.sistemas.repository.ProveedorRepository; // 🟢 Tu repositorio existente
import com.tienda.webservices.GetProveedorRequest;
import com.tienda.webservices.GetProveedorResponse;
import com.tienda.webservices.ProveedorSoap;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Optional;

@Endpoint
public class ProveedorSoapEndpoint {

    private static final String NAMESPACE_URI = "http://tienda.com/webservices";
    private final ProveedorRepository proveedorRepository;

    
    public ProveedorSoapEndpoint(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProveedorRequest")
    @ResponsePayload
    public GetProveedorResponse getProveedor(@RequestPayload GetProveedorRequest request) {
        GetProveedorResponse response = new GetProveedorResponse();
        ProveedorSoap soapProv = new ProveedorSoap();

        
        Optional<Proveedor> proveedorBD = proveedorRepository.findById(request.getId());

        if (proveedorBD.isPresent()) {
            Proveedor p = proveedorBD.get();
            soapProv.setId(p.getId());
            soapProv.setNombre(p.getNombre());
            
            soapProv.setTelefono(p.getTelefono());
            soapProv.setRuc(p.getRuc() != null ? p.getRuc() : "Sin RUC");
            soapProv.setEmail(p.getEmail() != null ? p.getEmail() : "sin-correo@proveedor.com");
        } else {
            soapProv.setId(request.getId());
            soapProv.setNombre("Proveedor no localizado");
            soapProv.setTelefono("Telefono no encontrado");
            soapProv.setRuc("-");
            soapProv.setEmail("-");
        }

        response.setProveedor(soapProv);
        return response;
    }
}