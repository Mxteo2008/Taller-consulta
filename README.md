
# Parte 1: Explorar la Programación Funcional en el Mundo Real

## Caso de estudio empresarial: Funeraria Jaramillo (Loja, Ecuador)

**Funeraria Jaramillo** es una empresa lojana dedicada a la prestación de servicios exequiales y venta de planes de previsión a plazos. Para la gestión de sus contratos, cobros, tablas de amortización y contabilidad, utiliza un sistema ERP basado en web desarrollado sobre **Frappe Framework / ERPNext** (JavaScript en el frontend y Python en el backend).

A continuación se presentan dos casos reales de uso de funciones anónimas extraídos de la lógica de negocio de su sistema de gestión.

---

### Caso 1: Filtrado de Movimientos Financieros en Frontend (JavaScript)

#### 1. Problema que resuelve
En el formulario de gestión contable, el sistema necesita filtrar dinámicamente la lista de movimientos registrados (`lista_de_movimientos`) para obtener únicamente aquellos que correspondan a un día específico (`valor`), permitiendo al usuario visualizar el desglose sin recargar la página.

#### 2. Lenguaje usado
* **Lenguaje:** JavaScript (ES6+).
* **Entorno/Framework:** Client-side de Frappe Framework (`cur_frm.doc`).

#### 3. Ventaja del enfoque funcional
El uso del método funcional `.filter()` evita escribir un bucle `for` manual o gestionar arreglos vacíos temporales con `.push()`. Se especifica declarativamente **qué** se necesita filtrar en lugar de **cómo** iterar.

#### 4. Fragmento de Código Real y Explicación

```javascript
let data_movimientos_filtrado = cur_frm.doc.lista_de_movimientos.filter(data => {
    if (data.dia == valor) {
        return true;
    }
});
```

* **Funcionamiento:** El método `.filter()` recibe una función anónima tipo flecha (`data => { ... }`). Esta lambda evalúa cada objeto `data` dentro del arreglo de movimientos. Si la propiedad `data.dia` coincide con la variable `valor`, la función retorna `true`, registrando el elemento en `data_movimientos_filtrado`.

#### 5. Fuente Consultada
* Información consultada a **Christian Jiménez**, encargado del mantenimiento del sistema.

---

### Caso 2: Auditoría de Cuotas Parcialmente Pagadas en Backend (Python)

#### 1. Problema que resuelve
En el módulo de cartera y cobranzas de contratos de previsión exequial, la empresa necesita identificar de forma rápida cuáles cuotas de una tabla de amortización (`doc.tabla_de_amortizacion`) se encuentran en estado **parcialmente pagado** (es decir, cuotas donde el cliente ya realizó un abono mayor a $\$0$, pero aún no liquida el total a pagar).

#### 2. Lenguaje y Herramientas
* **Lenguaje:** Python 3.
* **Herramientas:** Función de orden superior incorporada `filter()`, función auxiliar de conversión `flt()` de Frappe y sintaxis `lambda`.

#### 3. Ventaja del Enfoque Funcional
En lugar de recorrer iterativamente la tabla con un bucle `for` y añadir bloques `if` anidados para evaluar los montos de cada fila, se pasa un predicado en forma de función anónima (`lambda`) a la función de orden superior `filter()`. Esto reduce el código a una sola línea declarativa, legible y libre de mutación de variables acumuladoras.

#### 4. Fragmento de Código Real y Explicación

```python
cuotas_parcialmente_pagadas = list(filter(
    lambda fila: flt(fila.get('valor_pagado', 0)) > 0 
    and flt(fila.get('valor_pagado', 0)) < flt(fila.get('valor_a_pagar', 0)), 
    doc.tabla_de_amortizacion or []
))
```

* **Funcionamiento:** La función anónima (`lambda fila: ...`) actúa como un predicado booleano que recibe cada diccionario/objeto `fila` de la tabla de amortización. Se realiza una evaluación cuantitativa donde se extraen y convierten a decimal los campos `'valor_pagado'` y `'valor_a_pagar'`. Retorna `True` solo si el valor pagado es estrictamente mayor que $0$ y menor que el valor total a pagar. Además, `filter(...)` y `list(...)` filtran la colección `doc.tabla_de_amortizacion` aplicando la lambda a cada fila y convierten la secuencia resultante en la lista `cuotas_parcialmente_pagadas`.

#### 5. Fuente Consultada
* Información consultada a **Christian Jiménez**, encargado del mantenimiento del sistema.

---

# Parte 2: Entender - ¿Qué es una función anónima?

