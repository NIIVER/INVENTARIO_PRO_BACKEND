package com.sistemas.service;


import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.sistemas.model.Categoria;
import com.sistemas.model.MovimientoInventario;
import com.sistemas.model.Producto;
import com.sistemas.model.Proveedor;
import com.sistemas.model.Rol;
import com.sistemas.model.TipoMovimiento;
import com.sistemas.model.Usuario;
import com.sistemas.repository.CategoriaRepository;
import com.sistemas.repository.MovimientoRepository;
import com.sistemas.repository.ProductoRepository;
import com.sistemas.repository.ProveedorRepository;
import com.sistemas.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;




@Configuration
public class DataInitializer {
	@Bean
    public ApplicationRunner initializer(
            UsuarioRepository usuarioRepository,
            CategoriaRepository categoriaRepository,
            ProveedorRepository proveedorRepository,
            ProductoRepository productoRepository,
            MovimientoRepository movimientoRepository, // 🟢 INYECTADO AQUÍ
            PasswordEncoder passwordEncoder) {
            
        return args -> {
            
            // 1. Inicializar Usuario Admin
            if (usuarioRepository.count() == 0) {
                Usuario admin = new Usuario();
                admin.setNombre("Admin Sistema"); 
                admin.setEmail("admin@inventariopro.com"); 
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRol(Rol.ADMIN);

                usuarioRepository.save(admin);
                System.out.println("============== DATOS INICIALES ==============");
                System.out.println("✅ Usuario ADMIN inicial creado con éxito");
                System.out.println("📧 Email: admin@inventariopro.com");
                System.out.println("🔑 Password: admin123");
                System.out.println("=============================================");
            }

            // 2. Inicializar Categorías
            if (categoriaRepository.count() == 0) {
                Categoria cat1 = new Categoria("Tecnología", "Equipos y dispositivos electrónicos");
                Categoria cat2 = new Categoria("Accesorios", "Periféricos y accesorios");
                Categoria cat3 = new Categoria("Oficina", "Artículos de oficina y papelería");
                Categoria cat4 = new Categoria("Hogar", "Productos para el hogar");
                
                categoriaRepository.saveAll(List.of(cat1, cat2, cat3, cat4));
                System.out.println("✅ Categorías de muestra inicializadas.");
            }

            // 3. Inicializar Proveedores
            if (proveedorRepository.count() == 0) {
                Proveedor prov1 = new Proveedor();
                prov1.setNombre("Tech Perú SAC");
                prov1.setEmail("contacto@techperu.com");
                prov1.setRuc("20718293816");
                prov1.setTelefono("999666777");

                Proveedor prov2 = new Proveedor();
                prov2.setNombre("Importaciones SAC");
                prov2.setEmail("ventas@importaciones.com");
                prov2.setRuc("10458293814");
                prov2.setTelefono("987654321");

                proveedorRepository.saveAll(List.of(prov1, prov2));
                System.out.println("✅ Proveedores de muestra inicializados.");
            }

            // 4. Inicializar Productos
            if (productoRepository.count() == 0) {
                Categoria tecnologia = categoriaRepository.findAll().get(0);
                Categoria accesorios = categoriaRepository.findAll().get(1);
                Proveedor provTech = proveedorRepository.findAll().get(0);

                Producto prod1 = new Producto();
                prod1.setNombre("Laptop Dell Inspiron 15");
                prod1.setDescripcion("Laptop i5 16GB RAM");
                prod1.setPrecio(3000.00);
                prod1.setStock(20);
                prod1.setStockMinimo(5);
                prod1.setCategoria(tecnologia);
                prod1.setProveedor(provTech);
                prod1.setImagenUrl(null);

                Producto prod2 = new Producto();
                prod2.setNombre("Mouse Logitech M185");
                prod2.setDescripcion("Mouse inalámbrico negro");
                prod2.setPrecio(50.00);
                prod2.setStock(3); 
                prod2.setStockMinimo(10); 
                prod2.setCategoria(accesorios);
                prod2.setProveedor(provTech);
                prod2.setImagenUrl(null); 

                productoRepository.saveAll(List.of(prod1, prod2));
                System.out.println("✅ Productos de muestra inicializados.");
            }
         
            if (movimientoRepository.count() == 0) {
             
                Producto laptop = productoRepository.findAll().get(0);
                Producto mouse = productoRepository.findAll().get(1);
                
                
                MovimientoInventario entradaMarzo = new MovimientoInventario();
                entradaMarzo.setProducto(laptop);
                entradaMarzo.setCantidad(25);
                entradaMarzo.setTipo(TipoMovimiento.ENTRADA);
                entradaMarzo.setFecha(LocalDateTime.now().minusMonths(3)); 
                entradaMarzo.setDestino("Proveedor - Stock Base");
                entradaMarzo.setMotivo("Carga inicial de Laptops para campaña");

                
                MovimientoInventario salidaAbril = new MovimientoInventario();
                salidaAbril.setProducto(laptop);
                salidaAbril.setCantidad(5);
                salidaAbril.setTipo(TipoMovimiento.SALIDA);
                salidaAbril.setFecha(LocalDateTime.now().minusMonths(2)); 
                salidaAbril.setDestino("Venta Directa");
                salidaAbril.setMotivo("Venta a cliente corporativo");

             
                MovimientoInventario salidaMayo = new MovimientoInventario();
                salidaMayo.setProducto(mouse);
                salidaMayo.setCantidad(12);
                salidaMayo.setTipo(TipoMovimiento.SALIDA);
                salidaMayo.setFecha(LocalDateTime.now().minusMonths(1)); 
                salidaMayo.setDestino("Uso interno");
                salidaMayo.setMotivo("Renovación de periféricos en oficina administrativa");

               
                MovimientoInventario salidaHoy = new MovimientoInventario();
                salidaHoy.setProducto(laptop);
                salidaHoy.setCantidad(18);
                salidaHoy.setTipo(TipoMovimiento.SALIDA);
                salidaHoy.setFecha(LocalDateTime.now()); 
                salidaHoy.setDestino("Producto dañado");
                salidaHoy.setMotivo("Mermas críticas detectadas en almacén central");


                movimientoRepository.saveAll(List.of(entradaMarzo, salidaAbril, salidaMayo, salidaHoy));
                System.out.println("============== CONCURRENCIA Y FECHAS =============");
                System.out.println("✅ Movimientos iniciales creados en Marzo, Abril, Mayo y Junio.");
                System.out.println("📊 El gráfico estadístico ahora mostrará barras dinámicas.");
                System.out.println("==================================================");
            }
        };
    }
}
