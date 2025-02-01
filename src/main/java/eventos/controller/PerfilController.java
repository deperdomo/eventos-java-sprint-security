package eventos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eventos.dao.PerfilDao;
import eventos.entidades.Perfil;
import eventos.entidades.Tipo;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

	@Autowired
	PerfilDao pdao;
	
	@GetMapping("")
	public String perfilPage(Model model) {
		model.addAttribute("perfiles", pdao.buscarTodos());
		return "perfiles";
	}
	
	@GetMapping("/agregar")
	public String procAltaPerfil(Model model) {
		return "altaPerfil";
	}
	
	@PostMapping("/agregar")
	public String procNuevoPerfil(Model model, RedirectAttributes ratt, Perfil perfil) {
		if (pdao.insertarPerfil(perfil) == 1) {
			ratt.addFlashAttribute("mensajeOk", "Perfil agregado");
		} else {
			ratt.addFlashAttribute("mensajeError", "Perfil NO agregado");
		}
		return "redirect:/perfil";
	}
	
	@GetMapping("/editar/{idPerfil}")
	public String vistaEditarTipo(Model model, @PathVariable int idPerfil) {
		model.addAttribute("perfil", pdao.buscarPorId(idPerfil));
		return "editarPerfil";
	}
	
	@PostMapping("/editar/{idPerfil}")
	public String procEditarTipo(RedirectAttributes ratt, Perfil perfil) {
		
		if (pdao.insertarPerfil(perfil)==1)
        	ratt.addFlashAttribute("mensajeOk", "Perfil editado");
        else
            ratt.addFlashAttribute("mensajeError", "Perfil NO editado");
		
		return "redirect:/perfil";
	}
	
}