### 1. ¿Qué es una función anónima y de dónde viene el nombre lambda?

Una **función anónima** es aquella que se define sin asignarle un nombre o identificador explícito, creada para ser utilizada de forma directa en el lugar exacto donde se requiere. 

El término **lambda** proviene del **Cálculo Lambda** ($\lambda$-calculus), un sistema formal matemático desarrollado por Alonzo Church en la década de 1930 para analizar la definición de funciones, la aplicación de argumentos y la recursión.

#### Ejemplo (Scala):
```scala
var f2 = (n: Int) => (1 to n).map(i => 1.0 / (i * (i + 1))).sum
```

---

### 2. ¿En qué se diferencia de una función nombrada?

La principal diferencia radica en que una **función nombrada** se define globalmente o dentro de un ámbito con un identificador (nombre) para ser invocada múltiples veces a lo largo del programa, mientras que una **función anónima** se suele declarar *in-situ* como una expresión para resolver una tarea puntual.

#### Ejemplo Comparativo (Scala):

```scala
// Función/Método nombrado definido con 'def'
def f2(n: Int): Double = (1 to n).map(i => 1.0 / (i * (i + 1))).sum

// Función anónima (expresión lambda) asignada a una variable
var f2 = (n: Int) => (1 to n).map(i => 1.0 / (i * (i + 1))).sum
```

---

### 3. ¿Qué significa que las funciones sean ciudadanos de primera clase? ¿Por qué eso?

Que las funciones sean **ciudadanas de primera clase** (*first-class citizens*) significa que el lenguaje las trata con el mismo nivel de libertad y derechos que a cualquier otro valor o tipo de dato primitivo (`Int`, `String`, `Boolean`). Esto permite:
1. Almacenarlas en variables o estructuras de datos.
2. Pasarlas como argumentos a otras funciones.
3. Retornarlas como resultado desde otras funciones.

Esta denominación —nacida de una analogía histórica acuñada por Christopher Strachey para diferenciar elementos con derechos sintácticos plenos de aquellos restringidos— responde a la necesidad de eliminar la barrera entre código y datos, lo que permite construir abstracciones software mucho más compuestas, flexibles y reutilizables.

#### Ejemplo (Scala):

```scala
// 1. Guardar una función en una variable
val duplicar = (x: Int) => x * 2

// 2. Pasar una función como argumento a otra función
def aplicar(f: Int => Int, valor: Int): Int = f(valor)

val resultado1 = aplicar(duplicar, 5) // Devuelve 10

// 3. Retornar una función desde otra función
def crearMultiplicador(factor: Int): Int => Int = {
  (x: Int) => x * factor
}

val triplicar = crearMultiplicador(3) // 'triplicar' contiene una función nueva
val resultado2 = triplicar(5)          // Devuelve 15
```

---

### 4. ¿Permite pasar una lambda a `map` o `filter`?

**Sí**, totalmente. Métodos como `.map()` y `.filter()` son **funciones de orden superior** (*higher-order functions*), lo que significa que están diseñados específicamente para recibir otras funciones (incluyendo funciones anónimas o lambdas) como parámetros para transformar o filtrar elementos de una colección.

#### Ejemplo (Scala):

```scala
val numeros = List(1, 2, 3, 4, 5, 6)

// Filtrar números pares usando una lambda
val pares = numeros.filter(x => x % 2 == 0)

// Elevar al cuadrado usando una lambda
val alCuadrado = numeros.map(x => x * x)
```

---

### 5. ¿Qué es una *closure*?

Una ***closure*** (o clausura) es una función cuyo cuerpo hace referencia a una o más variables libres definidas fuera de su propio ámbito local. La función "atrapa" o "cierra sobre" esas variables del entorno contenedor, manteniendo acceso a ellas e interactuando con su estado.

#### Ejemplo (Scala):

```scala
// Variable definida fuera de la lambda (en el entorno contenedor)
var factor = 3

// 'multiplicar' es una closure: accede a la variable 'factor' externa
val multiplicar = (x: Int) => x * factor

println(multiplicar(5)) // Imprime 15 (5 * 3)

// Si la variable externa cambia, la closure refleja la modificación
factor = 10
println(multiplicar(5)) // Imprime 50 (5 * 10)
```
# Parte 3: Aplicar - ¿En qué problemas sirven las funciones anónimas?

Para todos los ejemplos uso la **misma lista de 10 gastos** de un estudiante durante un mes (`Gasto(descripcion, categoria, monto)`, con el monto en dólares). Se declara igual en los tres lenguajes (ver `scala/Parte3.scala`, `java/Parte3.java` y `python/parte3.py`):

