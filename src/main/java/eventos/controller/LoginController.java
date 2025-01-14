package eventos.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eventos.dao.EventoDao;
import eventos.dao.TipoDao;
import eventos.dao.UsuarioDao;
import eventos.entidades.Usuario;
import jakarta.servlet.http.HttpSession;



@Controller
public class LoginController {
	
	@Autowired
	UsuarioDao usuarioDao;
	@Autowired
	EventoDao edao;
	@Autowired
	TipoDao tdao;
	
	
	
	@GetMapping({"","/","/home"})
	public String home(Model model, HttpSession session) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = usuarioDao.buscarPorUsername(authentication.getName());
		
		System.out.println("Usuario logeado: "+usuario);
		session.setAttribute("usuario", usuario);

		model.addAttribute("eventos", edao.buscarTodos());
		model.addAttribute("tipos", tdao.buscarTodos());
		
		return "eventos";
	}
	
	@GetMapping("/login")
	public String login() {
		System.out.println("Entrando al get mapping de login");
		return "login";
	}
	@GetMapping("/login-error")
	public String loginError( Model model) {
		model.addAttribute("mensajeErrorLogin", "El username o la contraseña son incorrectos");
		return "login";
	}
	
	@GetMapping("/registro")
	public String registro() {
		System.out.println("Entrando al getmapping del registro");
		return "registro";
	}

	
}
