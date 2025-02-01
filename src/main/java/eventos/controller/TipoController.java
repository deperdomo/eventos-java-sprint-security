package eventos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eventos.dao.TipoDao;
import eventos.entidades.Tipo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/tipo")
public class TipoController {
	
	@Autowired
	TipoDao tdao;
	
	@GetMapping("")
	public String getMethodName(Model model) {
		model.addAttribute("tipos", tdao.buscarTodos());
		return "tipos";
	}
	
	@GetMapping("/agregar")
	public String vistaNuevoTipo() {
		return "altaTipo";
	}
	
	@PostMapping("/agregar")
	public String procNuevoTipo(Model model, RedirectAttributes ratt, Tipo tipo) {
		if (tdao.insertarTipo(tipo) == 1) {
			ratt.addFlashAttribute("mensajeOk", "Tipo '"+tipo.getNombre()+"' agregado");
		} else {
			ratt.addFlashAttribute("mensajeError", "Tipo NO agregado");
		}
		return "redirect:/tipo";
	}
	
	@GetMapping("/editar/{idTipo}")
	public String vistaEditarTipo(Model model, @PathVariable int idTipo) {
		model.addAttribute("tipo", tdao.buscarPorId(idTipo));
		return "editarTipo";
	}
	
	@PostMapping("/editar/{idTipo}")
	public String procEditarTipo(RedirectAttributes ratt, Tipo tipo) {
		
		if (tdao.insertarTipo(tipo)==1)
        	ratt.addFlashAttribute("mensajeOk", "Tipo editado");
        else
            ratt.addFlashAttribute("mensajeError", "Tipo NO editado");
		
		return "redirect:/tipo";
	}
	
	@GetMapping("/eliminar/{idTipo}")
	public String procEliminarTipo(RedirectAttributes ratt, Tipo tipo) {

		try {
			if (tdao.eliminarTipo(tipo)==1)
        	ratt.addFlashAttribute("mensajeOk", "Tipo eliminado");
	        else
	            ratt.addFlashAttribute("mensajeError", "Tipo NO eliminado");
		} catch (Exception e) {
			ratt.addFlashAttribute("mensaje", "Este Tipo está asignado a eventos, No puede ser eliminado");
		}
		return "redirect:/tipo";
	}
	
}
