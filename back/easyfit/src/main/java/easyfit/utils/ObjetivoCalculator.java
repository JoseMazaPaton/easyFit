package easyfit.utils;

import easyfit.models.entities.Objetivo;
import easyfit.models.entities.Usuario;
import easyfit.models.entities.ValorNutricional;
import easyfit.models.enums.Sexo;

public class ObjetivoCalculator {

    /**
     * Calcula las kcal objetivo usando la fórmula de Harris-Benedict.
     * Tiene en cuenta el sexo, edad, altura, peso y nivel de actividad.
     * También ajusta el valor según si el objetivo es perder, ganar o mantener peso.
     */
    public static void calcularKcal(Usuario usuario, Objetivo objetivo, ValorNutricional valores) {

        // Tasa Metabólica Basal (TMB)
        double tmb;
        if (usuario.getSexo() == Sexo.HOMBRE) {
            tmb = 10 * objetivo.getPesoActual() + 6.25 * usuario.getAltura() - 5 * usuario.getEdad() + 5;
        } else {
            tmb = 10 * objetivo.getPesoActual() + 6.25 * usuario.getAltura() - 5 * usuario.getEdad() - 161;
        }

        // Factor según nivel de actividad
        double factorActividad = switch (objetivo.getActividad()) {
            case SEDENTARIO -> 1.2;
            case LIGERO -> 1.375;
            case MODERADO -> 1.55;
            case ACTIVO -> 1.725;
            case MUYACTIVO -> 1.9;
        };

        // Cálculo de kcal de mantenimiento
        double mantenimiento = tmb * factorActividad;

        // Ajuste según objetivo (subir, bajar o mantener peso)
        double ajuste = (objetivo.getOpcionPeso().getValorKg() * 7700) / 7;
        double kcal = switch (objetivo.getObjetivoUsuario()) {
            case PERDERPESO -> mantenimiento - ajuste;
            case GANARPESO -> mantenimiento + ajuste;
            case MANTENER -> mantenimiento;
        };

        // Guardamos el resultado de las kcal en la entidad de valores nutricionales
        valores.setKcalObjetivo((int) kcal);
    }
}
