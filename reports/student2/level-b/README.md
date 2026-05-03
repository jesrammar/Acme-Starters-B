# Student 2 (Level B) — Campaign / Spokesperson / Milestone

Este directorio contiene borradores y plantillas para la **parte del estudiante 2** del nivel B.

## Alcance

- Entidades/roles del nivel C que mantengo:
  - `Spokesperson`
  - `Campaign`
  - `Milestone`
- Integración nueva del nivel B que me afecta:
  - Asociación de `Campaign` como componente de `Project` (definido por otro miembro del grupo).
  - Reglas de visibilidad/publicación ligadas a `Project`.

## Archivos

- `requirements-student2.md`: reescritura estructurada (solo mi ámbito).
- `analysis-student2.md`: decisiones, dependencias y preguntas para docentes.
- `planning-student2.csv`: tareas sugeridas (abrible con Excel).
- `testing-student2.md`: estrategia y casos de prueba propuestos.
- `lint-student2.md`: checklist de lint y criterios de aceptación.
- `uml/`:
  - `uml/plantuml/domain-model-student2.puml`: diagrama editable (recomendado).
  - `uml/umlet/entities-student2.uxf`: diagrama UMLet base (opcional).
  - `uml/umlet/constraints-student2.uxf`: constraints UMLet base (opcional).

## Notas

- Este material es **borrador**: está preparado para que el grupo lo revise y lo complete con las decisiones de `Project/Manager`.
- La relación `Project *—* Campaign` está modelada como **Many-to-Many** por defecto; si el grupo decide usar entidad intermedia, actualizar el UML y requisitos.

