case class Producto(
                     nombre: String,
                     categoria: String,
                     precio: Double,
                     stock: Int
                   )

val productos = List(
  Producto("Laptop", "Tecnología", 800, 5),
  Producto("Mouse", "Tecnología", 15, 20),
  Producto("Teclado", "Tecnología", 30, 12),
  Producto("Silla", "Muebles", 120, 8),
  Producto("Mesa", "Muebles", 200, 4),
  Producto("Cuaderno", "Papelería", 3, 50),
  Producto("Lápiz", "Papelería", 1, 100),
  Producto("Mochila", "Accesorios", 25, 15),
  Producto("Audífonos", "Tecnología", 45, 10),
  Producto("Lámpara", "Hogar", 35, 7)
)

// 1. partition.
productos.partition(_.precio > 30)

// Recorre la lista e imprime cada producto.
productos.foreach(println)

// 2. sortBy
val ordenados = productos.sortBy(p => p.precio)
ordenados.foreach(println)

// 3. groupBy
val agrupados = productos.groupBy(_.categoria)
agrupados.foreach(println)

// 4. find
val encontrado = productos.find(_.nombre == "Audífonos")
encontrado.foreach(println)

// Combinación
val resultadoCombinado = productos.filter(p => p.categoria == "Tecnología")
  .sortBy(p => p.precio).partition(p => p.precio > 40.0)
resultadoCombinado