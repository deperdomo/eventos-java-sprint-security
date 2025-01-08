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
	public String getReservaPage(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = udao.buscarPorUsername(authentication.getName());
		
		List<Reserva> reservasActivas = new ArrayList<>();
		
		for (Reserva reserva : rdao.buscarReservasPorUsuario(usuario)) {
			if (reserva.getEvento().estaActivo()) {
				reservasActivas.add(reserva);
			}
		}
		model.addAttribute("reservas", reservasActivas);
		model.addAttribute("mensaje", "Tus reservas activas");
		return "reservas";
	}
	
	@PostMapping("/editar/{idReserva}")
    public String agregarEditado(int cantidad, Model model, @PathVariable int idReserva) {
		
        Reserva reserva = rdao.buscarPorIdReserva(idReserva);
        reserva.setCantidad(cantidad);
        
        if (rdao.guardarReserva(reserva)==1)
        	model.addAttribute("mensaje", "Reserva editada");
        else
        	model.addAttribute("mensaje", "Reserva NO editada");

            return "redirect:/reserva";
    }
}
