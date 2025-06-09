package com.grupo2.happypets.controller;

import com.grupo2.happypets.model.Usuario;
import com.grupo2.happypets.model.Pago;
import com.grupo2.happypets.repository.UsuarioRepository;
import com.grupo2.happypets.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PagoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @PostMapping("/guardarPago")
    public String guardarPago(
            @RequestParam String nombre,
            @RequestParam String dni,
            @RequestParam String correoElectronico,
            @RequestParam String telefono,
            @RequestParam String tipoPago,
            @RequestParam(required = false) String numeroTarjeta,
            @RequestParam Double monto,
            Model model) {

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setDni(dni);
        usuario.setCorreoElectronico(correoElectronico);
        usuario.setTelefono(telefono);
        usuarioRepository.save(usuario);

        Pago pago = new Pago();
        pago.setTipoPago(tipoPago);
        pago.setNumeroTarjeta(numeroTarjeta);
        pago.setMonto(monto);
        pago.setUsuario(usuario);
        pagoRepository.save(pago);

        model.addAttribute("mensaje", "Pago registrado correctamente");
        return "pago_exitoso";
    }
}