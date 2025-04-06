package easyfit.services;

import easyfit.models.dtos.objetivo.AjusteSemanalDto;
import easyfit.models.dtos.objetivo.NivelActividadDto;
import easyfit.models.dtos.objetivo.ObjetivoResponseDto;
import easyfit.models.dtos.objetivo.ObjetivoUsuarioDto;
import easyfit.models.dtos.objetivo.PesoActualDto;
import easyfit.models.dtos.objetivo.PesoObjetivoDto;
import easyfit.models.entities.Objetivo;
import easyfit.models.entities.Usuario;
import easyfit.models.enums.ObjetivoUsuario;
import easyfit.models.enums.OpcionPeso;

public interface IObjetivoService {
	ObjetivoResponseDto actualizarPesoActual(PesoActualDto pesoDto, Usuario usuario);
	ObjetivoResponseDto actualizarPesoObjetivo(PesoObjetivoDto dto, Usuario usuario);
    ObjetivoResponseDto actualizarActividad(NivelActividadDto actividadDto, Usuario Usuario);
    ObjetivoResponseDto actualizarOpcionPeso(AjusteSemanalDto ajusteDto, Usuario usuario);
    ObjetivoResponseDto actualizarObjetivoUsuario(ObjetivoUsuarioDto nuevoObjetivo, Usuario usuario);
    
    
}