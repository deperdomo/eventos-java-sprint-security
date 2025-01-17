package eventos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eventos.dao.EventoDao;
import eventos.dao.ReservaDao;
import eventos.dao.UsuarioDao;
import eventos.entidades.Evento;
import eventos.entidades.Reserva;
import eventos.entidades.Usuario;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/reserva")
public class ReservaController {
	
	@Autowired
	ReservaDao rdao;
	@Autowired
	UsuarioDao udao;
	@Autowired
	EventoDao edao;
	
	@GetMapping("")
	public String getReservaPage(RedirectAttributes ratt, Model model, HttpSession session) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = udao.buscarPorUsername(authentication.getName());
		
		session.setAttribute("usuario", usuario);
		List<Reserva> reservasActivas = new ArrayList<>();
		
		for (Reserva reserva : rdao.buscarReservasPorUsuario(usuario)) {
			if (reserva.getEvento().estaActivo()) {
				reservasActivas.add(reserva);
			}
		}
		model.addAttribute("reservas", reservasActivas);
		ratt.addFlashAttribute("mensaje", "Tus reservas activas");
		return "reservas";
	}
	
	@PostMapping("/editar/{idReserva}")
    public String agregarEditado(@RequestParam int cantidad, RedirectAttributes ratt, @PathVariable int idReserva) {
		
        Reserva reserva = rdao.buscarPorIdReserva(idReserva);
        reserva.setCantidad(cantidad);
        
        if (edao.aforoRestante(reserva.getEvento().getIdEvento())+cantidad >= cantidad) {
        	rdao.guardarReserva(reserva);
        	ratt.addFlashAttribute("mensaje", "Reserva modificada correctamente");
        } else {
        	ratt.addFlashAttribute("mensaje", "La cantidad introducida es demaciado alta para el aforo disponible en este evento");
        }
        	
        return "redirect:/reserva";
    }
	
	@GetMapping("/cancelar/{idReserva}")
    public String cancelar( RedirectAttributes ratt, @PathVariable int idReserva) {
		Reserva reserva = rdao.buscarPorIdReserva(idReserva);
       
        if (rdao.eliminarReserva(reserva)==1)
        	ratt.addFlashAttribute("mensaje", "Reserva cancelada correctamente");
        else
        	ratt.addFlashAttribute("mensaje", "Reserva NO cancelada");

        return "redirect:/reserva";
    }
	
	@PostMapping("/agregar/{idEvento}")
	public String procAgregar(Reserva reserva, RedirectAttributes ratt, @PathVariable int idEvento, HttpSession session) {
		Evento evento = edao.buscarPorId(idEvento);
		reserva.setEvento(evento);
		reserva.setPrecioVenta(evento.getPrecio());
		reserva.setUsuario((Usuario) session.getAttribute("usuario"));
		System.out.println("Este es la reserva: "+ reserva);
		
		if (!evento.estaActivo() && evento.getEstado() == "TERMINADO" && evento.getEstado() == "CANCELADO") {
			ratt.addFlashAttribute("mensaje", "No es posible reservar en este evento, verifique su estado o fecha de inicio");
			return "redirect:/";
		}
		try {
			if(rdao.guardarReserva(reserva) == 1) {
			ratt.addFlashAttribute("mensaje", "Resreva agregada");
			} else {
				ratt.addFlashAttribute("mensaje", "No ha sido posible Agregar la Resreva");
			}
		} catch (Exception e) {
			ratt.addFlashAttribute("mensaje", "Ya ha hecho una reserva para este evento, puede modificarla aquí");
			return "redirect:/reserva";
		}
		
		
	    return "redirect:/";
	}
}
