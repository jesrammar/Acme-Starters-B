# Requisitos (Student 2) — Level B (Campaign / Spokesperson / Milestone)

## 1. Requisitos de información (modelo de datos)

### 1.1 Role: `Spokesperson`

- `cv: String` (obligatorio)
- `achievements: String` (obligatorio)
- `licensed: Boolean` (obligatorio)

### 1.2 Entity: `Campaign`

- `ticker: String` (obligatorio, único)
- `name: String` (obligatorio)
- `description: String` (obligatorio)
- `startMoment: Date` (obligatorio)
- `endMoment: Date` (obligatorio)
- `moreInfo: String` (opcional, URL)
- `draftMode: Boolean` (obligatorio)
- `spokesperson: Spokesperson` (obligatorio, N:1)

Campos derivados (no persistentes):

- `monthsActive: Double` — meses activos calculados a partir de `startMoment`/`endMoment` (redondeo a 1 decimal).
- `effort: Double` — esfuerzo de la campaña, suma del esfuerzo de sus hitos.

### 1.3 Entity: `Milestone`

- `title: String` (obligatorio)
- `achievements: String` (obligatorio)
- `effort: Double` (obligatorio, > 0)
- `kind: MilestoneKind` (obligatorio)
- `campaign: Campaign` (obligatorio, N:1)

### 1.4 Integración Level B: `Project` (referencia)

`Project` pertenece al alcance del estudiante que implemente **Manager/Project**, pero para mi parte se requiere:

- Relación `Project *—* Campaign` (una campaña puede pertenecer a 0..* proyectos; un proyecto puede incluir 0..* campañas).

> Si el grupo decide entidad intermedia (p. ej. `ProjectCampaign`), reflejarlo aquí.

## 2. Requisitos funcionales (solo mi ámbito)

### 2.1 Spokesperson — Campaign (CRUD y publicación)

- FR-S2-01: Un `Spokesperson` puede listar sus campañas (incluyendo las en borrador).
- FR-S2-02: Un `Spokesperson` puede crear una campaña en borrador.
- FR-S2-03: Un `Spokesperson` puede editar una campaña en borrador propia.
- FR-S2-04: Un `Spokesperson` puede publicar una campaña propia.
- FR-S2-05: Un `Spokesperson` puede ver el detalle de una campaña propia.

### 2.2 Spokesperson — Milestone (CRUD)

- FR-S2-06: Un `Spokesperson` puede listar los hitos de una de sus campañas.
- FR-S2-07: Un `Spokesperson` puede crear hitos asociados a una campaña suya.
- FR-S2-08: Un `Spokesperson` puede editar hitos de una campaña suya (según reglas del nivel C).
- FR-S2-09: Un `Spokesperson` puede ver el detalle de un hito de una campaña suya.

### 2.3 Level B — Proyectos (acoplamiento con Project/Manager)

Estas funcionalidades dependen de `Project` y de la selección de miembros del proyecto:

- FR-S2-10: Un `Spokesperson` que sea **miembro de un proyecto** puede asociar una de sus campañas al proyecto (aunque esté en borrador).
- FR-S2-11: Un `Spokesperson` puede desasociar una de sus campañas de un proyecto (solo si es miembro y es su campaña).
- FR-S2-12: Cualquier miembro de un proyecto puede listar/ver campañas e hitos del proyecto aunque estén en borrador.
- FR-S2-13: Cuando un `Manager` publique un `Project`, las campañas asociadas deben pasar a publicadas automáticamente.

## 3. Requisitos no funcionales

- NFR-S2-01: Consistencia con la arquitectura del framework (services/repositories/views) y patrones de Level C.
- NFR-S2-02: Validaciones con anotaciones/constraints (server-side) y mensajes i18n (EN/ES).
- NFR-S2-03: Trazabilidad: cada FR anterior debe mapear a clases/services y a casos de prueba.

## 4. Requisitos de validación (constraints)

### 4.1 Constraints de Campaign (Level C)

- C-S2-01: Una campaña no puede publicarse si no tiene al menos un hito.
- C-S2-02: `startMoment < endMoment`.
- C-S2-03: En el momento de publicar, `startMoment` y `endMoment` deben estar en el futuro (confirmar interpretación exacta con docentes).
- C-S2-04: `ticker` único.

### 4.2 Constraints de integración con Project (Level B)

- C-S2-05: Solo un miembro del proyecto puede asociar componentes al proyecto.
- C-S2-06: Un miembro del proyecto puede visualizar componentes (incluyendo borradores) del proyecto.
- C-S2-07: Publicar un proyecto implica publicar sus componentes automáticamente.

## 5. Requisitos de testing (resumen)

Ver `testing-student2.md`.

