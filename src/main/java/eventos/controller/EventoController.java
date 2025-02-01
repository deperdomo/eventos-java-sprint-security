package eventos.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eventos.dao.EventoDao;
import eventos.dao.TipoDao;
import eventos.dao.UsuarioDao;
import eventos.entidades.Evento;
import eventos.entidades.Tipo;
import eventos.entidades.Usuario;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/evento")
public class EventoController {
	
	@Autowired
	UsuarioDao udao;
	@Autowired
	EventoDao edao;
	@Autowired
	TipoDao tdao;
	

	@GetMapping("/filtro/todos")
	public String getTodos(Model model) {
	    model.addAttribute("eventos", edao.buscarTodos());
	    model.addAttribute("tipos", tdao.buscarTodos());
	    model.addAttribute("mensajeOk", "Todos los eventos");
		return "eventos";
	}
	
	@GetMapping("/filtro/cancelados")
	public String getCancelados(Model model) {
	    model.addAttribute("eventos", edao.buscarPorEstado("CANCELADO"));
	    model.addAttribute("tipos", tdao.buscarTodos());
	    model.addAttribute("mensajeOk", "Eventos cancelados");
		return "eventos";
	}
	
	@GetMapping("/filtro/terminados")
	public String getTerminados(Model model) {
	    model.addAttribute("eventos", edao.buscarPorEstado("TERMINADO"));
	    model.addAttribute("tipos", tdao.buscarTodos());
	    model.addAttribute("mensajeOk", "Eventos terminados");
		return "eventos";
	}
	
	@GetMapping("/filtro/activos")
	public String getAceptados(Model model) {
		List<Evento> eventosActivos = new ArrayList<>();
		for (Evento evento : edao.buscarPorEstado("ACEPTADO")) {
			if (evento.estaActivo()) {
				eventosActivos.add(evento);
			}
		}
	    model.addAttribute("eventos", eventosActivos);
	    model.addAttribute("tipos", tdao.buscarTodos());
	    model.addAttribute("mensajeOk", "Eventos activos");
		return "eventos";
	}
	
	@GetMapping("/filtro/destacados")
	public String getDestacados(Model model) {
	    model.addAttribute("eventos", edao.buscarPorDestacados("S"));
	    model.addAttribute("tipos", tdao.buscarTodos());
	    model.addAttribute("mensajeOk", "Eventos destacados");
		return "eventos";
	}
	
	@GetMapping("/tipo/{idTipo}")
	public String getByTipo(Model model, @PathVariable int idTipo) {
		model.addAttribute("tipos", tdao.buscarTodos());
	    model.addAttribute("eventos", edao.buscarPorTipo(tdao.buscarPorId(idTipo)));
	    model.addAttribute("mensajeOk", "Eventos de tipo "+tdao.buscarPorId(idTipo).getNombre());
		return "eventos";
	}
	
	
	@GetMapping("/agregar")
	public String formAgregarEvento(Model model) {
		model.addAttribute("tipos", tdao.buscarTodos());
		return "altaEventos";
	}
	
	@PostMapping("/agregar")
	public String procAgregar(Evento evento, RedirectAttributes ratt, @RequestParam int idTipo) {
		System.out.println("Este es el idTipo: "+ idTipo);
		System.out.println("Este es el evento: "+ evento);
		Tipo tipo = tdao.buscarPorId(idTipo);
		evento.setTipo(tipo);
		if(edao.agregarEvento(evento) == 1) {
			ratt.addFlashAttribute("mensajeOk", "Evento agregado");
		} else {
			ratt.addFlashAttribute("mensajeError", "No ha sido posible Agregar el evento");
		}
	    return "redirect:/";  
	}
	
	
	@GetMapping("/editar/{idEvento}")
    public String editar(Model model, @PathVariable int idEvento) {
        Evento evento = edao.buscarPorId(idEvento);

        if (evento != null) {
            model.addAttribute("evento", evento);
            model.addAttribute("tipos", tdao.buscarTodos());
            return "editarEvento";
        } else {
            model.addAttribute("mensajeError", "Evento no existe");
            return "forward:/";
        }
    }
	
	@PostMapping("/editar/{idEvento}")
    public String agregarEditado(Evento evento, RedirectAttributes ratt, @PathVariable int idEvento,  int idTipo) {
		Tipo tipo = tdao.buscarPorId(idTipo);
		evento.setTipo(tipo);
        System.out.println("Evento que me llega del formulario: "+evento);

        if (edao.agregarEvento(evento)==1)
        	ratt.addFlashAttribute("mensajeOk", "Evento editado");
        else
            ratt.addFlashAttribute("mensajeError", "Evento NO editado");

            return "redirect:/";
    }
	
	@GetMapping("/cancelar/{idEvento}")
    public String cancelar( RedirectAttributes ratt, @PathVariable int idEvento) {
		Evento evento = edao.buscarPorId(idEvento);
		evento.setEstado("CANCELADO");
       
        if (edao.agregarEvento(evento)==1)
        	ratt.addFlashAttribute("mensajeOk", "Evento cancelado");
        else
        	ratt.addFlashAttribute("mensajeError", "Eveto NO cancelado");

            return "redirect:/";
    }
	
	@GetMapping("/eliminar/{idEvento}")
    public String eliminar( RedirectAttributes ratt, @PathVariable int idEvento) {
		Evento evento = edao.buscarPorId(idEvento);
       try {
		if (edao.eliminarEvento(evento)==1)
        	ratt.addFlashAttribute("mensajeOk", "Evento eliminado");
	        else
	        	ratt.addFlashAttribute("mensajeError", "Eveto NO eliminado");
		} catch (Exception e) {
			ratt.addFlashAttribute("mensajeError", "Este evento tiene reservas, no se puede eliminar");
		}
            return "redirect:/";
    }
	
	@GetMapping("/detalle/{idEvento}")
    public String verDetalle(Model model, @PathVariable int idEvento) {
        Evento evento = edao.buscarPorId(idEvento);

        if (evento != null) {
            model.addAttribute("evento", evento);
            model.addAttribute("aforoReservado", edao.aforoRestante(idEvento));
            System.out.println("Este es el aforo reservado "+ edao.aforoRestante(idEvento));
            return "verDetalleEvento";
        } else {
            model.addAttribute("mensajeError", "Evento no existe");
            return "forward:/";
        }
    }
	
	
	
}
