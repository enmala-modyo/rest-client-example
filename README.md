## Ejemplo RestClient

Este proyecto es un pequeño ejemplo de un cliente REST usando OpenFeign.

El cliente utiliza [WeatherAPI](https://www.weatherapi.com/) para obtener la situación
meteorológica actual de alguna ciudad en el mundo.

### Weather API Key
Para ejecutar la aplicación se debe obtener una API Key (gratuita) desde WeatherAPI y configurarla
en la variable de ambiente `WEATHERAPI_KEY`

### Test de Integración
Para la implementación de los test de integración se ha utilizado
SpringBootTest para configurar el contexto de la aplicación, la base de datos en memoria H2
y se ha simulado la conexión con Weather API utilizando [MockServer](https://www.mock-server.com/).


