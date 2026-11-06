[caso_de_estudio_programaci_n_funcional (2).md](https://github.com/user-attachments/files/34457071/caso_de_estudio_programaci_n_funcional.2.md)
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
