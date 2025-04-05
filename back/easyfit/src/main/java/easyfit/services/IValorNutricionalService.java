package easyfit.services;

import easyfit.models.dtos.valornutricional.MacrosRequestDto;
import easyfit.models.dtos.valornutricional.ValorNutriconalResponseDto;
import easyfit.models.entities.Usuario;
import easyfit.models.entities.ValorNutricional;

public interface IValorNutricionalService extends IGenericCrud<ValorNutricional, Integer> {

	
	ValorNutriconalResponseDto actualizarMacrosPorPorcentaje(Usuario usuario, MacrosRequestDto dto);

	ValorNutriconalResponseDto registroMacrosYKcal(String email);
	
}