| descripcion | categoria | monto |
|---|---|---|
| Almuerzo | Comida | 3 |
| Pasaje | Transporte | 1 |
| Cine | Ocio | 6 |
| Cena | Comida | 5 |
| Taxi | Transporte | 4 |
| Libro | Estudio | 18 |
| Café | Comida | 2 |
| Videojuego | Ocio | 12 |
| Internet | Estudio | 20 |
| Pizza | Comida | 8 |

Documenté **4 de los 7 tipos de problemas** del catálogo: Selección, Transformación, Validación y Extremos. En cada uno muestro la situación real, la solución en los tres lenguajes y la salida. Como las tres versiones dan el mismo resultado, la salida se muestra una sola vez por tipo (los archivos completos de salida están en `img/`).

## 3.1 Catálogo de tipos de problemas

### 1. Selección (`filter`)

**Situación real:** un banco necesita detectar las transacciones mayores a $1 000. En mi lista: el estudiante quiere ver cuáles fueron sus gastos mayores a $5.

```scala
// Scala
gastos.filter(g => g.monto > 5).foreach(g => println(g.descripcion))
```
```java
// Java
gastos.stream()
    .filter(g -> g.monto > 5)
    .forEach(g -> System.out.println(g.descripcion));
```
```python
# Python
for g in filter(lambda g: g.monto > 5, gastos):
    print(g.descripcion)
```

**Salida (igual en los tres):**
```
Cine
Libro
Videojuego
Internet
Pizza
```

### 2. Transformación (`map`)

**Situación real:** el estudiante quiere ver todos sus gastos en centavos (monto × 100) sin modificar la lista original.

```scala
// Scala
gastos.map(g => g.descripcion + "=" + g.monto * 100).foreach(t => println(t))
```
```java
// Java
gastos.stream()
    .map(g -> g.descripcion + "=" + g.monto * 100)
    .forEach(t -> System.out.println(t));
```
```python
# Python
for t in map(lambda g: g.descripcion + "=" + str(g.monto * 100), gastos):
    print(t)
```

**Salida:**
```
Almuerzo=300
Pasaje=100
Cine=600
Cena=500
Taxi=400
Libro=1800
Café=200
Videojuego=1200
Internet=2000
Pizza=800
```

### 3. Validación (`exists`, `forall`)

**Situación real:** al final del mes el estudiante quiere saber si hubo algún gasto mayor a $15 y si todos los montos registrados son válidos (mayores a 0).

```scala
// Scala
gastos.exists(g => g.monto > 15)
gastos.forall(g => g.monto > 0)
```
```java
// Java
gastos.stream().anyMatch(g -> g.monto > 15)
gastos.stream().allMatch(g -> g.monto > 0)
```
```python
# Python
any(map(lambda g: g.monto > 15, gastos))
all(map(lambda g: g.monto > 0, gastos))
```

**Salida:**
```
¿Algún gasto mayor a $15? true
¿Todos los montos son mayores a 0? true
```
(Python imprime `True` con mayúscula inicial, el valor es el mismo).

### 4. Extremos (`maxBy`, `minBy`)

**Situación real:** el estudiante quiere identificar su gasto más grande y el más pequeño del mes para ver en qué se le fue más el dinero.

```scala
// Scala
val mayor = gastos.maxBy(g => g.monto)
val menor = gastos.minBy(g => g.monto)
```
```java
// Java: la lambda (a, b) compara dos gastos
Gasto mayor = gastos.stream().max((a, b) -> a.monto - b.monto).get();
Gasto menor = gastos.stream().min((a, b) -> a.monto - b.monto).get();
```
```python
# Python
mayor = max(gastos, key=lambda g: g.monto)
menor = min(gastos, key=lambda g: g.monto)
```

**Salida:**
```
Gasto mayor: Internet ($20)
Gasto menor: Pasaje ($1)
```

**Captura de ejecución:** `img/captura_scala.png`, `img/captura_java.png`, `img/captura_python.png`.

---

# Parte 4: Ampliar - Cuatro operaciones nuevas

Elegí `sortBy`, `groupBy`, `partition` y `zipWithIndex`. Todo el código está en `operaciones/Operaciones.scala` y usa la misma lista de gastos de la Parte 3.

## 4.1 y 4.2 Fichas de las operaciones

### Operación 1: `sortBy`

