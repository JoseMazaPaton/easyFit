package easyfit.services;

import easyfit.models.dtos.MacrosRequestDto;
import easyfit.models.dtos.ValorNutriconalResponseDto;
import easyfit.models.entities.Usuario;
import easyfit.models.entities.ValorNutricional;

public interface ValorNutricionalService extends IGenericCrud<ValorNutricional, Integer> {

	
	ValorNutriconalResponseDto actualizarMacrosPorPorcentaje(Usuario usuario, MacrosRequestDto dto);

	ValorNutriconalResponseDto registroMacrosYKcal(String email);
	
}
