# Juego Clicker de Enemigos

Este es un juego clicker desarrollado en Kotlin con Jetpack Compose, donde el jugador debe derrotar enemigos pulsando el botón “Atacar”.  
El objetivo es derrotar enemigos, ganar puntos y subir de nivel.

---

## Funcionamiento

- El jugador empieza en el nivel 1 con daño = 1.  
- Cada enemigo tiene una vida aleatoria entre 5 y 20, multiplicada por la dificultad actual.  
- Al derrotar enemigos:
  - Cada enemigo normal otorga puntos de nivel cada 3 kills.  
  - Cada 15 enemigos derrotados aparece un boss con mucha más vida (50 a 100 × dificultad).  
  - Los bosses otorgan 3 puntos de nivel.  

---

## Botones principales

### Atacar
- Resta vida al enemigo según el daño actual.  
- Si el enemigo muere, aparece uno nuevo aleatorio.  
- Si era un boss, otorga puntos extra de nivel.  

### Subir nivel
- Gasta 1 punto de nivel para:  
  - Aumentar en +1 el nivel.  
  - Aumentar en +1 el daño.  
  - Cada 5 subidas de nivel aumenta la dificultad, haciendo que los enemigos aparezcan con más vida.  
- Si se intenta subir de nivel sin puntos, aparece un mensaje de error en rojo.  

---

## Información en pantalla

- Nivel actual  
- Daño  
- Kills totales  
- Puntos de nivel disponibles  
- Dificultad actual  
- Imagen y vida del enemigo en curso  
- Mensajes dinámicos (subida de nivel, enemigo derrotado, aparición de boss, etc.)

---

## Características

- Enemigos normales con varias imágenes aleatorias.  
- Bosses con imágenes distintas a los enemigos normales.  
- Escalado de dificultad progresivo para aumentar la dificultad a medida que el jugador avanza.  

