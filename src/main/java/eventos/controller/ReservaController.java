package eventos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import eventos.dao.ReservaDao;
import eventos.dao.UsuarioDao;
import eventos.entidades.Reserva;
import eventos.entidades.Usuario;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/reserva")
public class ReservaController {
	
	@Autowired
	ReservaDao rdao;
	@Autowired
	UsuarioDao udao;
	
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
	
}
