package eventos.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eventos.dao.PerfilDao;
import eventos.dao.UsuarioDao;
import eventos.entidades.Evento;
import eventos.entidades.Perfil;
import eventos.entidades.Tipo;
import eventos.entidades.Usuario;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	UsuarioDao udao;
	@Autowired
	PerfilDao pdao;
	
	@GetMapping("")
	public String getMethodName(Model model) {
		model.addAttribute("usuarios", udao.buscarTodos());
		return "usuarios";
	}
	
	@PostMapping("/registro")
	public String procRegistro(Usuario usuario, RedirectAttributes ratt) {
		System.out.println("Controller de registro");
		List<Perfil> perfiles = new ArrayList<>();
		perfiles.add(pdao.buscarPorNombreDePerfil("ROLE_CLIENTE"));
	    usuario.setPerfiles(perfiles);
	    usuario.setFechaRegistro(new Date());
	    usuario.setPassword("{noop}"+usuario.getPassword());
	    System.out.println("Estos son los datos de usuario:"+ usuario);
	    try {
			if (udao.buscarPorUsername(usuario.getUsername())==null) {
				udao.insertarUsuario(usuario);
				ratt.addFlashAttribute("mensajeOk", "Te has registrado correctamente "+usuario.getNombre()+" "+ usuario.getApellidos()+", tienes que logearte para accedea a la app");
				return "redirect:/login";
		    } else {
		    	ratt.addFlashAttribute("mensajeErrorUserEmail", "El Nombre de usuario o el Email no son únicos");
		    	return "redirect:/registro";
		    }
		} catch (Exception e) {
			ratt.addFlashAttribute("mensajeErrorUserEmail", "El Nombre de usuario o el Email no son únicos");
			return "redirect:/registro";
		}
	}
	
	@GetMapping("/agregar")
	public String getAgregarUsuarioPage(Model model) {
		model.addAttribute("perfiles", pdao.buscarTodos());
		return "altaUsuario";
	}
	
	@PostMapping("/agregar")
	public String procAgregar(Usuario usuario, RedirectAttributes ratt, @RequestParam("idPerfil[]") List<Integer> idPerfiles) {
		System.out.println("Controller de agregar usuario");
		List<Perfil> perfiles = new ArrayList<>();
		for (Integer idPerfil : idPerfiles) {
			perfiles.add(pdao.buscarPorId(idPerfil));
		}
	    usuario.setPerfiles(perfiles);
	    usuario.setFechaRegistro(new Date());
	    usuario.setPassword("{noop}"+usuario.getPassword());
	    System.out.println("Estos son los datos de usuario:"+ usuario);
	    try {
			if (udao.buscarPorUsername(usuario.getUsername())==null) {
				udao.insertarUsuario(usuario);
				ratt.addFlashAttribute("mensajeOk", "Usuario agregado correctamente");
				return "redirect:/usuario";
		    } else {
		    	ratt.addFlashAttribute("mensajeErrorUserEmail", "El Nombre de usuario o el Email no son únicos");
		    	return "redirect:/usuario/agregar";
		    }
		} catch (Exception e) {
			ratt.addFlashAttribute("mensajeErrorUserEmail", "El Nombre de usuario o el Email no son únicos");
			return "redirect:/usuario/agregar";
		}    
	}
	
	@GetMapping("/editar/{username}")
    public String editar(Model model, @PathVariable String username) {
		
        Usuario usuario = udao.buscarPorUsername(username);
        
        if (usuario != null) {
        	usuario.setPassword(usuario.getPassword().substring(6));
            model.addAttribute("usuario", usuario);
            model.addAttribute("perfiles", pdao.buscarTodos());
            return "editarUsuario";
        } else {
            model.addAttribute("mensajeError", "Usuario no existe");
            return "forward:/usuario";
        }
    }
	
	@PostMapping("/editar/{username}")
    public String agregarEditado(Usuario usuario, RedirectAttributes ratt, @RequestParam("idPerfil[]") List<Integer> idPerfiles) {

        System.out.println("Usuario que me llega del formulario: "+usuario);
        usuario.setPassword("{noop}"+usuario.getPassword());

        List<Perfil> perfiles = new ArrayList<>();
		for (Integer idPerfil : idPerfiles) {
			perfiles.add(pdao.buscarPorId(idPerfil));
		}
		usuario.setPerfiles(perfiles);
        usuario.setFechaRegistro(new Date());
        if (udao.insertarUsuario(usuario)==1)
        	ratt.addFlashAttribute("mensajeOk", "Usuario editado");
        else
            ratt.addFlashAttribute("mensajeError", "Usuario NO editado");

            return "redirect:/usuario";
    }
	
	@GetMapping("/cancelar/{username}")
    public String cancelar( RedirectAttributes ratt, @PathVariable String username) {
		Usuario usuario= udao.buscarPorUsername(username);
		usuario.setEnabled(0);
       
        if (udao.insertarUsuario(usuario)==1)
        	ratt.addFlashAttribute("mensajeOk", "Usuario cancelado");
        else
        	ratt.addFlashAttribute("mensajeError", "Usuario NO cancelado");
            return "redirect:/usuario";
    }
	
	@GetMapping("/eliminar/{username}")
    public String eliminar( RedirectAttributes ratt, @PathVariable String username) {
		try {
			if (udao.eliminarUsuario(username)==1)
	        	ratt.addFlashAttribute("mensajeOk", "Usuario eliminado correctamente");
	        else
	        	ratt.addFlashAttribute("mensajeError", "Usuario NO eliminado");
		} catch (Exception e) {
			ratt.addFlashAttribute("mensajeError", "El usuario "+udao.buscarPorUsername(username).getNombre()+" posee reservas, NO puede ser eliminado");
		}
            return "redirect:/usuario";
    }

	
	
}