1. **Qué hace:** ordena la lista de menor a mayor según un criterio que yo defino, y devuelve una lista nueva.
2. **Lambda que recibe:** recibe un elemento y **devuelve la clave de ordenamiento** (un número, texto, etc.). Si le pongo un signo menos, el orden queda de mayor a menor.
3. **Ejemplo en Scala:**
```scala
gastos.sortBy(g => -g.monto).foreach(g => println(g.descripcion + " $" + g.monto))
```
```
Internet $20
Libro $18
Videojuego $12
Pizza $8
Cine $6
Cena $5
Taxi $4
Almuerzo $3
Café $2
Pasaje $1
```
4. **Problema que resuelve:** revisar en qué se fue más el dinero, del gasto más grande al más pequeño. Se parece al tipo **Extremos**, pero en vez de quedarme con un solo elemento obtengo todos ordenados.

### Operación 2: `groupBy`

1. **Qué hace:** separa la lista en grupos según una característica y devuelve un `Map`, donde para cada categoría guarda la lista de sus gastos.
2. **Lambda que recibe:** recibe un elemento y **devuelve la clave del grupo** (en el ejemplo, la categoría).
3. **Ejemplo en Scala:**
```scala
val porCategoria = gastos.groupBy(g => g.categoria)
List("Comida", "Transporte", "Ocio", "Estudio").foreach(c => {
  val lista = porCategoria(c)
  println(c + ": " + lista.size + " gastos, total $" + lista.map(g => g.monto).sum)
})
```
```
Comida: 4 gastos, total $18
Transporte: 2 gastos, total $5
Ocio: 2 gastos, total $18
Estudio: 2 gastos, total $38
```
4. **Problema que resuelve:** un resumen de gastos por categoría para saber dónde se concentra el dinero. Es un **tipo nuevo** (Agrupación): los tipos de la sección 3.1 devuelven un valor o una lista, no varias listas organizadas por clave.

### Operación 3: `partition`

1. **Qué hace:** divide la lista en dos a la vez: los elementos que cumplen una condición y los que no.
2. **Lambda que recibe:** recibe un elemento y **devuelve `Boolean`**. El resultado es una pareja: en `_1` van los que cumplen y en `_2` los que no.
3. **Ejemplo en Scala:**
```scala
val resultado = gastos.partition(g => g.monto > 10)
println("Grandes: " + resultado._1.map(g => g.descripcion).mkString(", "))
println("Pequeños: " + resultado._2.map(g => g.descripcion).mkString(", "))
```
```
Grandes: Libro, Videojuego, Internet
Pequeños: Almuerzo, Pasaje, Cine, Cena, Taxi, Café, Pizza
```
4. **Problema que resuelve:** separar los gastos grandes de los pequeños para revisar primero los que más pesan en el presupuesto. Se parece al tipo **Selección**, con la diferencia de que `filter` solo me da los que cumplen y `partition` me da también los que no, recorriendo la lista una sola vez.

### Operación 4: `zipWithIndex`

1. **Qué hace:** convierte cada elemento en una pareja `(elemento, posición)`. La posición empieza en 0.
2. **Lambda que recibe:** **no recibe lambda**. Se encadena con `map`, que sí recibe una lambda sobre la pareja, para armar el texto final.
3. **Ejemplo en Scala:**
```scala
gastos.sortBy(g => -g.monto).take(3).zipWithIndex
  .map(pareja => (pareja._2 + 1).toString + ". " + pareja._1.descripcion)
  .foreach(linea => println(linea))
```
```
1. Internet
2. Libro
3. Videojuego
```
4. **Problema que resuelve:** numerar una lista, por ejemplo, para armar un ranking. Es parecido al tipo **Transformación** (`map`), porque convierte cada elemento en otro valor, pero con la posición incluida.

## 4.3 Combinar operaciones

1. **Pregunta:** ¿cuál es el ranking de los gastos de comida, del mayor al menor?
2. **Cadena de operaciones:**
```scala
gastos
  .filter(g => g.categoria == "Comida")
  .sortBy(g => -g.monto)
  .zipWithIndex
  .map(pareja => (pareja._2 + 1).toString + ". " + pareja._1.descripcion + " - $" + pareja._1.monto)
  .foreach(linea => println(linea))
```
```
1. Pizza - $8
2. Cena - $5
3. Almuerzo - $3
4. Café - $2
```
3. **Explicación de cada paso:**
   * `filter` (vista en clase): deja solo los gastos de categoría Comida (4 de 10).
   * `sortBy` (nueva): ordena esos 4 por monto de mayor a menor gracias al signo menos.
   * `zipWithIndex` (nueva): le agrega a cada gasto su posición (0, 1, 2, 3).
   * `map` (vista en clase): convierte cada pareja `(gasto, posición)` en un texto con el formato `posición. descripción - $monto`, sumando 1 a la posición para que empiece en 1.

