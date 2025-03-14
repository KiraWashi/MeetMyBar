# MeetMyBar
Le projet fou de 4 amoureux de la bière en plein Brest

Meet My Bar est une application mobile Android qui permet de repérer les bars autour de soi et d'avoir des informations pratiques comme le prix de la bière la moins chère. Il sera possible de rechercher un bar par son nom, par un type de bière ou par tranche de prix pour les bières. Il est possible de se connecter  et de remplir ses infos personnelles et d’ajouter des amis sur l’application. Ajouter des amis permet d’avoir accès à leur favori et d’être ajouté à des événements créés pour une sortie dans un des bars présents. Les bars possèdent des évènements où l’utilisateur peut choisir si l'événement est visible par tous ou seulement pas ses invités ( au sein de ses amis ). Un événement est propre à un bar et à une date et heure précise. L’application est renseignée par ses utilisateurs, chaque user peut créer un bar et renseigner les informations qui lui correspondent, il est ensuite disponible pour les autres utilisateurs.
Il peut également mettre à jour les données du bar avec le nouveaux prix de la bière, ses horaires d’ouverture/fermeture.

## Structure du projet
meetmybar/ <br>
├── frontend/        # Application mobile Android <br>
    └── app/         # Code source de l'application <br>
└── backend/         # API REST Spring Boot <br>
  ├── src/         # Code source de l'API <br>
  └── docker/      # Configuration Docker <br>

## Technologies

### Frontend (Application Mobile)

- **Kotlin** - Langage de programmation principal
- **Jetpack Compose** - Framework UI moderne pour Android
- **Architecture MVVM** - Séparation des préoccupations pour une meilleure testabilité
- **Koin** - Injection de dépendances légère
- **Ktor Client** - Client HTTP pour la communication API
- **Coroutines & Flow** - Programmation asynchrone
- **Google Maps API** - Affichage de carte et services de localisation
- **DataStore** - Stockage persistant des préférences
- **Material 3** - Design system moderne et composants UI

### Backend (API REST)

- **Java 21** - Langage de programmation
- **Spring Boot 3.3.5** - Framework d'application
- **MariaDB** - Base de données relationnelle
- **Redis** - Mise en cache
- **Docker & Docker Compose** - Conteneurisation
- **Swagger/OpenAPI** - Documentation API interactive
- **Spring Data JDBC** - Accès aux données simplifié
- **JUnit & Mockito** - Framework de test

## Fonctionnalités principales

### Côté utilisateur (Frontend)

1. **Exploration de bars**
   - Carte interactive avec localisation des bars
   - Géolocalisation de l'utilisateur
   - Recherche de bars par nom

2. **Détails des bars**
   - Informations générales (nom, adresse, capacité)
   - Liste des boissons disponibles avec prix
   - Horaires d'ouverture avec statut actuel (ouvert/fermé)
   - Galerie de photos

3. **Gestion des bars et boissons**
   - Ajout, modification et suppression de bars
   - Ajout et modification de boissons dans un bar
   - Ajout de nouveaux types de boissons

4. **Personnalisation**
   - Choix du thème (Clair, Sombre, Système)

### Côté administrateur (Backend)

1. **API REST complète**
   - Endpoints pour toutes les opérations CRUD
   - Format JSON pour les échanges de données
   - Documentation Swagger interactive

2. **Gestion des données**
   - Persistance dans MariaDB
   - Mise en cache avec Redis
   - Upload et gestion de photos

3. **Sécurité et performance**
   - Validation des données
   - Gestion centralisée des exceptions
   - Circuit breaker pour les opérations critiques

## Installation et démarrage

### Prérequis

- JDK 21+
- Android Studio Hedgehog ou plus récent
- Docker et Docker Compose
- Gradle 8.0+

### Backend
#### Accéder au serveur à distance 
URL: http://leop.letareau.fr:8080 <br>
Tous les apis sont accessibles à cette url. 

#### Lancer en local
1. Cloner le dépôt
```bash
git clone https://github.com/votre-username/meetmybar.git
cd meetmybar/backend
```
2. Lancer le projet
```bash
docker-compose up -d
```

3. Accéder au Swagger

http://localhost:8080/swagger-ui.html

## Architecture
Frontend (MVVM)

Présentation : Composables Jetpack Compose, ViewModels
Domaine : Modèles, Interfaces Repository
Données : Implémentations Repository, Sources de données (API, préférences)

Backend (Couches)

API : Contrôleurs REST, Validation, Gestion d'erreurs
Business : Services, Logique métier
Données : Repositories, Entités, DTO
