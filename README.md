# maven-test

Le test a pour but de tester la les éléments de base de Maven.  
Le test doit etre fork puis envoyé par mail, ou en faisant une PR sur ce repo.

## Etape 1 : Initialisation
Créer un nouveau projet avec 3 modules: rest/model/webservice

## Etape 2 : Créer une api
Installer dans le module rest une application web basé sur Springboot

## Etape 3 : Créer un controlleur
Créer les controller pour les 4 principaux verbes Http

## Etape 4 : Lire et exposer le fichier
Lire le fichier json avec le contenu suivant :  
[
    {"id":1, "label": "allianz", "date":"2021-10-01"},
    {"id":2, "label": "toto", "date":"2021-10-02"},
    {"id":3, "label": "oto", "date":"2021-10-01"},
    {"id":4, "label": "123456654321", "date":"2021-10-03"},
    {"id":5, "label": "kayak", "date":"2021-10-04"},
    {"id":6, "label": "radar", "date":"2021-10-05"},
    {"id":7, "label": "sagas", "date":"2021-10-02"}
]
  
## Etape 5 : Exposer
Exposer le contenu du ficher via les controlleurs  
Permettre d'ajouter/modifier/supprimer un ligne (stocké en session)
