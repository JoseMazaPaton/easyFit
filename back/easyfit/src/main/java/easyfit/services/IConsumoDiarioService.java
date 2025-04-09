package easyfit.services;

import java.util.List;

import easyfit.models.dtos.consumodiario.DiarioResponseDto;
import easyfit.models.dtos.consumodiario.HistorialCaloriasDto;
import easyfit.models.dtos.consumodiario.HistorialPesoDto;
import easyfit.models.entities.ConsumoDiario;
import easyfit.models.entities.Usuario;

public interface IConsumoDiarioService extends IGenericCrud<ConsumoDiario, Integer>{

	DiarioResponseDto obtenerResumenDiario(Usuario usuario);

	HistorialPesoDto resumenPesosUsuario(Usuario usuario);

	List<HistorialCaloriasDto> getConsumoUltimos7Dias(Usuario usuario);
	
}
