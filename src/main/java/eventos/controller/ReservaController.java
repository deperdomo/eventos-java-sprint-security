package eventos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eventos.dao.EventoDao;
import eventos.dao.ReservaDao;
import eventos.dao.UsuarioDao;
import eventos.entidades.Evento;
import eventos.entidades.Reserva;
import eventos.entidades.Tipo;
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
	public String getReservaPage(RedirectAttributes ratt, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = udao.buscarPorUsername(authentication.getName());
		
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
    public String agregarEditado(int cantidad, RedirectAttributes ratt, @PathVariable int idReserva) {
		
        Reserva reserva = rdao.buscarPorIdReserva(idReserva);
        reserva.setCantidad(cantidad);
        
        if (rdao.guardarReserva(reserva)==1)
        	ratt.addFlashAttribute("mensaje", "Reserva editada");
        else
        	ratt.addFlashAttribute("mensaje", "Reserva NO editada");

        return "redirect:/reserva";
    }
	
	@GetMapping("/cancelar/{idReserva}")
    public String cancelar( RedirectAttributes ratt, @PathVariable int idReserva) {
		Reserva reserva = rdao.buscarPorIdReserva(idReserva);
       
        if (rdao.eliminarReserva(reserva)==1)
        	ratt.addFlashAttribute("mensaje", "Reserva cancelada");
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
		
		try {
			if(rdao.guardarReserva(reserva) == 1) {
			ratt.addFlashAttribute("mensaje", "Resreva agregada");
		} else {
			ratt.addFlashAttribute("mensaje", "No ha sido posible Agregar la Resreva");
		}
		} catch (Exception e) {
			ratt.addFlashAttribute("mensaje", "La reserva ya esta creada, puede modificarla aquí");
			return "redirect:/reserva";
		}
		
	    return "redirect:/";
	}
}
