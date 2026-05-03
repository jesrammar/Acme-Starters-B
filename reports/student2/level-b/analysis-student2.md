# Análisis (Student 2) — Level B (Campaign / Spokesperson / Milestone)

## 1. Qué cambia respecto a Level C

En mi ámbito (campaign/spokesperson/milestone) el cambio de Level B es principalmente de **integración**:

- `Campaign` pasa a ser también componente de `Project` (`Project *—* Campaign`).
- La visibilidad y publicación ya no dependen solo del `draftMode` de Campaign, sino de pertenecer (o no) a un proyecto y del estado de publicación del proyecto.

## 2. Dependencias y coordinación

### 2.1 Dependencia principal: `Project` y membresías

Necesito que el grupo defina:

- Entidad `Project` (campos mínimos B/2).
- Relación de membresías del proyecto (inventor/spokesperson/fundraiser).
- API de repositorio/servicio para:
  - comprobar si un usuario es miembro del proyecto.
  - listar campañas del proyecto (incluyendo borradores para miembros).
  - publicar componentes en cascada al publicar el proyecto.

### 2.2 Decisión de modelado pendiente

- Opción A (preferida): Many-to-Many directo `Project *—* Campaign`.
- Opción B: entidad intermedia `ProjectCampaign` si hay metadatos (fecha, rol, orden, etc.).

## 3. Preguntas para docentes (bloqueantes / aclaraciones)

1) Publicación en cascada:
   - Si al publicar un `Project` hay campañas asociadas que NO cumplen C-S2-01 (sin hitos) o C-S2-03 (momentos no futuros), ¿se bloquea la publicación del proyecto o se fuerza igualmente la publicación de componentes?

2) Visibilidad:
   - El enunciado dice que “todo miembro del proyecto puede mostrar los datos aunque no estén publicados”. ¿Esto incluye **listar** y **ver detalle** de campañas/hitos, o también operar (editar) sobre los componentes ajenos?

3) Membresía y asociación:
   - ¿Puede un `Spokesperson` asociar una campaña al proyecto si la campaña pertenece a otro `Spokesperson`? (interpretación: no).

4) Effort / monthsActive:
   - Confirmar el redondeo y la unidad “meses activos” para el cálculo B/3 a nivel proyecto.

## 4. Trazabilidad (esqueleto)

- FR-S2-01..05 → `acme.features.spokesperson.campaign.*`
- FR-S2-06..09 → `acme.features.spokesperson.milestone.*`
- FR-S2-10..13 → nuevas features (pendiente de estructura final del grupo):
  - `acme.features.spokesperson.projectCampaign.*` (sugerido)
  - `acme.features.any.projectCampaign.*` (listado/visualización para miembros)

