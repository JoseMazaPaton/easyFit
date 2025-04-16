package easyfit.services.impl;

import easyfit.models.entities.Objetivo;
import easyfit.models.entities.Usuario;
import easyfit.models.entities.ValorNutricional;
import easyfit.models.enums.Sexo;

public class ObjetivoCalculator {

    private static final int KCAL_MIN_MUJER = 1200;
    private static final int KCAL_MIN_HOMBRE = 1500;


    /**
     * Calcula las kcal objetivo usando Harris-Benedict + ajuste por objetivo.
     * Se basa en peso actual, altura, edad, sexo y nivel de actividad.
     * Controla kcal mínimas por salud.
     */
    public static ValorNutricional calcularKcal(Usuario usuario, Objetivo objetivo, ValorNutricional valores) {

        // Validaciones básicas
        if (usuario.getAltura() <= 0 || usuario.getEdad() <= 0 || usuario.getSexo() == null) {
            throw new IllegalArgumentException("Faltan datos válidos del usuario");
        }

        double peso = objetivo.getPesoActual();
        double altura = usuario.getAltura();
        int edad = usuario.getEdad();

        // 1. Calcular TMB con Mifflin-St Jeor
        double tmb = (10 * peso) + (6.25 * altura) - (5 * edad);
        tmb += (usuario.getSexo() == Sexo.HOMBRE) ? 5 : -161;

        // 2. Factor de actividad
        double factorActividad = switch (objetivo.getActividad()) {
            case SEDENTARIO -> 1.2;
            case LIGERO -> 1.375;
            case MODERADO -> 1.55;
            case ACTIVO -> 1.725;
            case MUYACTIVO -> 1.9;
        };
        double mantenimiento = tmb * factorActividad;

        // 3. Ajuste por objetivo (déficit/superávit moderado, no lineal)
        double kcal;
        switch (objetivo.getObjetivoUsuario()) {
            case PERDERPESO -> kcal = mantenimiento * 0.80; // Déficit del 20%
            case GANARPESO -> kcal = mantenimiento * 1.15; // Superávit del 15%
            case MANTENER -> kcal = mantenimiento;
            default -> kcal = mantenimiento;
        }

        // 4. Controlar mínimos saludables
        int kcalMinima = (usuario.getSexo() == Sexo.HOMBRE) ? KCAL_MIN_HOMBRE : KCAL_MIN_MUJER;
        if (kcal < kcalMinima) kcal = kcalMinima;

        // 5. Guardar y devolver
        valores.setKcalObjetivo((int) Math.round(kcal));
        return valores;
    }

    /**
     * Evalúa si el objetivo del usuario es coherente con su peso actual y peso objetivo.
     */
    public static boolean esObjetivoCoherente(Objetivo objetivo) {
        return switch (objetivo.getObjetivoUsuario()) {
            case PERDERPESO -> objetivo.getPesoObjetivo() < objetivo.getPesoActual();
            case GANARPESO  -> objetivo.getPesoObjetivo() > objetivo.getPesoActual();
            case MANTENER   -> true;
        };
    }
}