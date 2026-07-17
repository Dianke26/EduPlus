

## État du projet

### Semaine 1 ✅ — Sécurité JWT
- POST /api/auth/register, /api/auth/login, /api/auth/refresh-token

### Semaine 2 ✅ — CRUD métier
- /api/etudiants, /api/enseignants, /api/cours, /api/inscriptions
- Pagination : ?page=0&size=10

### Semaine 3 ✅ — Exceptions & Upload
- GlobalExceptionHandler (@ControllerAdvice)
- POST /api/etudiants/{id}/photo (JPEG/PNG, ≤ 2 Mo)
- POST /api/etudiants/{id}/docs (PDF)

### Semaine 4 ✅ — Finalisation
- Swagger UI
- Script SQL de données de test (data-test.sql)
- Sécurisation vérifiée sur tous les endpoints

## Démarrage

1. Démarrer MySQL (XAMPP)
2. Lancer l'application :
   ./mvnw spring-boot:run
   (Windows : .\mvnw spring-boot:run)
3. Base eduplus_db créée automatiquement, port 8080

## Documentation API (Swagger)

http://localhost:8080/swagger-ui/index.html

## Créer un compte de test

Pas de compte par défaut. Créer via POST /api/auth/register :
{
"fullname": "Admin EduPlus",
"email": "admin@eduplus.sn",
"password": "Admin@2026",
"role": "ADMIN"
}
Puis se connecter via /api/auth/login pour récupérer le token JWT.

## Variables d'environnement (application.yaml)

- DB : jdbc:mysql://localhost:3306/eduplus_db
- Username : root / Password : (vide)
- Port : 8080
- Upload : ./uploads, max 2 Mo