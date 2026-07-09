## État du projet

### Semaine 1 ✅ — Sécurité JWT
- Inscription : POST /api/auth/register
- Connexion : POST /api/auth/login
- Refresh token : POST /api/auth/refresh-token

### Semaine 2 ✅ — CRUD métier
- Enseignants : /api/enseignants (GET, POST, PUT, DELETE)
- Etudiants : /api/etudiants (GET, POST, PUT, DELETE)
- Cours : /api/cours (GET, POST, PUT, DELETE)
- Inscriptions : /api/inscriptions (GET, POST, DELETE)
- Pagination : ?page=0&size=10

### Semaine 3 ❌ — À faire
- GlobalExceptionHandler (@ControllerAdvice)
- Upload photo de profil (POST /api/etudiants/{id}/photo)
- Upload document PDF (POST /api/etudiants/{id}/docs)

### Semaine 4 ❌ — À faire
- Swagger UI
- Script SQL d'initialisation
- Rapport technique

## Démarrage
1. Démarrer XAMPP (MySQL)
2. Lancer l'application Spring Boot
3. Base de données : eduplus_db (créée automatiquement)
4. Port : 8080

## Variables d'environnement (application.yaml)
- DB : jdbc:mysql://localhost:3306/eduplus_db
- Username : root
- Password : (vide)