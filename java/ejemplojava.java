package ejemplojava;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ejemplojava {

    static class Gasto {
        String descripcion;
        String categoria;
        int monto;

        Gasto(String descripcion, String categoria, int monto) {
            this.descripcion = descripcion;
            this.categoria = categoria;
            this.monto = monto;
        }
    }

    public static void main(String[] args) {

        List<Gasto> gastos = Arrays.asList(
            new Gasto("Almuerzo",   "Comida",     3),
            new Gasto("Pasaje",     "Transporte", 1),
            new Gasto("Cine",       "Ocio",       6),
            new Gasto("Cena",       "Comida",     5),
            new Gasto("Taxi",       "Transporte", 4),
            new Gasto("Libro",      "Estudio",    18),
            new Gasto("Café",       "Comida",     2),
            new Gasto("Videojuego", "Ocio",       12),
            new Gasto("Internet",   "Estudio",    20),
            new Gasto("Pizza",      "Comida",     8)
        );

        // 1. Selección
        System.out.println("1. Selección: gastos mayores a $5");
        gastos.stream()
            .filter(g -> g.monto > 5)
            .forEach(g -> System.out.println(g.descripcion));

        // 2. Transformación
        System.out.println("\n2. Transformación: montos en centavos");
        gastos.stream()
            .map(g -> g.descripcion + "=" + (g.monto * 100))
            .forEach(t -> System.out.println(t));

        // 3. Validación
        System.out.println("\n3. Validación");
        System.out.println("¿Algún gasto mayor a $15? " + gastos.stream().anyMatch(g -> g.monto > 15));
        System.out.println("¿Todos los montos son mayores a 0? " + gastos.stream().allMatch(g -> g.monto > 0));

        // 4. Extremos
        System.out.println("\n4. Extremos");
        gastos.stream()
            .max(Comparator.comparingInt(g -> g.monto))
            .ifPresent(mayor -> System.out.println("Gasto mayor: " + mayor.descripcion + " ($" + mayor.monto + ")"));

        gastos.stream()
            .min(Comparator.comparingInt(g -> g.monto))
            .ifPresent(menor -> System.out.println("Gasto menor: " + menor.descripcion + " ($" + menor.monto + ")"));
    }
}