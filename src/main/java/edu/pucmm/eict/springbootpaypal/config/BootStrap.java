package edu.pucmm.eict.springbootpaypal.config;

import edu.pucmm.eict.springbootpaypal.entidades.Parametro;
import edu.pucmm.eict.springbootpaypal.repositorios.ParametroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class BootStrap implements ApplicationRunner {

    private Logger logger =  Logger.getLogger(BootStrap.class.getName());
    private ParametroRepository parametroRepository;

    public BootStrap(ParametroRepository parametroRepository) {
        this.parametroRepository = parametroRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        logger.info("Iniciando Aplicación Spring Boot Paypal");
        parametroRepository.save(new Parametro(1, "CUENTA_NEGOCIO_PAYPAL", "sb-vov4f28609475@business.example.com"));
    }
}
