# Réflexion sur la conception du plugin Lobor

Le but est d'avoir un système de montures qui permettent un déplacement plus rapide qu'à pied sur l'Agora.

### Monture
Pour commencer, il faut donc une classe monture qui aura la responsabilité de gérer l'entité sur laquel on monte, et son propriétaire associé.
Voilà ce qu'une monture doit pouvoir faire :

* apparaitre
* disparaitre
* s'immobiliser / (re)pouvoir avancer
* modifier sa vitesse
* faire automatiquement monter son propriétaire dessus

### Invocateur de montures
Il faut ensuite un moyen de créer des montures pour les joueurs.
Voilà ce qu'un invocateur de montures doit pouvoir faire :

* Créer une monture
* Indiquer à la monture quel est son propriétaire
* Attribuer des valeurs d'attributs initiales aux montures

### MontureManager
Il faut ensuite pouvoir gérer proprement l'ensemble des montures qui existent sur le serveur, particulièrement vis à vis des joueurs auxquels elles sont associées.
Le manager doit donc :

* Tenir un registre des montures instanciées et des joueurs auxquels elles sont associées
* Supprimer des montures à la disparation du joueur
* Gérer les différents invocateurs de montures présents sur le serveur