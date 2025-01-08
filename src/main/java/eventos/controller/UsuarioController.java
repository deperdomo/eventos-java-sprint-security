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
	
	@PostMapping("/agregar")
	public String procAgregar(Usuario usuario, RedirectAttributes ratt) {
		List<Perfil> perfiles = new ArrayList<>();
		perfiles.add(pdao.buscarPorNombreDePerfil("ROLE_CLIENTE"));
	    usuario.setPerfiles(perfiles);
	    usuario.setFechaRegistro(new Date());
	    
	    usuario.setPassword("{noop}"+usuario.getPassword());
	    System.out.println("Estos son los datos de usuario:"+ usuario);
	    if (udao.insertarUsuario(usuario)==1) {
	    	ratt.addFlashAttribute("mensaje", "Usuario agregado");
	    } else {
	    	ratt.addFlashAttribute("mensaje", "Usuario No agregado");
	    }
	    
	    if (SecurityContextHolder.getContext().getAuthentication().getName() == null) {
	    	return "login";
	    	
	    } else {
	    	return "redirect:/usuario";
	    }     
	}
	
	@GetMapping("/editar/{username}")
    public String editar(Model model, @PathVariable String username) {
		
        Usuario usuario = udao.buscarPorUsername(username);
        
        if (usuario != null) {
        	usuario.setPassword(usuario.getPassword().substring(6));
            model.addAttribute("usuario", usuario);
            return "editarUsuario";
        } else {
            model.addAttribute("mensaje", "Usuario no existe");
            return "forward:/usuario";
        }
    }
	
	@PostMapping("/editar/{username}")
    public String agregarEditado(Usuario usuario, RedirectAttributes ratt) {
		
        System.out.println("Usuario que me llega del formulario: "+usuario);
        usuario.setPassword("{noop}"+usuario.getPassword());

        
        if (udao.insertarUsuario(usuario)==1)
        	ratt.addFlashAttribute("mensaje", "Usuario editado");
        else
            ratt.addFlashAttribute("mensaje", "Usuario NO editado");

            return "redirect:/usuario";
    }
	
	@GetMapping("/cancelar/{username}")
    public String cancelar( RedirectAttributes ratt, @PathVariable String username) {
		Usuario usuario= udao.buscarPorUsername(username);
		usuario.setEnabled(0);
       
        if (udao.insertarUsuario(usuario)==1)
        	ratt.addFlashAttribute("mensaje", "Usuario cancelado");
        else
        	ratt.addFlashAttribute("mensaje", "Usuario NO cancelado");

            return "redirect:/usuario";
    }
	
	@GetMapping("/eliminar/{username}")
    public String eliminar( RedirectAttributes ratt, @PathVariable String username) {
       
        if (udao.eliminarUsuario(username)==1)
        	ratt.addFlashAttribute("mensaje", "Usuario eliminado");
        else
        	ratt.addFlashAttribute("mensaje", "Usuario NO eliminado");

            return "redirect:/usuario";
    }

}
