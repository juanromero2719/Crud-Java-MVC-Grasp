## Motivación

La motivación detrás de este proyecto fue crear una aplicación robusta y mantenible que sigue los principios de diseño de software, en particular los principios GRASP. Quería construir un sistema que pudiera gestionar múltiples entidades y fuentes de datos, como H2 y archivos CSV, de manera flexible y extensible.

## ¿Por qué construiste el proyecto?

Este proyecto fue construido para poner en práctica mis conocimientos sobre la arquitectura de software, el patrón Modelo-Vista-Controlador (MVC) y los principios de diseño GRASP. El objetivo era crear un sistema CRUD que fuera fácil de mantener, escalar y entender, permitiendo a otros desarrolladores agregar nuevas funcionalidades sin alterar significativamente el código existente.

## ¿Qué problema soluciona?

El proyecto resuelve el problema de la gestión de entidades en una base de datos y en archivos planos (CSV). Además, ofrece una estructura clara y extensible que permite cambiar fácilmente entre diferentes fuentes de datos (H2 y CSV) sin modificar el código de las entidades o controladores.

## ¿Qué hace a tu proyecto diferente?

Lo que diferencia a este proyecto es su adherencia a los principios GRASP y su capacidad para gestionar múltiples fuentes de datos de manera flexible. La automatización del proceso de configuración de DAO para diferentes fuentes de datos y la minimización del código repetitivo son aspectos clave que lo hacen único.

## ¿Qué aprendiste?

### Principios GRASP

- **Creator (Creador):** Se utiliza para delegar la responsabilidad de crear objetos a la clase que tiene la mayor cantidad de información para realizar esa creación.
- **Controller (Controlador):** Se aplica para definir una clase intermediaria que maneja las peticiones de la vista y coordina las respuestas adecuadas, asegurando un bajo acoplamiento.
- **Information Expert (Experto en Información):** Las responsabilidades se asignan a la clase que tiene la información necesaria para cumplirlas, promoviendo así la cohesión.
- **Low Coupling (Bajo Acoplamiento):** Las dependencias entre clases se minimizan para asegurar que los cambios en una clase tengan un impacto mínimo en otras.
- **High Cohesion (Alta Cohesión):** Se asegura que las clases tengan responsabilidades claras y relacionadas, lo que facilita la mantenibilidad y comprensión del código.
- **Polymorphism (Polimorfismo):** Se utiliza para permitir que diferentes tipos de objetos sean tratados de manera uniforme a través de interfaces comunes.
- **Pure Fabrication (Fabricación Pura):** Creación de clases que no representan entidades del dominio, sino que son necesarias para el buen diseño, como las fábricas de conexión.

### Clean Code y Vistas Gráficas

Durante el desarrollo de las vistas gráficas, apliqué los principios aprendidos en los primeros siete capítulos de *Clean Code* de Robert C. Martin:

1. **Capítulo 1 - Significado de los nombres:** Utilicé nombres claros y significativos para variables, métodos y clases en las vistas gráficas para que su propósito fuera inmediatamente evidente. Esto ayudó a que el código fuera más legible y autoexplicativo.

2. **Capítulo 2 - Funciones:** Las funciones en las vistas gráficas se mantuvieron pequeñas y con una única responsabilidad, siguiendo el principio de que una función debe hacer solo una cosa y hacerla bien.

3. **Capítulo 3 - Comentarios:** Evité comentarios innecesarios, enfocándome en escribir código que se explicara por sí mismo. Solo añadí comentarios cuando eran absolutamente necesarios para aclarar decisiones complejas.

4. **Capítulo 4 - Formato:** Mantener un formato consistente fue crucial para la legibilidad. Apliqué espacios en blanco, sangrías y particioné el código en secciones lógicas para hacer las vistas más fáciles de navegar.

5. **Capítulo 5 - Objetos y estructuras de datos:** Utilicé objetos para encapsular comportamiento y datos relacionados, en lugar de depender de estructuras de datos crudas. Esto promovió la encapsulación y la ocultación de la implementación.

6. **Capítulo 6 - Clases:** Las clases se diseñaron siguiendo el principio de cohesión alta, asegurando que cada clase tuviera una responsabilidad bien definida, lo que facilitó su entendimiento y mantenimiento.

7. **Capítulo 7 - Manejo de errores:** Implementé manejo de errores robusto en las vistas gráficas, utilizando excepciones en lugar de códigos de retorno, y asegurando que los errores se manejan de manera centralizada y coherente.

## Complejidades

Una de las complejidades más significativas del proyecto fue la implementación de un sistema que pudiera manejar múltiples fuentes de datos (H2 y CSV) de manera transparente. Esto requirió una configuración cuidadosa de las fábricas de conexión y la gestión dinámica de DAO, asegurando que el código fuera extensible sin necesidad de cambios significativos al agregar nuevas entidades o fuentes de datos.

La integración de las vistas gráficas con el sistema MVC también presentó desafíos, especialmente en la sincronización de las interfaces gráficas y de consola, y en la aplicación de principios de código limpio para asegurar la mantenibilidad del proyecto.

## Generalidades

El proyecto sigue una estructura clara basada en el patrón MVC, con carpetas separadas para las entidades, controladores, vistas y repositorios. La aplicación es capaz de manejar operaciones CRUD para diferentes entidades, como `Cargo`, `Pais`, `Departamento`, `Municipio`, `Direccion`, `Persona`, `Empleado`, y `Estudiante`, con la posibilidad de cambiar fácilmente entre almacenamiento en base de datos H2 o archivos CSV.

La configuración automática de DAOs basada en la lectura de directorios facilita la mantenibilidad y la escalabilidad del proyecto, y la implementación de interfaces gráficas siguiendo los principios de *Clean Code* asegura que la interfaz de usuario sea intuitiva y fácil de usar.
