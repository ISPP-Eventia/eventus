# eventus
Eventia es una app web para la organización, participación y promoción de eventos tanto personales como empresariales

## workflow

### planning
Tareas, tableros y organización
[click here](https://github.com/orgs/ISPP-Eventia/projects/1)

#### issue templates
- [feature request](https://github.com/ISPP-Eventia/eventus/issues/new?assignees=&labels=development&template=feature_request.md&title=task+-+)
- [doc request](https://github.com/ISPP-Eventia/eventus/issues/new?assignees=&labels=documentation&template=documentation-request.md&title=doc+-+)
- [bug report](https://github.com/ISPP-Eventia/eventus/issues/new?assignees=&labels=bug%2C+development&template=bug_report.md&title=bug+-+)

#### kanban
 Se usaran tableros Kanban para la gestión de los sprints e iteraciones
- **Conception**: Tareas sin milestone, no asignadas, sin definición, por refinar ... 
- **ToDo**: Tareas asignadas a la milestone en curso y normalmente a un desarrolador
- **InProgress**: Tareas en progreso de ser finalizadas por un desarrollador
- **Review**: Tareas finalizadas esperando a ser validadas por otro desarrollador mediante peer review (deben incluir un comentario con una guía para testear y los criterios de aceptación)
- **Done**: Tareas finalizadas, revisidas y mergueadas a develop.

### working on issues
#### creación
- PM: crea tareas generales y las asigna a jefes de equipo o integrantes concretos
- Jefes de equipo: pueden dividir y reasignar tareas a miembros de su equipo

> nota: siempre se deben usar issue templates para mantener coherencia y consistencia entre tareas

### resolución
- Las tareas se mueven de columna en los boards según el estado en el que están
- Una vez terminadas se mantienen en review donde el jefe de equipo o PM deberan asegurar que se cumplen los criterios de aceptación

### branching, gitflow
![gitflow](https://wac-cdn.atlassian.com/dam/jcr:cc0b526e-adb7-4d45-874e-9bcea9898b4a/04%20Hotfix%20branches.svg?cdnVersion=62)

### commiting
```md
#task - <type>: <description>

[optional body]

[optional footer(s)]
```

#### commit types
- **fix**: a commit of the type fix patches a bug in your codebase
- **feat**: a commit of the type feat introduces a new feature to the codebase
- **wip**: a commit of the type wip is still in progress, which means that is a part of a bigger fix or feat
- **test**: a commit of type test introduces a new test suite o test case to the codebase
- **ci**: a commit of type ci is adding configuration or integration functionality
- **style**: a commit of type style is adding styles to some part of the frontend
- **refactor**: a commit of type refactor is refactoring code

### reviewing

#### PRs
```md
  closes #task

  ## changes
  - a
  - b
  - c

  ## steps to review
  - a
  - b
  - c
```

#### peer review
- seguir los pasos de revisión de la PR
- iniciar review de github
- revisar el código implementado
    - añadir comentarios en el código si procede
- revisar el funcionamiento o corrección añadidas
- revisar los checks de control de integración
- añadir un comentario de revisión

```md
## review
- a es correcto
- b presenta errores
```

### integrating

#### frontend
- implementa la interfaz
- usa dummies para probar la interfaz
- espera a la implementación del backend
- implementa las API calls y las añade
- comprueba la correcta integración

#### backend
- implementa el servicio correspondiente
- facilita un endpoint con dicho servicio
- documenta dicho endpoint y comunica cambios del modelo si procede
- facilita petición/respuesta de prueba
- comunica la finalización al frontend

### releasing
- versionado semántico:
    -  **x.y.z**
    -  x = mayor
    -  y = minor
    -  z = patch
- release
    - automatizado por github actions (mergeo de rama release a main)
  
## herramientas

### Management
Tareas, tableros y organización
[click here](https://github.com/orgs/ISPP-Eventia/projects/1)

Discusiones y feedback
[click here](https://github.com/ISPP-Eventia/eventus/discussions)

### GDrive
Presentaciones y Documentos
[click here](https://drive.google.com/drive/folders/1TcJAmfqr-EsH7jqasPiwTlhzOfJOaZPX?usp=sharing)

### Figma
Diseño, UI/UX
[click here](https://www.figma.com/file/7ncBwZS520GKSlelXwdCkM/Eventus?node-id=0%3A1)


### Discord
Comunicación
[click here](https://discord.gg/6EDFFWnk)

