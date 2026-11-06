

case class Gasto(descripcion: String, categoria: String, monto: Int)
  val gastos = List(
    Gasto("Almuerzo",   "Comida",     3),
    Gasto("Pasaje",     "Transporte", 1),
    Gasto("Cine",       "Ocio",       6),
    Gasto("Cena",       "Comida",     5),
    Gasto("Taxi",       "Transporte", 4),
    Gasto("Libro",      "Estudio",    18),
    Gasto("Café",       "Comida",     2),
    Gasto("Videojuego", "Ocio",       12),
    Gasto("Internet",   "Estudio",    20),
    Gasto("Pizza",      "Comida",     8)
  )

  // 1. Selección
  println("1. Selección: gastos mayores a $5")
  gastos.filter(g => g.monto > 5).foreach(g => println(g.descripcion))

  // 2. Transformación
  println("\n2. Transformación: montos en centavos")
  gastos.map(g => g.descripcion + "=" + g.monto * 100).foreach(t => println(t))

  // 3. Validación
  println("\n3. Validación")
  println("¿Algún gasto mayor a $15? " + gastos.exists(g => g.monto > 15))
  println("¿Todos los montos son mayores a 0? " + gastos.forall(g => g.monto > 0))

  // 4. Extremos
  println("\n4. Extremos")
  val mayor = gastos.maxBy(g => g.monto)
  val menor = gastos.minBy(g => g.monto)
  println("Gasto mayor: " + mayor.descripcion + " ($" + mayor.monto + ")")
  println("Gasto menor: " + menor.descripcion + " ($" + menor.monto + ")")

