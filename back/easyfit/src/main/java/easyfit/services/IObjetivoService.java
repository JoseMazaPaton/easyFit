package easyfit.services;

import easyfit.models.dtos.objetivo.ObjetivoResponseDto;
import easyfit.models.dtos.objetivo.PesoActualDto;
import easyfit.models.dtos.objetivo.PesoObjetivoDto;
import easyfit.models.entities.Objetivo;
import easyfit.models.entities.Usuario;
import easyfit.models.enums.Actividad;
import easyfit.models.enums.ObjetivoUsuario;
import easyfit.models.enums.OpcionPeso;

public interface IObjetivoService {
	ObjetivoResponseDto actualizarPesoActual(PesoActualDto pesoDto, Usuario usuario);
    Objetivo actualizarPesoObjetivo(double nuevoPesoObjetivo, Usuario usuario);
    Objetivo actualizarActividad(Actividad nuevaActividad, Usuario usuario);
    Objetivo actualizarOpcionPeso(OpcionPeso nuevaOpcion, Usuario usuario);
    Objetivo actualizarObjetivoUsuario(ObjetivoUsuario nuevoObjetivo, Usuario usuario);
	ObjetivoResponseDto actualizarPesoObjetivo(PesoObjetivoDto dto, Usuario usuario);

    
    
}