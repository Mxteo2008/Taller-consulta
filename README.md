
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
