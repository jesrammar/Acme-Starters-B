# Testing report (Student 2) — Level B

## 1. Estrategia

- Pruebas funcionales manuales (UI) para flujos principales de Campaign y Milestone.
- Pruebas de integración (si se acuerda en el grupo) para la asociación `Project ↔ Campaign`.
- Evidencias: capturas de pantalla + datos de entrada (tickers, fechas) + resultado esperado.

## 2. Datos de prueba sugeridos

- `Campaign` con fechas en futuro válido (start < end).
- `Campaign` con `startMoment >= endMoment` (debe fallar validación).
- `Campaign` sin milestones (publicación debe fallar en nivel C).
- `Campaign` con 1 milestone (publicación debe pasar).
- `Milestone` con `effort = 0` (debe fallar).
- `Milestone` con `effort = 0.01` (mínimo aceptable).

## 3. Casos de prueba (tabla)

| ID | Actor | Caso | Precondición | Pasos | Resultado esperado |
|---|---|---|---|---|---|
| S2-T-01 | Spokesperson | Crear Campaign borrador | Autenticado | Crear con ticker único | Se crea en `draftMode=true` |
| S2-T-02 | Spokesperson | Editar Campaign borrador | Campaign propia en draft | Editar campos | Cambios persistidos |
| S2-T-03 | Spokesperson | Publicar Campaign sin milestones | Campaign propia en draft, 0 milestones | Publicar | Se rechaza con mensaje “must have milestone” |
| S2-T-04 | Spokesperson | Publicar Campaign con milestone | Campaign propia en draft, ≥1 milestone | Publicar | `draftMode=false` |
| S2-T-05 | Spokesperson | Crear Milestone con effort inválido | Campaign propia | Crear milestone effort=0 | Se rechaza |
| S2-T-06 | Spokesperson | Crear Milestone válido | Campaign propia | Crear effort=1.50 kind=TEASER | Se crea |
| S2-T-07 | Miembro Project | Ver Campaign en draft en Project | Campaign asociada al Project, draft | Listar/Ver detalle desde Project | Visible para miembros |
| S2-T-08 | No-miembro | Ver Campaign en draft en Project | Igual que S2-T-07 | Intentar listar/ver | No visible / acceso denegado |
| S2-T-09 | Manager | Publicar Project con Campaign asociada | Project publicable | Publicar Project | Campaign pasa a publicada (cascada) |

> Los casos S2-T-07..09 dependen de cómo se implemente `Project` y la membresía.

