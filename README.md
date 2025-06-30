# 🧠 Sistema de Consulta de Libros y Autores 📚

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Gutendex API](https://img.shields.io/badge/API-Gutendex-blue?style=for-the-badge)

Aplicación Java con Spring Boot para consultar información de libros y autores utilizando la API de Gutendex. Permite buscar libros por título, listar libros registrados, buscar autores, obtener estadísticas y más.

---

## ✨ Características principales

- 🔍 Búsqueda de libros por título (con normalización de entrada)
- 📚 Listado de libros registrados con detalles
- 👤 Búsqueda de autores por nombre
- 🧾 Listado de autores registrados con sus obras
- 🌍 Filtrado de libros por idioma con estadísticas
- 🏆 Top 10 libros más descargados
- 📅 Búsqueda de autores vivos en año específico
- 📊 Estadísticas detalladas de descargas
- 🕰️ Búsqueda de autores por rango de nacimiento

---

## 🛠️ Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **Gutendex API**

---

## 📊 Diagrama de clases

```mermaid
classDiagram
    class Libro {
        -Long id
        -String titulo
        -Autor autor
        -String idioma
        -Long descargas
        +getTitulo()
        +getAutor()
        +getIdioma()
        +getDescargas()
    }

    class Autor {
        -Long id
        -String nombre
        -Integer nacimiento
        -Integer fallecimiento
        -List<Libro> libros
        +getNombre()
        +getNacimiento()
        +getFallecimiento()
        +getLibros()
    }

    class MenuService {
        -GutendexService gutendexService
        -LibroService libroService
        -AutorService autorService
        +mostrarMenuPrincipal()
        -buscarLibroPorTitulo()
        -buscarAutorPorNombre()
        -listarLibrosRegistrados()
        -listarAutoresRegistrados()
        -buscarAutoresVivosEnAnio()
        -listarLibrosPorIdioma()
        -mostrarTop10Libros()
        -mostrarEstadisticasDescargas()
        -buscarAutoresPorRangoNacimiento()
    }

    class GutendexService {
        -LibroRepository libroRepository
        -AutorRepository autorRepository
        +buscarYGuardarLibroPorTitulo(String titulo)
        -guardarLibroDesdeDTO(BookDTO bookDTO)
        -procesarAutor(AuthorDTO authorDTO)
    }

    Libro "1" --* "1" Autor : tiene
    MenuService --> GutendexService
    MenuService --> LibroService
    MenuService --> AutorService
```

---

## 🚀 Instalación y ejecución

### Requisitos previos

- Java 17  
- Maven  
- PostgreSQL

### Pasos para ejecutar

1. **Configurar base de datos PostgreSQL**  
   - Crear una base de datos llamada `literatura`  
   - Configurar usuario y contraseña en `application.properties`

2. **Clonar el repositorio**
```bash
git clone https://github.com/Alejolora25/LiteraturaConsultaDeLibros.git
```

3. **Navegar al directorio del proyecto**
```bash
cd LiteraturaConsultaDeLibros
```

4. **Compilar y ejecutar con Maven**
```bash
./mvnw spring-boot:run
```

5. **Interactuar con la aplicación**
   - La aplicación mostrará un menú en la consola
   - Selecciona opciones usando números del 1 al 10

---

## 💻 Uso de la aplicación

### Menú principal

```
===== MENÚ PRINCIPAL =====
1. Buscar libro por título
2. Listar libros registrados
3. Buscar autores por nombre
4. Listar autores registrados
5. Listar libros por idioma
6. Top 10 libros más descargados
7. Buscar autores vivos en año específico
8. Estadísticas de descargas
9. Buscar autores por rango de nacimiento
10. Salir
```

### Funcionalidades destacadas

#### 🔍 Buscar libro por título
- Busca libros en Gutendex API por título
- Guarda los resultados en la base de datos
- Maneja normalización de títulos (espacios, mayúsculas/minúsculas)

#### 🌍 Listar libros por idioma

```
=== OPCIONES DE IDIOMA ===
1. Buscar libros por idioma específico
2. Ver estadísticas de todos los idiomas
```

- Muestra distribución de libros por idioma
- Calcula porcentajes de cada idioma
- Permite filtrar por código de idioma (ej: es, en, fr)

---

## 📊 Estadísticas de descargas

```
=== ESTADÍSTICAS DE DESCARGAS ===
Total de libros: 25
Mínimo de descargas: 50
Máximo de descargas: 1500
Promedio de descargas: 345.67
Suma total de descargas: 8642

Distribución por rangos de descargas:
- 0-99: 5 libros (20.0%)
- 100-499: 12 libros (48.0%)
- 500-999: 6 libros (24.0%)
- 1000+: 2 libros (8.0%)
```

---

## 📚 Ejemplos de libros para buscar

- *Pride and Prejudice* - ID: 1342 (Inglés)
- *Frankenstein* - ID: 84 (Inglés)
- *Don Quijote* - ID: 2000 (Español)
- *The Metamorphosis* - ID: 5200 (Alemán/Inglés)
- *Alice's Adventures in Wonderland* - ID: 11 (Inglés)

---

## 🤝 Contribuciones

¡Las contribuciones son bienvenidas!

1. Haz un fork del repositorio  
2. Crea una rama para tu feature  
   ```bash
   git checkout -b feature/nueva-funcionalidad
   ```
3. Haz commit de tus cambios  
   ```bash
   git commit -am 'Agrega nueva funcionalidad'
   ```
4. Haz push a la rama  
   ```bash
   git push origin feature/nueva-funcionalidad
   ```
5. Abre un Pull Request

---

## 👤 Autor

**Alejandro Lora**  
Proyecto desarrollado como parte del reto de Alura Latam.

## 💻 Imagenes de uso

- Menu principal

![image](https://github.com/user-attachments/assets/f2bade63-d87d-4d87-90f9-7dabd687643c)

1. Buscar libro por título

![image](https://github.com/user-attachments/assets/285a3f09-5c90-4b16-b05d-c32cfb0ad7eb)

2. Listar libros registrados

![image](https://github.com/user-attachments/assets/d7777b1c-eb12-44bb-8d2e-742d7c7bc274)

3. Buscar autores por nombre

![image](https://github.com/user-attachments/assets/5c25ddb4-2f31-4dd6-9541-389e64065a15)

4. Listar autores registrados

![image](https://github.com/user-attachments/assets/09921784-4e44-4021-8929-dfc8be784d27)

5. Listar libros por idioma - Opcion 1
   
![image](https://github.com/user-attachments/assets/ecc278b8-f2f4-41f5-ad68-6642827a09da)

5. Listar libros por idioma  - Opcion 2

![image](https://github.com/user-attachments/assets/56ea1998-0fa3-4b40-9927-1df9779a87e3)

6. Top 10 libros más descargados

![image](https://github.com/user-attachments/assets/65c78904-ac41-4eb2-b7cd-91ed42c136e7)

7. Buscar autores vivos en año específico

![image](https://github.com/user-attachments/assets/989b04c0-1c30-4166-a0f0-6bb396ce4c0f)

8. Estadísticas de descargas

![image](https://github.com/user-attachments/assets/97415630-b169-4434-98a5-f821b7a6b0dd)

9. Buscar autores por rango de nacimiento

![image](https://github.com/user-attachments/assets/8260145c-58f8-4bb9-93fc-1cfefeb8660c)

10. Salir

![image](https://github.com/user-attachments/assets/ec41b0fc-5904-40a9-b80a-26f9281ee179)
