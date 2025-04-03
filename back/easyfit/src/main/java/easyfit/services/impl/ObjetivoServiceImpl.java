package easyfit.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import easyfit.models.entities.Objetivo;
import easyfit.models.entities.Usuario;
import easyfit.models.enums.Actividad;
import easyfit.models.enums.ObjetivoUsuario;
import easyfit.models.enums.OpcionPeso;
import easyfit.repositories.IObjetivoRepository;
import easyfit.services.IObjetivoService;
import java.util.NoSuchElementException;

@Service
public class ObjetivoServiceImpl implements IObjetivoService {

    @Autowired
    private IObjetivoRepository objetivoRepository;

    @Override
    public Objetivo actualizarPesoActual(double nuevoPeso, Usuario usuario) {
        if(nuevoPeso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor a 0");
        }
        
        Objetivo objetivo = objetivoRepository.findByUsuarioEmail(usuario.getEmail())
            .orElseThrow(() -> new NoSuchElementException("No se encontraron objetivos para el usuario"));
        
        objetivo.setPesoActual(nuevoPeso);
        return objetivoRepository.save(objetivo);
    }
    
    @Override
    public Objetivo actualizarPesoObjetivo(double nuevoPesoObjetivo, Usuario usuario) {
        if(nuevoPesoObjetivo <= 0) {
            throw new IllegalArgumentException("El peso objetivo debe ser mayor a 0");
        }
        
        Objetivo objetivo = objetivoRepository.findByUsuarioEmail(usuario.getEmail())
            .orElseThrow(() -> new NoSuchElementException("No se encontraron objetivos para el usuario"));
        
        objetivo.setPesoObjetivo(nuevoPesoObjetivo);
        return objetivoRepository.save(objetivo);
    }
    
    @Override
    public Objetivo actualizarActividad(Actividad nuevaActividad, Usuario usuario) {
        if(nuevaActividad == null) {
            throw new IllegalArgumentException("La actividad no puede ser nula");
        }
        
        Objetivo objetivo = objetivoRepository.findByUsuarioEmail(usuario.getEmail())
            .orElseThrow(() -> new NoSuchElementException("No se encontraron objetivos para el usuario"));
        
        objetivo.setActividad(nuevaActividad);
        return objetivoRepository.save(objetivo);
    }
    
    @Override
    public Objetivo actualizarOpcionPeso(OpcionPeso nuevaOpcion, Usuario usuario) {
        if(nuevaOpcion == null) {
            throw new IllegalArgumentException("La opción de peso no puede ser nula");
        }
        
        Objetivo objetivo = objetivoRepository.findByUsuarioEmail(usuario.getEmail())
            .orElseThrow(() -> new NoSuchElementException("No se encontraron objetivos para el usuario"));
        
        objetivo.setOpcionPeso(nuevaOpcion);
        return objetivoRepository.save(objetivo);
    }
    
    @Override
    public Objetivo actualizarObjetivoUsuario(ObjetivoUsuario nuevoObjetivo, Usuario usuario) {
        if(nuevoObjetivo == null) {
            throw new IllegalArgumentException("El objetivo no puede ser nulo");
        }
        
        Objetivo objetivo = objetivoRepository.findByUsuarioEmail(usuario.getEmail())
            .orElseThrow(() -> new NoSuchElementException("No se encontraron objetivos para el usuario"));
        
        objetivo.setObjetivoUsuario(nuevoObjetivo);
        return objetivoRepository.save(objetivo);
    }
    
}
