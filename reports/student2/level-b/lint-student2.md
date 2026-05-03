# Lint report (Student 2) — Level B

## Herramientas

- Eclipse + SonarLint (según configuración del grupo/IDE).
- Compilación Maven:
  - `mvn -DskipTests package`
  - `mvn test` (cuando el grupo habilite tests)

## Criterio de aceptación (mi ámbito)

- No warnings/errores nuevos introducidos por mis commits en:
  - `acme.entities.campaigns.*`
  - `acme.features.spokesperson.campaign.*`
  - `acme.features.spokesperson.milestone.*`
  - features nuevas relacionadas con `Project ↔ Campaign` (pendiente de naming final)
- Cualquier warning inevitable queda documentado en este informe con:
  - archivo/clase, explicación, y por qué no se corrige.

## Checklist

- [ ] El proyecto compila con Java 21.
- [ ] No hay imports sin usar.
- [ ] No hay código muerto evidente en services.
- [ ] Mensajes i18n añadidos (EN/ES) para validaciones nuevas.
- [ ] Validaciones server-side cubren los constraints nuevos.

