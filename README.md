Polygon Design App

Características

Selección de figuras prediseñadas:
Descarga inicial de figuras desde un servicio REST.
Generación de polígonos regulares personalizados solicitando el número de lados.
Pantalla de diseño:

Visualización de puntos y líneas conectadas que forman la figura.
Modificación interactiva de los puntos usando gestos.

Persistencia de datos:
Las figuras descargadas inicialmente se almacenan localmente para su uso sin conexión.
Los usuarios pueden guardar figuras personalizadas para retomarlas más adelante.

Gestión de figuras:
Eliminación de figuras prediseñadas o guardadas mediante una interacción de larga pulsación.
Listado dinámico de figuras cargadas desde el almacenamiento local.

Pantallas
Selección
Presenta una lista de figuras prediseñadas y un botón para crear un nuevo polígono regular.
Permite seleccionar una figura y ajustar la escala antes de continuar al diseño.

Diseño
Muestra la figura seleccionada con sus puntos base.
Incluye funcionalidad para mover puntos y modificar la figura.
Botón para guardar la figura editada.

Estructura del Proyecto
Arquitectura
El proyecto utiliza la arquitectura Clean Architecture con las siguientes capas:

Data: Maneja las fuentes de datos remota y local.
Domain: Contiene los casos de uso de la aplicación.
Presentation: Gestiona la UI con Jetpack Compose y el estado usando ViewModel y StateFlow.

Principales Componentes

LocalGcaDataSource: Implementa el almacenamiento local usando SharedPreferences.
Permite guardar, recuperar y eliminar figuras.
PolygonsViewModel
Gestiona el estado de la UI y la lógica de negocio.
Proporciona las siguientes acciones:
fetchPolygons(): Descarga o carga figuras.
fetchSavedPolygon(): Recupera la figura guardada.
savePolygon(polygon): Guarda una figura.
deletePolygon(polygon): Elimina una figura.

Dependencias Clave
Jetpack Compose: Para la creación de UI declarativa.
Hilt: Para la inyección de dependencias.
Retrofit: Para consumir el servicio REST.
Gson: Para la serialización/deserialización de datos JSON.
SharedPreferences: Para el almacenamiento local.