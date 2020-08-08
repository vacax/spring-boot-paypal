package edu.pucmm.eict.springbootpaypal.controladores;

import edu.pucmm.eict.springbootpaypal.config.BootStrap;
import edu.pucmm.eict.springbootpaypal.entidades.Compra;
import edu.pucmm.eict.springbootpaypal.repositorios.CompraRepository;
import edu.pucmm.eict.springbootpaypal.repositorios.ParametroRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping(path = "/")
public class PagoController {

    private ParametroRepository parametroRepository;
    private CompraRepository compraRepository;
    private Logger logger =  Logger.getLogger(PagoController.class.getName());

    public PagoController(ParametroRepository parametroRepository, CompraRepository compraRepository){
        this.parametroRepository = parametroRepository;
        this.compraRepository = compraRepository;
    }

    /**
     *
     * @param model
     * @return
     */
    @GetMapping(path = "/")
    public String index(Model model){
        return "index";
    }

    /**
     *
     * @param model
     * @return
     */
    @GetMapping(path = "/formulario")
    public String formularioPago(Model model){
        model.addAttribute("cuentaNegocio", parametroRepository.getOne(1).getValor());
        return "formulario";
    }

    @PostMapping(path = "/procesarCompraPaypal")
    public String procesarCompraPayPal(Model model, @RequestParam Map<String,String> params){
        //mostrando todas las peticiones recibidas.
        params.forEach((k,v) -> {
            logger.info(String.format("[%s] = %s", k, v));
        });

        //
        Compra compra = new Compra();
        compra.setFactura(params.get("invoice"));
        compra.setTransaccion(params.get("txn_id"));
        compra.setNombre(params.get("item_name"));
        compra.setEstatusPago(params.get("payment_status"));

        compra.setMontoCompra(new BigDecimal(params.get("payment_gross")));
        compra.setMontoManejo(new BigDecimal(params.get("handling_amount")));
        compra.setMontoFee(new BigDecimal(params.get("payment_fee")));
        compra.setMontoEnvio(new BigDecimal(params.get("shipping")));

        compra.setCompradorId(params.get("txn_id"));
        compra.setEmailComprador(params.get("payer_email"));
        compra.setFechaCompra(new Date());
        compra.setVendedor(params.get("business"));

        compra.setCiudad(params.get("address_city"));
        compra.setZip(params.get("address_zip"));
        compra.setEstado(params.get("address_state"));
        compra.setDireccion(params.get("address_name"));

        compraRepository.save(compra);

        return "redirect:/listar";
    }

    @GetMapping(path = "/listar")
    public String listadoCompras(Model model){
        List<Compra> lista = compraRepository.findAll();
        model.addAttribute("lista", lista);
        return "listar";
    }

}
