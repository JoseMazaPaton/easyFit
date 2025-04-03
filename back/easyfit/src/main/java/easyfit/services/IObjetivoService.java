package easyfit.services;

import easyfit.models.entities.Objetivo;
import easyfit.models.entities.Usuario;
import easyfit.models.enums.Actividad;
import easyfit.models.enums.ObjetivoUsuario;
import easyfit.models.enums.OpcionPeso;

public interface IObjetivoService {
    Objetivo actualizarPesoActual(double nuevoPeso, Usuario usuario);
    Objetivo actualizarPesoObjetivo(double nuevoPesoObjetivo, Usuario usuario);
    Objetivo actualizarActividad(Actividad nuevaActividad, Usuario usuario);
    Objetivo actualizarOpcionPeso(OpcionPeso nuevaOpcion, Usuario usuario);
    Objetivo actualizarObjetivoUsuario(ObjetivoUsuario nuevoObjetivo, Usuario usuario);
    
    
}