
class Gasto:
    def __init__(self, descripcion, categoria, monto):
        self.descripcion = descripcion
        self.categoria = categoria
        self.monto = monto


gastos = [
    Gasto("Almuerzo",   "Comida",     3),
    Gasto("Pasaje",     "Transporte", 1),
    Gasto("Cine",       "Ocio",       6),
    Gasto("Cena",       "Comida",     5),
    Gasto("Taxi",       "Transporte", 4),
    Gasto("Libro",      "Estudio",    18),
    Gasto("Café",       "Comida",     2),
    Gasto("Videojuego", "Ocio",       12),
    Gasto("Internet",   "Estudio",    20),
    Gasto("Pizza",      "Comida",     8),
]

# 1. Selección
print("1. Selección: gastos mayores a $5")
for g in filter(lambda g: g.monto > 5, gastos):
    print(g.descripcion)

# 2. Transformación
print("\n2. Transformación: montos en centavos")
for t in map(lambda g: g.descripcion + "=" + str(g.monto * 100), gastos):
    print(t)

# 3. Validación
print("\n3. Validación")
print("¿Algún gasto mayor a $15?", any(map(lambda g: g.monto > 15, gastos)))
print("¿Todos los montos son mayores a 0?", all(map(lambda g: g.monto > 0, gastos)))

# 4. Extremos
print("\n4. Extremos")
mayor = max(gastos, key=lambda g: g.monto)
menor = min(gastos, key=lambda g: g.monto)
print("Gasto mayor: " + mayor.descripcion + " ($" + str(mayor.monto) + ")")
print("Gasto menor: " + menor.descripcion + " ($" + str(menor.monto) + ")")
