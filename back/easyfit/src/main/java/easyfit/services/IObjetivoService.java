package easyfit.services;

import easyfit.models.dtos.KcalYMacrosDto;
import easyfit.models.entities.Objetivo;

public interface IObjetivoService extends IGenericCrud<Objetivo, Integer>{

	KcalYMacrosDto calcularKcalYMacrosObjetivo(String email);
}