**Captura de ejecución:** `img/captura_operaciones.png`.

---

# Parte 5: Comparar y concluir

## 5.1 Scala, Java y Python frente a frente

| Criterio | Scala | Java | Python |
|---|---|---|---|
| Sintaxis de la lambda | `g => g.monto > 5` | `g -> g.monto > 5` | `lambda g: g.monto > 5` |
| Cómo se declara el tipo | Se infiere del tipo de la lista; se puede escribir explícito: `(g: Gasto) => ...` | Se infiere del `Stream<Gasto>` que viene de la lista | No se declara: Python es de tipado dinámico |
| Operaciones con listas disponibles | Directo sobre la lista: `filter`, `map`, `exists`, `forall`, `maxBy`, `minBy` | Primero `.stream()`, luego: `filter`, `map`, `anyMatch`, `allMatch`, `max`, `min` | Funciones sueltas: `filter`, `map`, `any`, `all`, `max`, `min` con `key` |
| Restricción del lenguaje | Ninguna que me haya afectado | Hay que escribir `.stream()` antes de cada operación, y un `Stream` solo se puede usar una vez | La lambda solo puede tener **una expresión**, y `filter`/`map` devuelven un iterador que se recorre con `for` o se convierte con `list()` |
| Legibilidad (mi opinión) | La más clara: las operaciones se encadenan con punto y se leen de izquierda a derecha | Un poco más larga por `.stream()`, pero ordenada | Clara con lambdas simples, pero `filter(lambda..., lista)` se lee de adentro hacia afuera |

**¿En qué lenguaje fue más natural resolver los problemas?** En **Scala**. Cada tipo de problema tenía una operación con nombre propio directamente sobre la lista (`filter`, `map`, `exists`, `forall`, `maxBy`, `minBy`) y no hubo que adaptar nada. En Java hubo que agregar `.stream()` antes de cada operación, y en Python hubo que usar funciones sueltas y recorrer el resultado.

**¿En qué tipo de problema hubo más diferencias y por qué?** En **Extremos**. Scala tiene `maxBy` y `minBy`, donde la lambda solo devuelve el valor a comparar (`g => g.monto`). En Python se usa `max` con `key=lambda`, que es parecido. En Java en cambio la lambda recibe **dos** gastos y los compara (`(a, b) -> a.monto - b.monto`), que fue lo más difícil de entender. También hay diferencias en **Validación**, porque los nombres cambian en cada lenguaje (`exists`/`forall`, `anyMatch`/`allMatch`, `any`/`all`) y en Python hay que usar `map` antes de `any` o `all`.

## 5.2 Conclusión

Las funciones anónimas son la pieza que permite que operaciones como `filter`, `map` o `exists` sirvan para cualquier problema: la operación define **cómo se recorre** la lista y la lambda define **qué se hace con cada elemento**. Sin ellas, cada condición necesitaría una función con nombre o un bucle `for` con variables auxiliares. Con ellas, cada problema se reduce a una línea que dice qué se quiere, como se vio en los cuatro tipos de problemas de la Parte 3, donde el mismo patrón funcionó en Scala, Java y Python.

Esto coincide con lo que se vio en el caso real de la **Funeraria Jaramillo** ([Caso 2](#caso-2-auditoría-de-cuotas-parcialmente-pagadas-en-backend-python)): para encontrar las cuotas parcialmente pagadas bastó con pasar una `lambda` a `filter()`, sin bucle ni variables acumuladoras, igual que mi ejemplo de Selección con `filter(lambda g: g.monto > 5, gastos)`. Además, las diferencias entre lenguajes que encontré (lambdas de una sola expresión en Python, la lambda de dos parámetros en Java) también aparecen en sistemas reales como ese ERP.

---

# Referencias

* Documentación oficial de Scala. (s. f.). *Scala Documentation*. https://docs.scala-lang.org
* Oracle. (s. f.). *Lambda Expressions*. The Java Tutorials. https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
* Python Software Foundation. (s. f.). *Functional Programming HOWTO*. https://docs.python.org/3/howto/functional.html
* Winitzki, S. (s. f.). *The Science of Functional Programming: A Tutorial, with Examples in Scala*. https://github.com/winitzki/sofp/releases/download/v2.2/sofp.pdf
* Anthropic. (2026). *Claude* [Asistente de inteligencia artificial]. https://claude.ai (usado como apoyo para estructurar los ejemplos de código y la redacción de las partes 3 a 5).
